package com.sns.subscribe.repository;

import com.sns.subscribe.entity.SubscribeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SubscribeRepository extends JpaRepository<SubscribeEntity, Integer> {
    public List<SubscribeEntity> findByToUserId(int toUserId);

    public Optional<SubscribeEntity> findByToUserIdAndFromUserId(int toUserId, int fromUserId);
}
