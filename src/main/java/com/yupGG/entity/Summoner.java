package com.yupGG.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Summoner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer profileIconId;
    private Integer summonerLevel;
    private String gameName;
    private String tagLine;

    @OneToOne(mappedBy = "summoner", cascade = CascadeType.ALL)
    private LeagueEntry leagueEntry;

    @OneToMany(mappedBy = "summoner", cascade = CascadeType.ALL)
    private List<Match> matches;

}