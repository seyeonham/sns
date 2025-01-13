package com.sns.timeline;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/timeline")
@Controller
public class TimelineController {

    @GetMapping("")
    public String timeline() {
        return "timeline/timeline";
    }
}
