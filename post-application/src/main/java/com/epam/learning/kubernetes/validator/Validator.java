package com.epam.learning.kubernetes.validator;

import com.epam.learning.kubernetes.dto.PostDto;
import com.epam.learning.kubernetes.exception.ValidationException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Validator {

    private static final int MIN_LENGTH = 5;
    private static final int MAX_LENGTH = 255;

    private static final String ERROR_MESSAGE_POST_TEXT_IS_REQUIRED = "Post text is required";
    private static final String ERROR_MESSAGE_USERNAME_LESS_MIN_LENGTH = "Post text length is less than " + MIN_LENGTH;
    private static final String ERROR_MESSAGE_USERNAME_GREAT_MAX_LENGTH = "Post text length is great than " + MAX_LENGTH;

    public void validate(PostDto post) {
        List<String> errorMessages = new ArrayList<>();
        validateTextField(post.getText(), errorMessages);
        processErrorMessages(errorMessages);
    }

    private void processErrorMessages(List<String> errorMessages) {
        if (!errorMessages.isEmpty()) {
            throw new ValidationException(errorMessages);
        }
    }

    private void validateTextField(String text, List<String> errorMessages) {
        if (StringUtils.isNotBlank(text)) {
            validateTextFieldLength(text, errorMessages);
        } else {
            errorMessages.add(ERROR_MESSAGE_POST_TEXT_IS_REQUIRED);
        }
    }

    private void validateTextFieldLength(String username, List<String> errorMessages) {
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
