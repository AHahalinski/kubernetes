package com.epam.learning.kubernetes.controller;

import com.epam.learning.kubernetes.exception.ExternalServiceUnavailable;
import com.epam.learning.kubernetes.exception.EntityNotFoundException;
import com.epam.learning.kubernetes.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

@Component
public class UserRestTemplate {

    private static final String ERROR_MESSAGE_USER_APP_UNAVAILABLE = "User app is unavailable";

    @Value("${user-app.url}")
    private String userAppHostPort;

    private String userAppUrlUsers = "/user-application/v1/users/";

    private final RestTemplate restTemplate;

    public UserRestTemplate() {
        this.restTemplate = new RestTemplate();
    }

    public User findById(long authorId) {
        try {
            return restTemplate.getForEntity(getFullUserAppUrl() + authorId, User.class).getBody();
        } catch (ResourceAccessException ex) {
            throw new ExternalServiceUnavailable(ERROR_MESSAGE_USER_APP_UNAVAILABLE);
        } catch (HttpClientErrorException.NotFound ex) {
            throw new EntityNotFoundException(ex.getMessage());
        }
    }

    public void update(long id, User user) {
        try {
            restTemplate.put(getFullUserAppUrl() + id, user);
        } catch (ResourceAccessException ex) {
            throw new ExternalServiceUnavailable(ERROR_MESSAGE_USER_APP_UNAVAILABLE);
        }
    }

    private String getFullUserAppUrl() {
        return userAppHostPort + userAppUrlUsers;
    }
}
