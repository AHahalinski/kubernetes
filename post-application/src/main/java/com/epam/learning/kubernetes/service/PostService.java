package com.epam.learning.kubernetes.service;

import com.epam.learning.kubernetes.dto.PostDto;

public interface PostService {

    PostDto create(PostDto postDto);

    PostDto findById(Long id);

    void delete(Long id);

    PostDto update(PostDto postDto);
}
