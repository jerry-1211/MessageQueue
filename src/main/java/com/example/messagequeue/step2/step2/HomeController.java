package com.example.messagequeue.step2.step2;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/step2/home")
    public String home(Model model){
        model.addAttribute("message", "Welcome to RabbitMQ Sample!");
        return "step2/index";
    }
}
