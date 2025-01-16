package com.sns.comment.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Comment {
    private int id;
    private int postId;
    private int userId;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
