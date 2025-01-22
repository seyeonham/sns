package com.sns.timeline.domain;

import com.sns.comment.domain.CommentDTO;
import com.sns.post.entity.PostEntity;
import com.sns.subscribe.entity.SubscribeEntity;
import com.sns.user.entity.UserEntity;
import lombok.Data;

import java.util.List;

// 글 1개 == 카드 1개
@Data
public class CardDTO {
    // 글 1개
    private PostEntity post;

    // 글쓴이 정보
    private UserEntity user;

    // 댓글 N개
    private List<CommentDTO> commentList;

    // 좋아요 N개 -> 숫자
    private int likeCount;

    // 좋아요 하트 여부
    private boolean filledLike;

    // 좋아요한 유저
    private List<UserEntity> likeUserList;

    // 구독, 구독해제
    private SubscribeEntity subscribe;
}
