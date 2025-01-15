package com.sns.post.bo;

import com.sns.post.entity.PostEntity;
import com.sns.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PostBO {

    private final PostRepository postRepository;

    public List<PostEntity> getPostList() {
        return postRepository.findAllByOrderByIdDesc();
    }
}
