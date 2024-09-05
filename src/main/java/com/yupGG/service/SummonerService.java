package com.yupGG.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yupGG.config.RiotHttpClient;
import com.yupGG.dto.*;
import com.yupGG.entity.Summoner;
import com.yupGG.repository.SummonerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Service
public class SummonerService {
    private final RiotHttpClient riotHttpClient;
    private final ObjectMapper objectMapper;
    @Autowired
    private SummonerRepository summonerRepository;
    @Value("${riot.url}")
    private String riotUrl;
    private String riotAsiaUrl = "https://asia.api.riotgames.com";

    public SummonerService(RiotHttpClient riotHttpClient, ObjectMapper objectMapper) throws JsonMappingException {
        this.riotHttpClient = riotHttpClient;
        this.objectMapper = objectMapper;
    }

    public PuuidDto getPuuid(String summoners, String summonersTag) throws JsonProcessingException, UnsupportedEncodingException {
        String encodedSummoners = URLEncoder.encode(summoners, "UTF-8").replace("+", "%20"); // +을 %20으로 변환
        String encodedSummonersTag = URLEncoder.encode(summonersTag, "UTF-8").replace("+", "%20"); // +을 %20으로 변환
        PuuidDto puuidDto = new PuuidDto();
        StringBuilder sb = new StringBuilder("/riot/account/v1/accounts/by-riot-id/" + encodedSummoners + "/" + encodedSummonersTag);
        // 디버깅
        System.out.println("Request URL: " + riotAsiaUrl + sb.toString());

        ResponseDto responseDto = riotHttpClient.getRiotResponse(riotAsiaUrl,sb.toString());
        // 응답 본문 로그
        System.out.println("Response Body: " + responseDto.getResponseBody());
        if (responseDto.isOK()) {
            puuidDto = objectMapper.readValue(responseDto.getResponseBody(), PuuidDto.class);
        }
        return puuidDto;
    }

    public SummonerDTO getSummonerByPuuid(String puuid) throws JsonProcessingException {
        SummonerDTO summonerDTO = new SummonerDTO();
        StringBuilder sb = new StringBuilder("/lol/summoner/v4/summoners/by-puuid/" + puuid);
        ResponseDto responseDto = riotHttpClient.getRiotResponse(riotUrl,sb.toString());
        if (responseDto.isOK()) {
            summonerDTO = objectMapper.readValue(responseDto.getResponseBody(), SummonerDTO.class);
        }
        return summonerDTO;
    }


    public List<LeagueEntryDto> getSummonerRank(String summonerId) throws JsonProcessingException {
        List<LeagueEntryDto> leagueEntryDtoList = new ArrayList<>();
        StringBuilder sb = new StringBuilder("/lol/league/v4/entries/by-summoner/"+summonerId);
        System.out.println("랭크 겟 : "+sb.toString());
        ResponseDto responseDto = riotHttpClient.getRiotResponse(riotUrl, sb.toString());

        if (responseDto.isOK()) {
            String jsonInput = responseDto.getResponseBody();
            leagueEntryDtoList = objectMapper.readValue(jsonInput, new TypeReference<List<LeagueEntryDto>>() {});
        }
        return leagueEntryDtoList;
    }


}
