package com.sns.user.bo;

import com.sns.common.EncryptUtils;
import com.sns.user.entity.UserEntity;
import com.sns.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserBO {

    @Autowired
    private UserRepository userRepository;

    // input: loginId
    // output: UserEntity or null
    public UserEntity getUserEntityByLoginId(String loginId) {
         return userRepository.findByLoginId(loginId).orElse(null);
    }

    // input: loginId, password
    // output: UserEntity or null
    public UserEntity getUserEntityByLoginIdPassword(String loginId, String password) {
        // 비밀번호 해싱
        String hashedPassword = EncryptUtils.md5(password);

        // db select
        return userRepository.findByLoginIdAndPassword(loginId, hashedPassword).orElse(null);
    }

    public UserEntity getUserEntityById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    public UserEntity getUserEntityByIdPassword(int userId, String password) {
        // 비밀번호 해싱
        String hashedPassword = EncryptUtils.md5(password);

        // db select
        return userRepository.findByIdAndPassword(userId, hashedPassword).orElse(null);
    }

    // input: 4개 파라미터
    // output: UserEntity or boolean
    public boolean addUser(String loginId, String password, String name, String email) {
        String hashedPassword = EncryptUtils.md5(password);

        UserEntity user = userRepository.save(
                UserEntity.builder()
                        .loginId(loginId)
                        .password(hashedPassword)
                        .name(name)
                        .email(email)
                        .build()
        );

        return user == null ? false : true;
    }

    public UserEntity updateUserEntity(String loginId, String newPassword, String name, String email, int userId) {
        Optional<UserEntity> user = userRepository.findById(userId);

        // 비밀번호 해싱
        String hashedPassword = "";
        if (newPassword != "") {
            hashedPassword = EncryptUtils.md5(newPassword);
        }

        if (user.isPresent()) {
            UserEntity userEntity = user.get();
            userEntity = userEntity.builder()
                    .id(userId)
                    .loginId(loginId != "" ? loginId : userEntity.getLoginId())
                    .password(hashedPassword != "" ? hashedPassword : userEntity.getPassword())
                    .name(name != "" ? name : userEntity.getName())
                    .email(email != "" ? email : userEntity.getEmail())
                    .createdAt(userEntity.getCreatedAt())
                    .build();

            return userRepository.save(userEntity);
        }

        return null;
    }
}
