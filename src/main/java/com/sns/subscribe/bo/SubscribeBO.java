package com.sns.subscribe.bo;

import com.sns.subscribe.entity.SubscribeEntity;
import com.sns.subscribe.repository.SubscribeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SubscribeBO {

    private final SubscribeRepository subscribeRepository;

    public List<SubscribeEntity> getSubscribe() {
        return subscribeRepository.findAll();
    }
}
