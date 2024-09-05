package com.yupGG.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yupGG.dto.*;
import com.yupGG.dto.MatchDto;
import com.yupGG.dto.match.InfoDto;
import com.yupGG.dto.match.info.ParticipantDto;
import com.yupGG.entity.LeagueEntry;
import com.yupGG.entity.Match;
import com.yupGG.entity.Summoner;
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

        List<Match> matchHistory =matchService.getMatchHistory(summonerId);
        for (int i = 0; i < matchHistory.size(); i++) {
            System.out.println(matchHistory.get(i).getChampionName());
        }


        if (summonerId != null && !summonerId.isEmpty()) {
            leagueEntryDto = summonerService.getSummonerRank(summonerId);
        }


        model.addAttribute("spell", summonerSpell());

        model.addAttribute("matchDto", matchHistory);
        model.addAttribute("gameInfo", matchHistory);
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
        SummonerDTO summonerDTO = new SummonerDTO();

        if (gameName != null && !gameName.isEmpty()) {
            puuidDto = summonerService.getPuuid(gameName, tagLine);
        }
        String puuid = puuidDto.getPuuid();
        summonerDTO = summonerService.getSummonerByPuuid(puuid);

        List<MatchDto> matchDtos = matchService.getMatch(matchService.getMatchId(puuid));
        List<ParticipantDto> gameInfo = matchService.participantDtos(matchDtos, puuid);

        for (MatchDto match : matchDtos) {
            match.setWinner(match.getInfo().getParticipants().stream()
                    .anyMatch(p -> p.getPuuid().equals(puuid) && p.isWin()));
        }



        model.addAttribute("spell", summonerSpell());


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
        Summoner savesummoner = matchService.saveSummoner(summonerDTO,puuidDto);
        for (int i = 0; i < matchDtos.size(); i++) {
            Match saveMatch = matchService.saveMatch(matchDtos.get(i),gameInfo.get(i),savesummoner,i);
        }
        for (int i = 0; i < leagueEntryDto.size(); i++) {
            LeagueEntry leagueEntry = matchService.saveLeagueEntry(leagueEntryDto.get(i),savesummoner);
        }

        for (int i = 0; i < matchDtos.getFirst().getMetadata().getParticipants().size(); i++) {
            System.out.println("이게 뭐징~~? : " + matchDtos.getFirst().getMetadata().getParticipants().get(i));
        }


        model.addAttribute("matchDto", matchDtos);
        model.addAttribute("gameInfo", gameInfo);
        // 필요한 다른 데이터를 모델에 추가

        return "search/searchPage :: #match-container"; // 특정 섹션만 반환
    }

    public Map<Integer, String> summonerSpell() {
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
        return summonerSpells;
    }
}
