package com.sns.timeline.bo;

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

    // input: X
    // output: List<CardDTO>
    public List<CardDTO> generateCardList(Integer fromUserId) {
        List<CardDTO> cardList = new ArrayList<>();

        // 글 목록 가져옴
        List<PostEntity> postList = postBO.getPostList();

        // 반복문 => PostEntity -> CardDTO     => 리스트에 넣는다.
        for (PostEntity postEntity : postList) {
            CardDTO cardDTO = new CardDTO();
            cardDTO.setPost(postEntity);

            int userId = postEntity.getUserId();
            UserEntity user = userBO.getUserEntityById(userId);
            cardDTO.setUser(user);

            SubscribeEntity subscribe = subscribeBO.getSubscribeByToUserIdFromUserId(userId, fromUserId).orElse(null);
            cardDTO.setSubscribe(subscribe);

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
            cardDTO.setPost(postEntity);

            int userId = postEntity.getUserId();
            UserEntity user = userBO.getUserEntityById(userId);
            cardDTO.setUser(user);

            cardList.add(cardDTO);
        }

        return cardList;
    }
}
