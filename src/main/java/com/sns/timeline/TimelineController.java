package com.sns.timeline;

import com.sns.comment.bo.CommentBO;
import com.sns.comment.domain.Comment;
import com.sns.post.bo.PostBO;
import com.sns.post.entity.PostEntity;
import com.sns.timeline.bo.TimelineBO;
import com.sns.timeline.domain.CardDTO;
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

    // private final PostBO postBO;
    // private final CommentBO commentBO;
    private final TimelineBO timelineBO;

    @GetMapping("")
    public String timeline(Model model, HttpSession session) {
        // List<PostEntity> postList = postBO.getPostList();
        // model.addAttribute("postList", postList);

        // List<Comment> commentList = commentBO.getCommentByPostId();
        // model.addAttribute("commentList", commentList);

        List<CardDTO> cardList = timelineBO.generateCardList();
        model.addAttribute("cardList", cardList);
        return "timeline/timeline";
    }

}
