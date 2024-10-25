package com.epam.learning.kubernetes.validator;

import com.epam.learning.kubernetes.exception.ValidationException;
import com.epam.learning.kubernetes.model.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Validator {

    private static final String REGEX_NUMBER = "^\\d*$";

    private static final int MIN_LENGTH = 5;
    private static final int MAX_LENGTH = 15;

    private static final String ERROR_MESSAGE_USERNAME_IS_REQUIRED = "Username is required";
    private static final String ERROR_MESSAGE_USERNAME_LESS_MIN_LENGTH = "Username length is less than " + MIN_LENGTH;
    private static final String ERROR_MESSAGE_USERNAME_GREAT_MAX_LENGTH = "Username length is great than " + MAX_LENGTH;
    private static final String ERROR_MESSAGE_AMOUNT_OF_POSTS_NOT_POSITIVE_NUMBER =
            "Amount of posts should be a number that is more than '0'";

    public void validate(User user) {
        List<String> errorMessages = new ArrayList<>();
        validateUsername(user.getUsername(), errorMessages);
        validateAmountOfPosts(user.getAmountOfPosts(), errorMessages);
        processErrorMessages(errorMessages);
    }


    public void validateForUpdate(User user) {
        List<String> errorMessages = new ArrayList<>();

        String username = user.getUsername();
        if (StringUtils.isNotBlank(username)) {
            validateUsername(username, errorMessages);
        }

        String amountOfPosts = user.getAmountOfPosts();
        if (StringUtils.isNotBlank(amountOfPosts)) {
            validateAmountOfPosts(amountOfPosts, errorMessages);
        }

        processErrorMessages(errorMessages);
    }

    private void processErrorMessages(List<String> errorMessages) {
        if (!errorMessages.isEmpty()) {
            throw new ValidationException(errorMessages);
        }
    }

    private void validateUsername(String username, List<String> errorMessages) {
        if (StringUtils.isNotBlank(username)) {
            validateUsernameFieldLength(username, errorMessages);
        } else {
            errorMessages.add(ERROR_MESSAGE_USERNAME_IS_REQUIRED);
        }
    }

    private void validateAmountOfPosts(String amountOfPosts, List<String> errorMessages) {
        if (!amountOfPosts.matches(REGEX_NUMBER)) {
            errorMessages.add(ERROR_MESSAGE_AMOUNT_OF_POSTS_NOT_POSITIVE_NUMBER);
        }
    }

    private void validateUsernameFieldLength(String username, List<String> errorMessages) {
        validateMinLength(username, errorMessages);
        validateMaxLength(username, errorMessages);
    }

    private void validateMaxLength(String username, List<String> errorMessages) {
        if (username.length() > MAX_LENGTH) {
            errorMessages.add(ERROR_MESSAGE_USERNAME_GREAT_MAX_LENGTH);
        }
    }

    private void validateMinLength(String username, List<String> errorMessages) {
        if (username.length() < MIN_LENGTH) {
            errorMessages.add(ERROR_MESSAGE_USERNAME_LESS_MIN_LENGTH);
        }
    }

}
