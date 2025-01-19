package com.sns.subscribe.bo;

import com.sns.subscribe.entity.SubscribeEntity;
import com.sns.subscribe.repository.SubscribeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class SubscribeBO {

    private final SubscribeRepository subscribeRepository;

    public List<SubscribeEntity> getSubscribeByToUserId(int toUserId) {
        return subscribeRepository.findByToUserId(toUserId);
    }

    public SubscribeEntity updateSubscribe(int toUserId, int fromUserId) {
        Optional<SubscribeEntity> existSubscribe =
                subscribeRepository.findByToUserIdAndFromUserId(toUserId, fromUserId);

        if (existSubscribe.isPresent()) {
            // 구독이 존재하면 업데이트
            SubscribeEntity subscribe = existSubscribe.get(); // 존재하는 구독을 가져옵니다.

            // 기존의 값을 유지하면서 deleteYn만 업데이트
            subscribe = subscribe.builder()
                    .id(subscribe.getId()) // 기존 id 유지
                    .toUserId(subscribe.getToUserId()) // 기존 값 유지
                    .fromUserId(subscribe.getFromUserId()) // 기존 값 유지
                    .deleteYn(subscribe.getDeleteYn().equals("Y") ? "N" : "Y") // deleteYn만 변경
                    .build();

            return subscribeRepository.save(subscribe); // 업데이트된 객체 저장 (기존 객체의 id가 유지되어 update로 처리)
        } else {
            // 구독이 존재하지 않으면 새로운 구독 생성 (insert)
            SubscribeEntity subscribe = SubscribeEntity.builder()
                    .toUserId(toUserId)
                    .fromUserId(fromUserId)
                    .deleteYn("N") // 새로 구독하는 경우 deleteYn은 "N"
                    .build();

            return subscribeRepository.save(subscribe); // 새 구독 정보 저장 후 반환
        }
    }
}
