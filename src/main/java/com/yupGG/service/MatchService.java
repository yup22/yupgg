package com.yupGG.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yupGG.config.RiotHttpClient;

import com.yupGG.dto.ResponseDto;
import com.yupGG.dto.MatchDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MatchService {
    private final RiotHttpClient riotHttpClient;
    private final ObjectMapper objectMapper;

    @Value("${riot.url}")
    private String riotUrl;
    private String riotAsiaUrl = "https://asia.api.riotgames.com";

    public MatchService(RiotHttpClient riotHttpClient, ObjectMapper objectMapper) throws JsonMappingException {
        this.riotHttpClient = riotHttpClient;
        this.objectMapper = objectMapper;
    }

    public List<String> getMatchId(String puuid) throws JsonProcessingException {
        List<String> MatchIdList = new ArrayList<>();
        StringBuilder sb = new StringBuilder("/lol/match/v5/matches/by-puuid/"+puuid+"/ids");
        ResponseDto responseDto = riotHttpClient.getRiotResponse(riotAsiaUrl, sb.toString());

        if (responseDto.isOK()) {
            String jsonInput = responseDto.getResponseBody();
            MatchIdList = objectMapper.readValue(jsonInput, new TypeReference<List<String>>() {});
        }
        return MatchIdList;
    }

    public List<MatchDto> getMatch(List<String> MatchIdList) throws JsonProcessingException {
        List<MatchDto> matchDtoList = new ArrayList<>();
        for (String id : MatchIdList) {
            StringBuilder sb = new StringBuilder("/lol/match/v5/matches/"+id);
            ResponseDto responseDto = riotHttpClient.getRiotResponse(riotAsiaUrl,sb.toString());
            if (responseDto.isOK()) {
                matchDtoList.add(objectMapper.readValue(responseDto.getResponseBody(), MatchDto.class));
            }else {
                // 예외 처리: 실패한 경우 로그를 남기거나, 계속 진행할지 결정
                System.err.println("Failed to retrieve match: " + id);
            }
        }
        return matchDtoList;
    }
}
