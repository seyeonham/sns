package com.sns.timeline.bo;

import com.sns.post.bo.PostBO;
import com.sns.post.entity.PostEntity;
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

    // input: X
    // output: List<CardDTO>
    public List<CardDTO> generateCardList() {
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

            // !!!!!!!!!!!!! list에 꼭 담기
            cardList.add(cardDTO);
        }

        return cardList;
    }
}
