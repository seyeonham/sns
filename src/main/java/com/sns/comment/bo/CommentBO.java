package com.sns.comment.bo;

import com.sns.comment.domain.Comment;
import com.sns.comment.mapper.CommentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentBO {

    private final CommentMapper commentMapper;

    public int addComment(String content, int postId, int userId) {
        return commentMapper.insertComment(content, postId, userId);
    }

    public List<Comment> getCommentByPostId() {
        return commentMapper.selectCommentByPostId();
    }
}
