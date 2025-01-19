package com.sns.subscribe;

import com.sns.subscribe.bo.SubscribeBO;
import com.sns.subscribe.entity.SubscribeEntity;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/subscribe")
@RestController
public class SubscribeRestController {

    private final SubscribeBO subscribeBO;

    @PatchMapping("/user")
    public Map<String, Object> user(
            @RequestParam("toUserId") int toUserId,
            HttpSession session) {

        // 로그인 여부 확인
        Integer fromUserId = (Integer)session.getAttribute("userId");
        Map<String, Object> result = new HashMap<>();
        if (fromUserId == null) {
            result.put("code", 403);
            result.put("error_message", "로그인을 해주세요.");
            return result;
        }

        SubscribeEntity subscribe = subscribeBO.updateSubscribe(toUserId, fromUserId);
        if (subscribe != null) {
            result.put("code", 200);
            result.put("result", "성공");
        } else {
            result.put("code", 500);
            result.put("error_message", "팔로우 과정에 오류가 발생하였습니다.");
        }
        return result;
    }
}
