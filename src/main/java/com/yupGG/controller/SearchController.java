package com.yupGG.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.http.HttpClient;

@Controller
@RequestMapping("/riot")
public class SearchController {

    @GetMapping("/summoners")
    public String search(@RequestParam(name = "summoners", required = false)String summoners ,
                         @RequestParam(name = "summonersTag", required = false)String summonersTag, Model model) {
        summonersTag ='#'+summonersTag;

        model.addAttribute("summoners",summoners);
        model.addAttribute("summonersTag",summonersTag);
        return "search/searchPage";
    }
}
