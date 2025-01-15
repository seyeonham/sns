package com.sns.timeline;

import com.sns.post.bo.PostBO;
import com.sns.post.entity.PostEntity;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/timeline")
@Controller
public class TimelineController {

    private final PostBO postBO;

    @GetMapping("")
    public String timeline(Model model, HttpSession session) {
        List<PostEntity> postList = postBO.getPostList();
        model.addAttribute("postList", postList);
        return "timeline/timeline";
    }

}
