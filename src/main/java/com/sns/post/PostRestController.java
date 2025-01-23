package com.sns.post;

import com.sns.post.bo.PostBO;
import com.sns.post.entity.PostEntity;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/post")
@RestController
public class PostRestController {

    private final PostBO postBO;

    @PostMapping("/create")
    public Map<String, Object> create(
            @RequestParam(value = "content", required = false) String content,
            @RequestParam("file") MultipartFile file,
            HttpSession session) {

        Integer userId = (Integer) session.getAttribute("userId");
        String userLoginId = (String)session.getAttribute("userLoginId");

        Map<String, Object> result = new HashMap<>();
        if (userId == null) {
            result.put("code", 403);
            result.put("error_message", "로그인을 해주세요.");
            return result;
        }
        // DB insert
        PostEntity post = postBO.addPost(userId, userLoginId, file, content);

        // 응답값
        if (post != null) {
            result.put("code", 200);
            result.put("result", "성공");
        } else {
            // 파일 업로드 실패
            result.put("code", 500);
            result.put("error_message", "글을 쓰는데 실패했습니다.");
        }
        return result;
    }

    @DeleteMapping("/delete")
    public Map<String, Object> delete(
            @RequestParam("postId") int postId,
            HttpSession session
    ) {

        Integer userId = (Integer)session.getAttribute("userId");
        Map<String, Object> result = new HashMap<>();
        if (userId == null) {
            result.put("code", 403);
            result.put("error_message", "로그인을 해주세요.");
            return result;
        }

        // DB delete
        postBO.deletePost(postId);

        // 응답값
        result.put("code", 200);
        result.put("result", "성공");
        return result;
    }
}
