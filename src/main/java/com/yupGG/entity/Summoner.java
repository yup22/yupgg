package com.yupGG.entity;

import com.yupGG.dto.PuuidDto;
import com.yupGG.dto.SummonerDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "summoners")
public class Summoner {

    @Id
    @Column(name = "summoner_id", unique = true) // 유일성 제약 조건 추가
    private String summonerId;

    private Integer profileIconId;
    private long summonerLevel;
    private String gameName;
    private String tagLine;


    public Summoner setSummoner(SummonerDTO summonerDto, PuuidDto puuidDto) {
        this.setSummonerId(summonerDto.getId());
        this.setProfileIconId(summonerDto.getProfileIconId());
        this.setSummonerLevel(summonerDto.getSummonerLevel());
        this.setGameName(puuidDto.getGameName());
        this.setTagLine(puuidDto.getTagLine());

        return this;
    }
}