package com.sns.post.bo;

import com.sns.comment.bo.CommentBO;
import com.sns.common.FileManagerService;
import com.sns.like.bo.LikeBO;
import com.sns.post.entity.PostEntity;
import com.sns.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostBO {

    private final PostRepository postRepository;
    private final FileManagerService fileManager;
    private final LikeBO likeBO;
    private final CommentBO commentBO;

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

    // transaction - 같은 로직을 한 transaction으로 묶음.
    // 하나가 delete가 되었는데 다른 것이 delete가 되지 않는다면 문제가 될 수 있음.
    // 따라서 transaction 어노테이션 사용. 하나가 오류가 난다면 전체 오류.
    @Transactional
    public void deletePost(int postId) {

        PostEntity post = postRepository.findById(postId);
        if (post == null) {
            log.warn("[### 글 삭제] post is null. postId:{}", postId);
            return;
        }

        // 이미지 삭제
        fileManager.deleteFile(post.getImagePath());

        // 좋아요 삭제
        likeBO.deleteLikeByPostId(postId);

        // 댓글 삭제
        commentBO.deleteCommentByPostId(postId);

        // 글 삭제
        postRepository.delete(post);
    }
}
