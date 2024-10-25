package com.epam.learning.kubernetes.service;

import com.epam.learning.kubernetes.dao.PostDao;
import com.epam.learning.kubernetes.dto.PostDto;
import com.epam.learning.kubernetes.exception.EntityNotFoundException;
import com.epam.learning.kubernetes.model.Post;
import com.epam.learning.kubernetes.validator.Validator;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class PostServiceImpl implements PostService {

    private static final String ERROR_MESSAGE_POST_NOT_FOUND = "Post with id = '%s' not found";

    private final ModelMapper mapper = new ModelMapper();

    private final PostDao postDao;

    private final Validator validator;

    @Autowired
    public PostServiceImpl(PostDao postDao, Validator validator) {
        this.postDao = postDao;
        this.validator = validator;
    }

    @Transactional
    @Override
    public PostDto create(PostDto postDto) {
        validator.validate(postDto);
        setPostedAt(postDto);
        Post post = mapper.map(postDto, Post.class);

        return mapper.map(postDao.save(post), PostDto.class);
    }

    @Transactional(readOnly = true)
    @Override
    public PostDto findById(Long id) {
        if (postDao.existsById(id)) {
            return mapper.map(postDao.getReferenceById(id), PostDto.class);
        }
        throw new EntityNotFoundException(String.format(ERROR_MESSAGE_POST_NOT_FOUND, id));
    }

    @Transactional
    @Override
    public void delete(Long id) {
        if (postDao.existsById(id)) {
            postDao.deleteById(id);
        } else {
            throw new EntityNotFoundException(String.format(ERROR_MESSAGE_POST_NOT_FOUND, id));
        }
    }

    @Transactional
    @Override
    public PostDto update(PostDto updatedPostDto) {
        Long postId = updatedPostDto.getId();
        if (postDao.existsById(postId)) {
            Post existingPost = postDao.getReferenceById(postId);
            validator.validate(updatedPostDto);
            Post updatedPost = mapper.map(updatedPostDto, Post.class);
            updateFields(existingPost, updatedPost);

            return mapper.map(postDao.save(existingPost), PostDto.class);
        }
        throw new EntityNotFoundException(String.format(ERROR_MESSAGE_POST_NOT_FOUND, postId));
    }

    private void updateFields(Post existingPost, Post post) {
        updateTextField(existingPost, post.getText());
        updateTopicField(existingPost, post.getTopic());
    }

    private void updateTextField(Post existingPost, String text) {
        if (StringUtils.isNotBlank(text)) {
            existingPost.setText(text);
        }
    }

    private void updateTopicField(Post existingPost, String topic) {
        if (StringUtils.isNotBlank(topic)) {
            existingPost.setTopic(topic);
        }
    }

    private void setPostedAt(PostDto post) {
        post.setPostedAt(LocalDateTime.now());
    }

}
