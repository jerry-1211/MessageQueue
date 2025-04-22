package com.example.messagequeue.step3.step3;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/step3")
public class HomeController {

    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("message", "Welcome to RabbitMQ Sample!");
        return "step3/index";
    }

    @GetMapping("/news")
    public String news(Model model){
        model.addAttribute("message", "Welcome to RabbitMQ News Sample!");
        return "step3/news";
    }
}
