package com.yupGG.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yupGG.config.RiotHttpClient;

import com.yupGG.dto.*;
import com.yupGG.dto.match.InfoDto;
import com.yupGG.dto.match.info.ParticipantDto;
import com.yupGG.entity.LeagueEntry;
import com.yupGG.entity.Match;
import com.yupGG.entity.Summoner;
import com.yupGG.repository.LeagueEntryRepository;
import com.yupGG.repository.MatchRepository;
import com.yupGG.repository.SummonerRepository;
import jakarta.mail.Part;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MatchService {
    private final RiotHttpClient riotHttpClient;
    private final ObjectMapper objectMapper;
    @Autowired
    private SummonerRepository summonerRepository;
    @Autowired
    private MatchRepository matchRepository;
    @Autowired
    private LeagueEntryRepository leagueEntryRepository;

    @Value("${riot.url}")
    private String riotUrl;
    private String riotAsiaUrl = "https://asia.api.riotgames.com";

    public MatchService(RiotHttpClient riotHttpClient, ObjectMapper objectMapper) throws JsonMappingException {
        this.riotHttpClient = riotHttpClient;
        this.objectMapper = objectMapper;
    }

    public List<String> getMatchId(String puuid) throws JsonProcessingException {
        List<String> MatchIdList = new ArrayList<>();
        StringBuilder sb = new StringBuilder("/lol/match/v5/matches/by-puuid/" + puuid + "/ids");
        ResponseDto responseDto = riotHttpClient.getRiotResponse(riotAsiaUrl, sb.toString());

        if (responseDto.isOK()) {
            String jsonInput = responseDto.getResponseBody();
            MatchIdList = objectMapper.readValue(jsonInput, new TypeReference<List<String>>() {
            });
        }
        return MatchIdList;
    }

    public List<MatchDto> getMatch(List<String> MatchIdList) throws JsonProcessingException {
        List<MatchDto> matchDtoList = new ArrayList<>();
        for (String id : MatchIdList) {
            StringBuilder sb = new StringBuilder("/lol/match/v5/matches/" + id);
            ResponseDto responseDto = riotHttpClient.getRiotResponse(riotAsiaUrl, sb.toString());
            if (responseDto.isOK()) {
                matchDtoList.add(objectMapper.readValue(responseDto.getResponseBody(), MatchDto.class));
            } else {
                // 예외 처리: 실패한 경우 로그를 남기거나, 계속 진행할지 결정
                System.err.println("Failed to retrieve match: " + id);
            }
        }

        return matchDtoList;
    }

    public List<ParticipantDto> participantDtos(List<MatchDto> matchDtos, String puuid) {
        List<InfoDto> infoDtos = new ArrayList<>();
        List<ParticipantDto> gameInfo = new ArrayList<>();
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
        return gameInfo;
    }

    public Summoner saveSummoner(SummonerDTO summonerDTO, PuuidDto puuidDto) {
        Summoner summoner = new Summoner();
        summoner.setSummoner(summonerDTO, puuidDto);

        if (summonerRepository.findByGameName(summonerDTO.getId()) != null) {
            summonerRepository.updateSummoner(summonerDTO.getId(), summonerDTO.getProfileIconId(),
                    summonerDTO.getProfileIconId(), puuidDto.getGameName(), puuidDto.getTagLine());
            return null;
        }

        return summonerRepository.save(summoner);
    }

    public Match saveMatch(MatchDto matchDto, ParticipantDto participantDto, Summoner summoner,int i) {
        Match match = new Match();
        match.setMatch(matchDto, participantDto, summoner);
        System.out.println("소환사아이디2 : " + summoner.getSummonerId());
        if (matchRepository.findGameId(matchDto.getInfo().getGameId()) != null) {
            System.out.println("소환사아이디3 :" + summoner.getSummonerId());
            matchRepository.updateMatch(
                    match.getId(),
                    matchDto.getInfo().getGameId(),
                    matchDto.getInfo().getQueueId(),
                    matchDto.getInfo().getGameEndTimestamp(),
                    matchDto.getInfo().getGameDuration(),
                    participantDto.getChampionName(),
                    participantDto.getSummoner1Id(),
                    participantDto.getSummoner2Id(),
                    participantDto.getItem0(),
                    participantDto.getItem1(),
                    participantDto.getItem2(),
                    participantDto.getItem3(),
                    participantDto.getItem4(),
                    participantDto.getItem5(),
                    participantDto.getItem6(),
                    participantDto.isWin(),
                    participantDto.getKills(),
                    participantDto.getDeaths(),
                    participantDto.getAssists(),
                    participantDto.getTotalMinionsKilled(),
                    participantDto.getVisionScore()
            );
            return null;
        }
        return matchRepository.save(match);
    }

    public LeagueEntry saveLeagueEntry(LeagueEntryDto leagueEntryDto, Summoner summoner) {
        LeagueEntry leagueEntry = new LeagueEntry();
        leagueEntry.setLeagueEntry(leagueEntryDto, summoner);
        if (leagueEntryRepository.findBySummoner_SummonerId(summoner.getSummonerId()) != null) {
             leagueEntryRepository.updateLeagueEntry(
                    summoner.getSummonerLevel(),
                    leagueEntryDto.getTier(),
                    leagueEntryDto.getRank(),
                    leagueEntryDto.getLeaguePoints(),
                    leagueEntryDto.getWins(),
                    leagueEntryDto.getLosses()
            );
            return null;
        }
        return leagueEntryRepository.save(leagueEntry);
    }

    public List<Match> getMatchHistory(String summonerId) {
        List<Match> matchHistory = matchRepository.findBySummoner_SummonerIdOrderByGameEndTimestampDesc(summonerId);


        return matchHistory;
    }

}
