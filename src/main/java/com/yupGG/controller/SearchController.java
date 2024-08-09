package com.yupGG.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yupGG.config.RiotHttpClient;
import com.yupGG.dto.PuuidDto;
import com.yupGG.dto.ResponseDto;
import com.yupGG.dto.StatusDto;
import com.yupGG.dto.SummonerDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URLEncoder;
import java.net.http.HttpClient;

@Controller
@RequestMapping("/riot")
public class SearchController {
    private  final Logger logger = LoggerFactory.getLogger(SearchController.class);

    @GetMapping("/summoners")
    public String search(@RequestParam(name = "summoners", required = false)String summoners ,
                         @RequestParam(name = "summonersTag", required = false)String summonersTag, Model model) throws JsonProcessingException {

        ResponseDto responseDto = null;
        SummonerDTO summonerDTO = new SummonerDTO();
        PuuidDto puuidDto = new PuuidDto();
        StatusDto statusDto = new StatusDto();
        if ("".equals(summoners) == false) {
            responseDto = new RiotHttpClient().getPuuid(summoners, summonersTag);

            statusDto = responseDto.getStatus();
            if (responseDto.isOK()) {
                ObjectMapper mapper = new ObjectMapper();
                puuidDto = mapper.readValue(responseDto.getResponseBody(), PuuidDto.class);
            }
        }
        String puuid = puuidDto.getPuuid();
        System.out.println(puuid);

        model.addAttribute("summoners",summoners);
        model.addAttribute("summonersTag",summonersTag);
        model.addAttribute("summonersTag",puuid);
        return "search/searchPage";
    }
}
