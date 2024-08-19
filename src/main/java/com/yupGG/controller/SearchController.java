package com.yupGG.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yupGG.dto.*;
import com.yupGG.dto.MatchDto;
import com.yupGG.dto.match.InfoDto;
import com.yupGG.dto.match.info.ParticipantDto;
import com.yupGG.service.MatchService;
import com.yupGG.service.SummonerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/riot")
public class SearchController {
    private final SummonerService summonerService;
    private final MatchService matchService;

    public SearchController(SummonerService summonerService, MatchService matchService) {
        this.summonerService = summonerService;
        this.matchService = matchService;
    }

    private  final Logger logger = LoggerFactory.getLogger(SearchController.class);

    @GetMapping("/summoners")
    public String search(@RequestParam(name = "summoners", required = false)String summoners ,
                         @RequestParam(name = "summonersTag", required = false)String summonersTag, Model model) throws JsonProcessingException {

        PuuidDto puuidDto = new PuuidDto();
        SummonerDTO summonerDTO = new SummonerDTO();
        List<LeagueEntryDto> leagueEntryDto = new ArrayList<>();


        if (summoners != null && !summoners.isEmpty()) {
            puuidDto = summonerService.getPuuid(summoners, summonersTag);
        }

        String puuid = puuidDto.getPuuid();
        System.out.println("puuid : " + puuid);

        if (puuid != null && !puuid.isEmpty()) {
            summonerDTO = summonerService.getSummonerByPuuid(puuid);
        }

        String summonerId = summonerDTO.getId();

        if (summonerId != null && !summonerId.isEmpty()) {
            leagueEntryDto = summonerService.getSummonerRank(summonerId);
        }

        List<MatchDto> matchDtos = matchService.getMatch(matchService.getMatchId(puuid));



        for (MatchDto matchDto : matchDtos) {
            InfoDto info = matchDto.getInfoDto();
            for (ParticipantDto participant : info.getParticipants()) {
                if (puuid.equals(participant.getPuuid())) {
                    System.out.println("게임 ID: " + info.getGameId());
                    // 필요한 다른 정보도 출력할 수 있습니다.
                }
            }
        }
        System.out.println("SummonerID" + summonerDTO.getId());
        System.out.println("GameName" + puuidDto.getGameName());
        System.out.println("SummonerLevel" + summonerDTO.getSummonerLevel());
        System.out.println(leagueEntryDto);

        model.addAttribute("summoners", summonerDTO);
        model.addAttribute("puuidDto", puuidDto);

        if (!leagueEntryDto.isEmpty()) {
            model.addAttribute("leagueEntryDto", leagueEntryDto.get(0));
        } else {
            model.addAttribute("leagueEntryDto", null);
        }

        return "search/searchPage";
    }
}
