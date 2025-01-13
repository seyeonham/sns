package com.sns.user;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/user")
@Controller
public class UserController {

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
}
