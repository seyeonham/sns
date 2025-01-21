package com.sns.timeline.bo;

import com.sns.comment.bo.CommentBO;
import com.sns.comment.domain.CommentDTO;
import com.sns.like.bo.LikeBO;
import com.sns.like.domain.Like;
import com.sns.post.bo.PostBO;
import com.sns.post.entity.PostEntity;
import com.sns.subscribe.bo.SubscribeBO;
import com.sns.subscribe.entity.SubscribeEntity;
import com.sns.timeline.domain.CardDTO;
import com.sns.user.bo.UserBO;
import com.sns.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class TimelineBO {

    private final PostBO postBO;
    private final UserBO userBO;
    private final SubscribeBO subscribeBO;
    private final CommentBO commentBO;
    private final LikeBO likeBO;

    // input: X
    // output: List<CardDTO>
    public List<CardDTO> generateCardList(Integer fromUserId) {
        List<CardDTO> cardList = new ArrayList<>();

        // 글 목록 가져옴
        List<PostEntity> postList = postBO.getPostList();

        // 반복문 => PostEntity -> CardDTO     => 리스트에 넣는다.
        for (PostEntity postEntity : postList) {
            CardDTO cardDTO = new CardDTO();

            // 글 1개
            cardDTO.setPost(postEntity);

            // 글쓴이
            int userId = postEntity.getUserId();
            UserEntity user = userBO.getUserEntityById(userId);
            cardDTO.setUser(user);

            // 구독
            if (fromUserId != null) {
                SubscribeEntity subscribe = subscribeBO.getSubscribeByToUserIdFromUserId(userId, fromUserId).orElse(null);
                cardDTO.setSubscribe(subscribe);
            }

            // 댓글 N개
            int postId = postEntity.getId();
            List<CommentDTO> commentList = commentBO.generateCommentListByPostId(postId);
            cardDTO.setCommentList(commentList);

            // 좋아요 개수
            int likeCount = likeBO.getLikeCountByPostId(postId);
            cardDTO.setLikeCount(likeCount);

            // 좋아요 하트 여부
            if (fromUserId != null) {
                boolean filledLike = likeBO.getLikeCountByUserIdPostIdFilledLike(userId, postId);
                cardDTO.setFilledLike(filledLike);
            }

            // !!!!!!!!!!!!! list에 꼭 담기
            cardList.add(cardDTO);
        }

        return cardList;
    }

    public List<CardDTO> generateMyCardList(int myId) {
        List<CardDTO> cardList = new ArrayList<>();

        // 글 목록 가져옴
        List<PostEntity> myPostList = postBO.getPostListByUserId(myId);

        for (PostEntity postEntity : myPostList) {
            CardDTO cardDTO = new CardDTO();
            
            // 글 1개
            cardDTO.setPost(postEntity);

            // 글쓴이
            int userId = postEntity.getUserId();
            UserEntity user = userBO.getUserEntityById(userId);
            cardDTO.setUser(user);

            // 댓글 N개
            int postId = postEntity.getId();
            List<CommentDTO> commentList = commentBO.generateCommentListByPostId(postId);
            cardDTO.setCommentList(commentList);

            cardList.add(cardDTO);
        }

        return cardList;
    }

    public List<CardDTO> generateSubscribeCardList(int fromUserId) {
        List<CardDTO> cardList = new ArrayList<>();

        // 구독 목록 가져옴
        List<SubscribeEntity> subscribeList = subscribeBO.getSubscribeByFromUserId(fromUserId, "N");

        // 글 목록 가져옴
        List<PostEntity> postList = postBO.getPostList();

        for (PostEntity postEntity : postList) {
            CardDTO cardDTO = new CardDTO();

            for (SubscribeEntity subscribeEntity : subscribeList) {
                int postUserId = postEntity.getUserId();
                int toUserId = subscribeEntity.getToUserId();
                if (toUserId == postUserId) {
                    cardDTO.setPost(postEntity);

                    // 글쓴이
                    int userId = postEntity.getUserId();
                    UserEntity user = userBO.getUserEntityById(userId);
                    cardDTO.setUser(user);

                    // 댓글 N개
                    int postId = postEntity.getId();
                    List<CommentDTO> commentList = commentBO.generateCommentListByPostId(postId);
                    cardDTO.setCommentList(commentList);

                    // 구독
                    SubscribeEntity subscribe = subscribeBO.getSubscribeByToUserIdFromUserId(userId, fromUserId).orElse(null);
                    cardDTO.setSubscribe(subscribe);

                    // 좋아요

                    cardList.add(cardDTO);
                }
            }
        }

        return cardList;
    }

    public List<CardDTO> generateUserCardList(int userId, int fromUserId) {
        List<CardDTO> cardList = new ArrayList<>();

        // 글 목록 가져옴
        List<PostEntity> myPostList = postBO.getPostListByUserId(userId);

        for (PostEntity postEntity : myPostList) {
            CardDTO cardDTO = new CardDTO();

            // 글 1개
            cardDTO.setPost(postEntity);

            // 글쓴이
            int postUserId = postEntity.getUserId();
            UserEntity user = userBO.getUserEntityById(postUserId);
            cardDTO.setUser(user);

            // 댓글 N개
            int postId = postEntity.getId();
            List<CommentDTO> commentList = commentBO.generateCommentListByPostId(postId);
            cardDTO.setCommentList(commentList);

            // 구독
            SubscribeEntity subscribe = subscribeBO.getSubscribeByToUserIdFromUserId(userId, fromUserId).orElse(null);
            cardDTO.setSubscribe(subscribe);

            cardList.add(cardDTO);
        }

        return cardList;
    }

}
