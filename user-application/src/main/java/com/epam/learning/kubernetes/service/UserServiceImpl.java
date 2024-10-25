package com.epam.learning.kubernetes.service;

import com.epam.learning.kubernetes.dao.UserDao;
import com.epam.learning.kubernetes.exception.UserEntityNotFoundException;
import com.epam.learning.kubernetes.exception.ValidationException;
import com.epam.learning.kubernetes.model.User;
import com.epam.learning.kubernetes.validator.Validator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private static final String ERROR_MESSAGE_POST_NOT_FOUND = "User with id = '%s' not found";
    private static final String ERROR_MESSAGE_USER_ALREADY_EXIST = "Another user already has this username: ";

    private final UserDao userDao;

    private final Validator validator;

    @Autowired
    public UserServiceImpl(UserDao userDao, Validator validator) {
        this.userDao = userDao;
        this.validator = validator;
    }

    @Transactional
    @Override
    public User create(User user) {
        checkUniqueUsername(user);
        validator.validate(user);

        return userDao.save(user);

    }

    @Transactional(readOnly = true)
    @Override
    public User findById(Long id) {
        if (userDao.existsById(id)) {
            return userDao.getReferenceById(id);
        }
        throw new UserEntityNotFoundException(String.format(ERROR_MESSAGE_POST_NOT_FOUND, id));
    }

    @Transactional
    @Override
    public void delete(Long id) {
        if (userDao.existsById(id)) {
            userDao.deleteById(id);
        }
        throw new UserEntityNotFoundException(String.format(ERROR_MESSAGE_POST_NOT_FOUND, id));
    }

    @Transactional
    @Override
    public User update(User updatedUser) {
        Long userId = updatedUser.getId();
        if (isExistsById(updatedUser)) {
            User existingUser = userDao.getReferenceById(userId);
            return performUpdating(updatedUser, existingUser);
        } else {
            throw new UserEntityNotFoundException(String.format(ERROR_MESSAGE_POST_NOT_FOUND, userId));
        }

    }

    private User performUpdating(User updatedUser, User existingUser) {
        checkUniqueUsername(updatedUser);
        validator.validateForUpdate(updatedUser);
        updateUserFields(existingUser, updatedUser);

        return userDao.save(existingUser);
    }

    private void checkUniqueUsername(User updatedUser) {
        Optional<User> optionalUser = userDao.findByUsername(updatedUser.getUsername());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (isIdsNotEqual(updatedUser, user)) {
                throw new ValidationException(ERROR_MESSAGE_USER_ALREADY_EXIST + updatedUser.getUsername());
            }
        }
    }

    private boolean isIdsNotEqual(User updatedUser, User user) {
        return !user.getId().equals(updatedUser.getId());
    }

    private boolean isExistsById(User updatedUser) {
        return userDao.existsById(updatedUser.getId());
    }

    private void updateUserFields(User existingUser, User updatedUser) {
        updateUsername(existingUser, updatedUser);
        updateAmountOfPosts(existingUser, updatedUser);
    }

    private void updateAmountOfPosts(User existingUser, User updatedUser) {
        String amountOfPosts = updatedUser.getAmountOfPosts();
        if (StringUtils.isNotBlank(amountOfPosts)) {
            existingUser.setAmountOfPosts(amountOfPosts);
        }
    }

    private void updateUsername(User existingUser, User user) {
        String username = user.getUsername();
        existingUser.setUsername(username);
    }

}
