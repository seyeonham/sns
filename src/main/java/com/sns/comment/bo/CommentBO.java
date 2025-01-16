package com.sns.comment.bo;

import com.sns.comment.mapper.CommentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CommentBO {

    private final CommentMapper commentMapper;

    public int addComment(String content, int postId, int userId) {
        return commentMapper.insertComment(content, postId, userId);
    }
}
