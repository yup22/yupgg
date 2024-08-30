package com.yupGG.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class LeagueEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tier;
    private String rank;
    private Integer leaguePoints;
    private Integer wins;
    private Integer losses;

    @OneToOne
    @JoinColumn(name = "summoner_id")
    private Summoner summoner;

    // getters and setters
}
