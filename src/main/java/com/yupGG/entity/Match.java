package com.yupGG.entity;

import com.yupGG.dto.MatchDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer queueId;
    private Long gameEndTimestamp;
    private Integer gameDuration;
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


    // getters and setters
}