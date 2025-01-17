package com.sns.timeline.domain;

import com.sns.post.entity.PostEntity;
import com.sns.user.entity.UserEntity;
import lombok.Data;

// 글 1개 == 카드 1개
@Data
public class CardDTO {
    // 글 1개
    private PostEntity post;

    // 글쓴이 정보
    private UserEntity user;

    // 댓글 N개

    // 좋아요 N개 -> 숫자

}
