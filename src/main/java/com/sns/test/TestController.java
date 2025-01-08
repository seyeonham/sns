package com.sns.test;

import com.sns.post.mapper.PostMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class TestController {

    @Autowired
    private PostMapper postMapper;

    @GetMapping("/test1")
    @ResponseBody
    public String test1() {
        return "<h2>Hello world!</h2>";
    }

    @GetMapping("/test2")
    @ResponseBody
    public Map<String, Object> test2() {
        Map<String, Object> map = new HashMap<>();
        map.put("사과",200);
        map.put("복숭아", 42);
        map.put("귤", 98);
        return map;
    }

    @GetMapping("/test3")
    public String test3() {
        return "test/test3";
    }

    @GetMapping("/test4")
    @ResponseBody
    public List<Map<String, Object>> test4() {
        return postMapper.selectPostListTest();
    }
}
