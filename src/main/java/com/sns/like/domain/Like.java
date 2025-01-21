package com.sns.like.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Like {
    private int userId;
    private int postId;
    private int filledLike;
    private LocalDateTime createdAt;
}
