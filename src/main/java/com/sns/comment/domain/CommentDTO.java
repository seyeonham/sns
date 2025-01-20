package com.sns.comment.domain;

import com.sns.user.entity.UserEntity;
import lombok.Data;

@Data
public class CommentDTO {
    // 댓글 객체
    private Comment comment;

    // 댓글쓴이
    private UserEntity user;
}
