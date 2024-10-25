package com.epam.learning.kubernetes.dao;

import com.epam.learning.kubernetes.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostDao extends JpaRepository<Post, Long> {
}
