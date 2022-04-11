package com.hehe.gmt.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Log4j2
@Controller
public class HtmlController {

    @GetMapping("/")
    public String getGameSetting(HttpSession session) {
        return "page/gmt";
    }

    @PostMapping("/")
    public String processSetting(Model model, final HttpServletRequest request) {
        String[] inputs = request.getParameterValues("input_question");
        model.addAttribute("data", inputs);
        return "page/gmt";
    }
}
