package com.sns.comment.mapper;

import com.sns.comment.domain.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommentMapper {
    public int insertComment(
            @Param("content") String content,
            @Param("postId") int postId,
            @Param("userId") int userId);

    public List<Comment> selectCommentByPostId(int postId);

    public int deleteCommentById(int id);
}
