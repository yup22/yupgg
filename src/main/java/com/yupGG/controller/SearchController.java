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

import java.io.UnsupportedEncodingException;
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
                         @RequestParam(name = "summonersTag", required = false) String summonersTag, Model model) throws JsonProcessingException, UnsupportedEncodingException {

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
        Map<String, Integer> winRate = new HashMap<>();
        int winCount = 0, loseCount = 0;

// matchHistory가 null이 아니고 비어 있지 않은지 확인
        if (matchHistory != null && !matchHistory.isEmpty()) {
            for (int i = 0; i < matchHistory.size(); i++) {
                // 각 매치의 객체가 null인지, 그리고 getChampionName()이 null이 아닌지 확인
                if (matchHistory.get(i) != null && matchHistory.get(i).getChampionName() != null) {
                    System.out.println(matchHistory.get(i).getChampionName());

                    // 최대 20개의 경기까지만 처리
                    if (i == 20) {
                        break;
                    }

                    // 승리 여부를 확인할 때도 null 체크
                    if (Boolean.TRUE.equals(matchHistory.get(i).getWin())) {
                        winCount++;
                    } else {
                        loseCount++;
                    }
                }
            }
        } else {
            // matchHistory가 null이거나 비어 있을 경우 처리
            System.out.println("매치 히스토리가 없습니다.");
        }

        winRate.put("승", winCount);
        winRate.put("패", loseCount);
        winRate.put("전", winCount + loseCount);

        model.addAttribute("winRate", winRate);

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
            if (leagueEntryDto.size() > 1 && leagueEntryDto.get(0).getQueueType().equals("CHERRY")) {
                model.addAttribute("leagueEntryDto", leagueEntryDto.get(1));
            } else {
                model.addAttribute("leagueEntryDto", leagueEntryDto.get(0));
            }
        }else{
            model.addAttribute("leagueEntryDto", "null");

        }



        return "search/searchPage";
    }

    @GetMapping("/gangsin")
    public String gangsin(@RequestParam("gameName") String gameName,
                          @RequestParam("tagLine") String tagLine, Model model) throws JsonProcessingException, UnsupportedEncodingException {

        PuuidDto puuidDto = new PuuidDto();
        SummonerDTO summonerDTO = new SummonerDTO();

        if (gameName != null && !gameName.isEmpty()) {
            System.out.println("게임네임 : "+ gameName);
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
            if (leagueEntryDto.size() > 1 && leagueEntryDto.get(0).getQueueType().equals("CHERRY")) {
                model.addAttribute("leagueEntryDto", leagueEntryDto.get(1));
            } else {
                model.addAttribute("leagueEntryDto", leagueEntryDto.get(0));
            }
        }else{
            model.addAttribute("leagueEntryDto", "null");

        }
        Summoner savesummoner = matchService.saveSummoner(summonerDTO,puuidDto);
        for (int i = 0; i < matchDtos.size(); i++) {
            Match saveMatch = matchService.saveMatch(matchDtos.get(i),gameInfo.get(i),savesummoner,i);
        }
        if (!leagueEntryDto.isEmpty()) {
            if (leagueEntryDto.get(0).getQueueType().equals("CHERRY")) {
                LeagueEntry leagueEntry = matchService.saveLeagueEntry(leagueEntryDto.get(1),savesummoner);
            } else {
                LeagueEntry leagueEntry = matchService.saveLeagueEntry(leagueEntryDto.get(0),savesummoner);
            }
        }

        List<Match> matchHistory =matchService.getMatchHistory(summonerId);
        Map<String, Integer> winRate = new HashMap<>();
        int winCount = 0, loseCount = 0;

// matchHistory가 null이 아니고 비어 있지 않은지 확인
        if (matchHistory != null && !matchHistory.isEmpty()) {
            for (int i = 0; i < matchHistory.size(); i++) {
                // 각 매치의 객체가 null인지, 그리고 getChampionName()이 null이 아닌지 확인
                if (matchHistory.get(i) != null && matchHistory.get(i).getChampionName() != null) {
                    System.out.println(matchHistory.get(i).getChampionName());
                    // 최대 20개의 경기까지만 처리
                    if (i == 20) {
                        break;
                    }
                    // 승리 여부를 확인할 때도 null 체크
                    if (Boolean.TRUE.equals(matchHistory.get(i).getWin())) {
                        winCount++;
                    } else {
                        loseCount++;
                    }
                }
            }
        } else {
            // matchHistory가 null이거나 비어 있을 경우 처리
            System.out.println("매치 히스토리가 없습니다.");
        }
        winRate.put("승", winCount);
        winRate.put("패", loseCount);
        winRate.put("전",winCount+loseCount);

        model.addAttribute("winRate",winRate);
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
