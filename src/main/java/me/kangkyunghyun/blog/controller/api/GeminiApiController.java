package me.kangkyunghyun.blog.controller.api;

import me.kangkyunghyun.blog.service.GeminiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GeminiApiController {

    @Autowired
    private GeminiService geminiService;

    @GetMapping("/api/gemini/summary")
    public String getSummary(@RequestParam String text) {
        return geminiService.getSummary(text);
    }

    @GetMapping("/api/gemini/reply")
    public String generateReply(@RequestParam String text) {
        return geminiService.getSummary(text);
    }
}
