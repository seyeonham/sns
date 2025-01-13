package com.sns.user;

import com.sns.bo.UserBO;
import com.sns.entity.UserEntity;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("/user")
@RestController
public class UserRestController {

    @Autowired
    private UserBO userBO;

    @GetMapping("/is-duplicate-id")
    public Map<String, Object> isDuplicateId(
            @RequestParam("loginId") String loginId
    ) {
        // DB 조회
        UserEntity user = userBO.getUserEntityByLoginId(loginId);
        boolean isDuplicate = false;
        if (user != null) {
            isDuplicate = true;
        }
        // 응답
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("is_duplicate_id", isDuplicate);
        return result;
    }

    @PostMapping("/sign-up")
    public Map<String, Object> signUp(
            @RequestParam("loginId") String loginId,
            @RequestParam("password") String password,
            @RequestParam("name") String name,
            @RequestParam("email") String email
    ) {

        // DB insert
        boolean isSuccess = userBO.addUser(loginId, password, name, email);

        // 응답값
        Map<String, Object> result = new HashMap<>();
        if (isSuccess) {
            result.put("code", 200);
            result.put("result", "성공");
        } else {
            result.put("code", 500);
            result.put("error_message", "회원가입에 실패했습니다.");
        }

        return result;
    }

    @PostMapping("/sign-in")
    public Map<String, Object> signIn(
            @RequestParam("loginId") String loginId,
            @RequestParam("password") String password,
            HttpSession session
    ) {
        // db select
        UserEntity user = userBO.getUserEntityByLoginIdPassword(loginId, password);

        // response
        Map<String, Object> result = new HashMap<>();
        if (user != null) {
            session.setAttribute("userId", user.getId());
            session.setAttribute("userLoginId", user.getLoginId());
            session.setAttribute("userName", user.getName());

            result.put("code", 200);
            result.put("result", "성공");
        } else {
            result.put("code", 300);
            result.put("error_message", "아이디나 비밀번호가 틀렸습니다.");
        }
        return result;
    }
}
