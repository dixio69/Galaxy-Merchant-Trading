package com.hehe.gmt.controller;

import com.hehe.gmt.services.GmtService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Log4j2
@Controller
public class HtmlController {
    @Autowired
    private GmtService gmtService;

    @GetMapping("/")
    public String getGameSetting(HttpSession session) {
        return "page/gmt";
    }

    @PostMapping("/")
    public String processSetting(Model model, final HttpServletRequest request, HttpSession session) {
        String[] inputs = request.getParameterValues("input_question");
        model.addAttribute("data", inputs);
        model.addAttribute("answers", gmtService.processSentences(session.getId(), inputs));
        return "page/gmt";
    }
}
