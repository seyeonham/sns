package com.sns.user.repository;

import com.sns.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    public Optional<UserEntity> findByLoginId(String loginId);
    public Optional<UserEntity> findByLoginIdAndPassword(String loginId, String password);
}
