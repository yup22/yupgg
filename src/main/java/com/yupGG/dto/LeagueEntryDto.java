package com.yupGG.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LeagueEntryDto {
    private String leagueId;
    private String summonerId;  // Player's encrypted summonerId.
    private String queueType;
    private String tier;
    private String rank;  // The player's division within a tier.
    private int leaguePoints;
    private int wins;  // Winning team on Summoners Rift.
    private int losses;  // Losing team on Summoners Rift.
    private boolean hotStreak;
    private boolean veteran;
    private boolean freshBlood;
    private boolean inactive;
}
