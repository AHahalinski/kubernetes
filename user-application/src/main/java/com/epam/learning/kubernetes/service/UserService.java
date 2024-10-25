package com.epam.learning.kubernetes.service;

import com.epam.learning.kubernetes.model.User;

public interface UserService {

    User create(User user);

    User findById(Long id);

    void delete(Long id);

    User update(User user);
}
