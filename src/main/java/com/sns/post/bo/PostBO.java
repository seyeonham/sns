package com.sns.post.bo;

import com.sns.common.FileManagerService;
import com.sns.post.entity.PostEntity;
import com.sns.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PostBO {

    private final PostRepository postRepository;
    private final FileManagerService fileManager;

    public List<PostEntity> getPostList() {
        return postRepository.findAllByOrderByIdDesc();
    }

    public List<PostEntity> getPostListByUserId(int userId) {
        return postRepository.findByUserIdOrderByIdDesc(userId);
    }

    // input: content, file, userId, userLoginId
    // output: PostEntity or 파일 업로드 실패
    public PostEntity addPost(int userId, String userLoginId, MultipartFile file, String content) {
        String imagePath = fileManager.uploadFile(file, userLoginId);

        if (imagePath == null) { // 파일 업로드 실패
            return null;
        }

        PostEntity post = PostEntity.builder()
                .userId(userId)
                .imagePath(imagePath)
                .content(content)
                .build();

        // save는 실패하면 null이 아니라 exception이다.(그 처리는 생략했음)
        return postRepository.save(post);
    }
}
