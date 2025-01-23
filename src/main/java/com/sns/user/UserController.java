package com.sns.user;

import com.sns.user.bo.UserBO;
import com.sns.user.entity.UserEntity;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@RequestMapping("/user")
@Controller
public class UserController {

    private final UserBO userBO;

    @GetMapping("/sign-up-view")
    public String signUpView() {
        return "user/signUp";
    }

    @GetMapping("/sign-in-view")
    public String signInView() {
        return "user/signIn";
    }

    @GetMapping("/sign-out")
    public String signOut(
            HttpSession session
    ) {
        session.removeAttribute("userId");
        session.removeAttribute("userLoginId");
        session.removeAttribute("userName");

        return "redirect:/user/sign-in-view";
    }

    @GetMapping("/edit-view")
    public String editView(
            @RequestParam(value = "userId", required = false) Integer userId,
            HttpSession session, Model model) {

        if (userId == null) {
            return "redirect:/user/sign-in-view";
        }

        UserEntity user = userBO.getUserEntityById(userId);
        model.addAttribute("user", user);

        return "user/edit";
    }
}
