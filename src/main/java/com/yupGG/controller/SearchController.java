package com.yupGG.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.http.HttpClient;

@Controller
public class SearchController {
    @Value("${riot.api.key}")
    private String riotApiKey;
    private String riotUrl = "https://kr.api.riotgames.com";
    // 라이엇 개발자 홈페이지 ACCOUNT-V! -> 2번째꺼 gameName,tagLine
// /riot/account/v1/accounts/by-riot-id/{gameName}/{tagLine}
    @GetMapping("/summoners")
    public String search(@RequestParam(name = "summoners", required = false)String summoners ,
                         @RequestParam(name = "summonersTag", required = false)String summonersTag, Model model) {
        summonersTag ='#'+summonersTag;
        String riotIdUrl = riotUrl+"/riot/account/v1/accounts/by-riot-id/"+summoners+"/"+summonersTag;

        model.addAttribute("summoners",summoners);
        model.addAttribute("summonersTag",summonersTag);
        return "search/searchPage";
    }
}
