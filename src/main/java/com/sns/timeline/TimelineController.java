package com.sns.timeline;

import com.sns.comment.bo.CommentBO;
import com.sns.comment.domain.Comment;
import com.sns.post.bo.PostBO;
import com.sns.post.entity.PostEntity;
import com.sns.subscribe.bo.SubscribeBO;
import com.sns.subscribe.entity.SubscribeEntity;
import com.sns.timeline.bo.TimelineBO;
import com.sns.timeline.domain.CardDTO;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/timeline")
@Controller
public class TimelineController {

    // private final PostBO postBO;
    // private final CommentBO commentBO;
    private final TimelineBO timelineBO;
    private final SubscribeBO subscribeBO;

    @GetMapping("")
    public String timeline(Model model, HttpSession session) {
        // List<PostEntity> postList = postBO.getPostList();
        // model.addAttribute("postList", postList);

        // List<Comment> commentList = commentBO.getCommentByPostId();
        // model.addAttribute("commentList", commentList);
        Integer fromUserId = (Integer)session.getAttribute("userId");

        List<CardDTO> cardList = timelineBO.generateCardList(fromUserId);
        model.addAttribute("cardList", cardList);

        return "timeline/timeline";
    }


    @GetMapping("/my-post-view")
    public String myPostView(Model model, HttpSession session) {

        Integer myId = (Integer)session.getAttribute("userId");
        if (myId == null) {
            return "redirect:user/sign-in-view";
        }

        List<CardDTO> myCardList = timelineBO.generateMyCardList(myId);
        if (myCardList.size() > 0) {
            model.addAttribute("myCardList", myCardList);
        } else {
            model.addAttribute("myCardList", null);
        }

        return "timeline/myPost";
    }

    @GetMapping("subscribe-post-view")
    public String subscribePostView(Model model, HttpSession session) {

        Integer fromUserId = (Integer)session.getAttribute("userId");
        if (fromUserId == null) {
            return "redirect:/user/sign-in-view";
        }

        List<CardDTO> subscribeCardList = timelineBO.generateSubscribeCardList(fromUserId);
        if (subscribeCardList.size() > 0) {
            model.addAttribute("subscribeCardList", subscribeCardList);
        } else {
            model.addAttribute("subscribeCardList", null);
        }

        return "timeline/subscribePost";
    }

    @GetMapping("/user-post-view")
    public String userPostView(Model model, HttpSession session,
                               @RequestParam("userId") int userId) {

        Integer fromUserId = (Integer)session.getAttribute("userId");
        if (fromUserId == null) {
            return "redirect:/user/sign-in-view";
        }

        List<CardDTO> userCardList = timelineBO.generateUserCardList(userId, fromUserId);
        if (userCardList.size() > 0) {
            model.addAttribute("userCardList", userCardList);
        } else {
            model.addAttribute("userCardList", null);
        }

        return "timeline/userPost";
    }

}
