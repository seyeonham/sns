package com.sns.subscribe.repository;

import com.sns.subscribe.entity.SubscribeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscribeRepository extends JpaRepository<SubscribeEntity, Integer> {
}
