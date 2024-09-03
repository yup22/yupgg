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

    public PuuidDto getPuuid(String summoners, String summonersTag) throws JsonProcessingException {
        PuuidDto puuidDto = new PuuidDto();
        StringBuilder sb = new StringBuilder("/riot/account/v1/accounts/by-riot-id/" + summoners + "/" + summonersTag);
        ResponseDto responseDto = riotHttpClient.getRiotResponse(riotAsiaUrl,sb.toString());
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
        ResponseDto responseDto = riotHttpClient.getRiotResponse(riotUrl, sb.toString());

        if (responseDto.isOK()) {
            String jsonInput = responseDto.getResponseBody();
            leagueEntryDtoList = objectMapper.readValue(jsonInput, new TypeReference<List<LeagueEntryDto>>() {});
        }
        return leagueEntryDtoList;
    }

    /*public void updateSummoner(String summonerId, Integer profileIconId, long summonerLevel, String gameName, String tagLine) {
        summonerRepository.updateSummoner(summonerId, profileIconId, summonerLevel, gameName, tagLine);
    }*/
}
