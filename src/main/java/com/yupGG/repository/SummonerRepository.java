package com.yupGG.repository;

import com.yupGG.entity.Match;
import com.yupGG.entity.Summoner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SummonerRepository extends JpaRepository<Summoner, String> {
    // 사용자 정의 메소드 추가 가능
    Summoner findByGameName(String summonerId);

    @Modifying
    @Query("UPDATE Summoner s SET s.profileIconId = :profileIconId, s.summonerLevel = :summonerLevel, s.gameName = :gameName, s.tagLine = :tagLine WHERE s.summonerId = :summonerId")
    void updateSummoner(String summonerId, Integer profileIconId, long summonerLevel, String gameName, String tagLine);
}