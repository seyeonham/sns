package com.sns.comment;

import com.sns.comment.bo.CommentBO;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/comment")
@RestController
public class CommentRestController {

    private final CommentBO commentBO;

    // TODO Post로 바꿀 예정
    @GetMapping ("/create")
    public Map<String, Object> create(
            @RequestParam("content") String content,
            @RequestParam("postId") int postId,
            HttpSession session
    ) {
        // DB insert
        Integer userId = (Integer)session.getAttribute("userId");

        Map<String, Object> result = new HashMap<>();
        if (userId == null) {
            result.put("code", 403);
            result.put("error_message", "로그인을 해주세요.");
            return result;
        }

        int rowCount = commentBO.addComment(content, postId, userId);

        // 응답값
        if (rowCount > 0) {
            result.put("code", 200);
            result.put("result", "성공");
        } else {
            result.put("code", 500);
            result.put("error_message", "댓글을 작성하는데 실패했습니다.");
        }
        return result;
    }
}
