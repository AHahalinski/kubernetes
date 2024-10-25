package com.epam.learning.kubernetes.dto;

import java.time.LocalDateTime;

public class PostDto {

    private Long id;
    private Long authorId;
    private String topic;
    private String text;
    private LocalDateTime postedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getPostedAt() {
        return postedAt;
    }

    public void setPostedAt(LocalDateTime postedAt) {
        this.postedAt = postedAt;
    }

    @Override
    public String toString() {
        return "PostDto{" +
                "id=" + id +
                ", authorId='" + authorId + '\'' +
                ", topic='" + topic + '\'' +
                ", text='" + text + '\'' +
                ", postedAt=" + postedAt +
                '}';
    }
}
