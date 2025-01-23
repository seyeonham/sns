package com.sns.user;

import com.sns.user.bo.UserBO;
import com.sns.user.entity.UserEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
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
            @RequestParam("loginId") String loginId,
            HttpSession session
    ) {

        Integer userId = (Integer)session.getAttribute("userId");
        // DB 조회
        UserEntity user = userBO.getUserEntityByLoginId(loginId);
        boolean isDuplicate = false;
        boolean isSame = false;
        if (user != null) {
            UserEntity sessionUser = userBO.getUserEntityById(userId);
            if (sessionUser.getLoginId().equals(loginId)) {
                isSame = true;
            } else {
                isDuplicate = true;
            }
        }

        // 응답값
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("is_duplicate_id", isDuplicate);
        result.put("is_same", isSame);
        return result;
    }

    @GetMapping("/check-password")
    public Map<String, Object> checkPassword(
            @RequestParam("password") String password,
            HttpSession session
    ) {
        Integer userId = (Integer)session.getAttribute("userId");
        Map<String, Object> result = new HashMap<>();
        if (userId == null) {
            result.put("code", 403);
            result.put("error_message", "로그인을 해주세요.");
            return result;
        }

        boolean checkPassword = false;
        UserEntity user = userBO.getUserEntityByIdPassword(userId, password);
        if (user != null) {
            checkPassword = true;
        }
        result.put("code", 200);
        result.put("check_password", checkPassword);
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

    @PostMapping("/edit")
    public Map<String, Object> edit(
            @RequestParam("id") int id,
            @RequestParam(value = "newPassword", required = false) String newPassword,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "email", required = false) String email,
            HttpSession session
    ) {

        Integer userId = (Integer)session.getAttribute("userId");
        Map<String, Object> result = new HashMap<>();
        if (userId == null) {
            result.put("code", 403);
            result.put("error_message", "로그인을 해주세요.");
            return result;
        }

        return result;
    }
}
