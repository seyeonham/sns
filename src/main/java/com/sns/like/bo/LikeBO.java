package com.sns.like.bo;

import com.sns.like.domain.Like;
import com.sns.like.mapper.LikeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class LikeBO {

    private final LikeMapper likeMapper;

    // insert or update
    public int updateLike(int userId, int postId) {
        Optional<Like> existLike = likeMapper.selectLikeByUserIdPostId(userId, postId);
        int rowCount = 0;
        if (existLike.isPresent()) {
            Like like = existLike.get();
            if (like.getFilledLike() > 0) {
                rowCount = likeMapper.updateLikeByUserIdPostId(userId, postId, 0);
            } else {
                rowCount = likeMapper.updateLikeByUserIdPostId(userId, postId, 1);
            }
        } else {
            rowCount = likeMapper.insertLikeByUserIdPostId(userId, postId, 1);
        }

        return rowCount;
    }

    // select count
    public int getLikeCountByPostId(int postId) {
        return likeMapper.selectLikeCountByPostId(postId);
    }

    public boolean getLikeCountByUserIdPostIdFilledLike(int userId, int postId) {
        int filledLike = likeMapper.selectLikeCountByUserIdPostId(userId, postId);
        if (filledLike > 0) {
            return true;
        } else {
            return false;
        }
    }
}
