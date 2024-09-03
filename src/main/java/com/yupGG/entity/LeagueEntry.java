package com.yupGG.entity;

import com.yupGG.dto.LeagueEntryDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "league_entry")
public class LeagueEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tier;
    @Column(name = "`rank`")  // 예약어를 감싸는 방법
    private String rank;
    private Integer leaguePoints;
    private Integer wins;
    private Integer losses;

    @OneToOne
    @JoinColumn(name = "summoner_id")
    private Summoner summoner;

    public LeagueEntry setLeagueEntry(LeagueEntryDto dto, Summoner summoner) {
        this.setTier(dto.getTier());
        this.setRank(dto.getRank());
        this.setLeaguePoints(dto.getLeaguePoints());
        this.setWins(dto.getWins());
        this.setLosses(dto.getLosses());
        this.setSummoner(summoner);

        return this;
    }
}
