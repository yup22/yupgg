package com.yupGG.entity;

import com.yupGG.dto.MatchDto;
import com.yupGG.dto.match.InfoDto;
import com.yupGG.dto.match.info.ParticipantDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "matches")  // 테이블 이름을 "matches"로 변경
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer queueId;
    private Long gameEndTimestamp;
    private long gameDuration;
    private String championName;
    private Integer summoner1Id;
    private Integer summoner2Id;
    private Integer item0;
    private Integer item1;
    private Integer item2;
    private Integer item3;
    private Integer item4;
    private Integer item5;
    private Integer item6;

    private Boolean win;
    private Integer kills;
    private Integer deaths;
    private Integer assists;
    private Integer totalMinionsKilled;
    private Integer visionScore;

    @ManyToOne
    @JoinColumn(name = "summoner_id")
    private Summoner summoner;

    public Match setMatch(MatchDto dto,ParticipantDto gameInfo,Summoner summoner) {

        this.setQueueId(dto.getInfo().getQueueId());
        this.setGameEndTimestamp(dto.getInfo().getGameEndTimestamp());
        this.setGameDuration(dto.getInfo().getGameDuration());
        this.setChampionName(gameInfo.getChampionName());
        this.setSummoner1Id(gameInfo.getSummoner1Id());
        this.setSummoner2Id(gameInfo.getSummoner2Id());
        this.setItem0(gameInfo.getItem0());
        this.setItem1(gameInfo.getItem1());
        this.setItem2(gameInfo.getItem2());
        this.setItem3(gameInfo.getItem3());
        this.setItem4(gameInfo.getItem4());
        this.setItem5(gameInfo.getItem5());
        this.setItem6(gameInfo.getItem6());
        this.setWin(gameInfo.isWin());
        this.setKills(gameInfo.getKills());
        this.setDeaths(gameInfo.getDeaths());
        this.setAssists(gameInfo.getAssists());
        this.setTotalMinionsKilled(gameInfo.getTotalMinionsKilled());
        this.setVisionScore(gameInfo.getVisionScore());
        this.setSummoner(summoner);
        return this;
    }


    // getters and setters
}