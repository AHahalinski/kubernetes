package com.epam.learning.kubernetes.controller;

import com.epam.learning.kubernetes.dto.PostDto;
import com.epam.learning.kubernetes.model.User;
import com.epam.learning.kubernetes.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/post-application/v1/")
public class PostController {

    private final UserRestTemplate userRestTemplate;

    private final PostService postService;

    @Autowired
    public PostController(UserRestTemplate userRestTemplate, PostService postService) {
        this.userRestTemplate = userRestTemplate;
        this.postService = postService;
    }

    @GetMapping("/greeting")
    public String greeting() {
        return "Hello, k8s!";
    }

    @PostMapping("/posts")
    public PostDto create(@RequestBody PostDto postDto) {
        User user = userRestTemplate.findById(postDto.getAuthorId());
        PostDto createdPostDto = postService.create(postDto);
        increaseUserAmountOfPosts(user);
        return createdPostDto;
    }

    @GetMapping("/posts/{id}")
    public PostDto findById(@PathVariable("id") Long id) {
        return postService.findById(id);
    }

    @DeleteMapping("/posts/{id}")
    public void delete(@PathVariable("id") Long id) {
        PostDto postDto = postService.findById(id);
        User user = userRestTemplate.findById(postDto.getAuthorId());
        postService.delete(id);
        decreaseUserAmountOfPosts(user);
    }

    @PutMapping("/posts/{id}")
    public PostDto update(@PathVariable("id") Long id, @RequestBody PostDto postDto) {
        postDto.setId(id);
        return postService.update(postDto);
    }

    private void increaseUserAmountOfPosts(User user) {
        user.setAmountOfPosts(user.getAmountOfPosts() + 1);
        userRestTemplate.update(user.getId(), user);
    }

    private void decreaseUserAmountOfPosts(User user) {
        if (hasPosts(user)) {
            user.setAmountOfPosts(user.getAmountOfPosts() - 1);
            userRestTemplate.update(user.getId(), user);
        }
    }

    private boolean hasPosts(User user) {
        return user.getAmountOfPosts() > 0;
    }
}
