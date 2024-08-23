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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/riot")
public class SearchController {
    private final SummonerService summonerService;
    private final MatchService matchService;

    public SearchController(SummonerService summonerService, MatchService matchService) {
        this.summonerService = summonerService;
        this.matchService = matchService;
    }

    private final Logger logger = LoggerFactory.getLogger(SearchController.class);

    @GetMapping("/summoners")
    public String search(@RequestParam(name = "summoners", required = false) String summoners,
                         @RequestParam(name = "summonersTag", required = false) String summonersTag, Model model) throws JsonProcessingException {

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

        model.addAttribute("summoners", summonerDTO);
        model.addAttribute("puuidDto", puuidDto);
        model.addAttribute("puuid", puuid);

        if (!leagueEntryDto.isEmpty()) {
            model.addAttribute("leagueEntryDto", leagueEntryDto.get(0));
        } else {
            model.addAttribute("leagueEntryDto", null);
        }

        return "search/searchPage";
    }

    @GetMapping("/gangsin")
    public String gangsin(@RequestParam("gameName") String gameName,
                          @RequestParam("tagLine") String tagLine, Model model) throws JsonProcessingException {

        PuuidDto puuidDto = new PuuidDto();

        if (gameName != null && !gameName.isEmpty()) {
            puuidDto = summonerService.getPuuid(gameName, tagLine);
        }
        String puuid = puuidDto.getPuuid();

        List<MatchDto> matchDtos = matchService.getMatch(matchService.getMatchId(puuid));
        List<ParticipantDto> gameInfo = new ArrayList<>();
        List<InfoDto> infoDtos = new ArrayList<>();
        InfoDto infoDto = null;

        for (MatchDto matchDto : matchDtos) {
            // 먼저 infoDto가 null인지 확인합니다.
            infoDto = matchDto.getInfo();

            if (infoDto != null) {
                // participants가 null이 아닐 경우에만 루프를 돌도록 합니다.
                List<ParticipantDto> participants = infoDto.getParticipants();

                if (participants != null) {
                    for (ParticipantDto participant : participants) {
                        if (puuid.equals(participant.getPuuid())) {
                            System.out.println("게임 ID: " + infoDto.getGameId());
                            infoDtos.add(infoDto);
                            gameInfo.add(participant);
                            // 필요한 다른 정보도 출력할 수 있습니다.
                        }
                    }
                } else {
                    System.out.println("participants 리스트가 null입니다.");
                }

            } else {
                System.out.println("infoDto가 null입니다.");
            }
        }

        for (MatchDto match : matchDtos) {
            match.setWinner(match.getInfo().getParticipants().stream()
                    .anyMatch(p -> p.getPuuid().equals(puuid) && p.isWin()));
        }

        Map<Integer, String> summonerSpells = new HashMap<>();

        summonerSpells.put(21, "SummonerBarrier");
        summonerSpells.put(1, "SummonerBoost");
        summonerSpells.put(2202, "SummonerCherryFlash");
        summonerSpells.put(2201, "SummonerCherryHold");
        summonerSpells.put(14, "SummonerDot");
        summonerSpells.put(2, "SummonerExhaust");
        summonerSpells.put(4, "SummonerFlash");
        summonerSpells.put(6, "SummonerHaste");
        summonerSpells.put(7, "SummonerHeal");
        summonerSpells.put(13, "SummonerMana");
        summonerSpells.put(30, "SummonerPoroRecall");
        summonerSpells.put(31, "SummonerPoroThrow");
        summonerSpells.put(3, "SummonerSmite");
        summonerSpells.put(39, "SummonerSnowURFSnowball_Mark");
        summonerSpells.put(32, "SummonerSnowball");
        summonerSpells.put(12, "SummonerTeleport");
        summonerSpells.put(54, "Summoner_UltBookPlaceholder");
        summonerSpells.put(55, "Summoner_UltBookSmitePlaceholder");

        String summonerId = gameInfo.getFirst().getSummonerId();
        List<LeagueEntryDto> leagueEntryDto = null;
        if (summonerId != null && !summonerId.isEmpty()) {
            leagueEntryDto = summonerService.getSummonerRank(summonerId);
        }

        if (!leagueEntryDto.isEmpty()) {
            model.addAttribute("leagueEntryDto", leagueEntryDto.get(0));
        } else {
            model.addAttribute("leagueEntryDto", null);
        }

        model.addAttribute("spell", summonerSpells);
        model.addAttribute("matchDto", matchDtos);
        model.addAttribute("gameInfo", gameInfo);
        // 필요한 다른 데이터를 모델에 추가

        return "search/searchPage :: #match-container"; // 특정 섹션만 반환
    }
}
