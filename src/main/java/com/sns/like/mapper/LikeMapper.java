package com.sns.like.mapper;

import com.sns.like.domain.Like;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Mapper
public interface LikeMapper {

    // select
    public Optional<Like> selectLikeByUserIdPostId(
            @Param("userId") int userId,
            @Param("postId") int postId);

    public int selectLikeCountByPostId(int postId);

    public int selectLikeCountByUserIdPostId(
            @Param("userId") int userId,
            @Param("postId") int postId);

    // insert
    public int insertLikeByUserIdPostId(
            @Param("userId") int userId,
            @Param("postId") int postId,
            @Param("filledLike") int filledLike);

    // update
    public int updateLikeByUserIdPostId(
            @Param("userId") int userId,
            @Param("postId") int postId,
            @Param("filledLike") int filledLike
    );
}
