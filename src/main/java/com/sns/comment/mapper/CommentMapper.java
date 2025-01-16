package com.sns.comment.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CommentMapper {
    public int insertComment(
            @Param("content") String content,
            @Param("postId") int postId,
            @Param("userId") int userId);
}
