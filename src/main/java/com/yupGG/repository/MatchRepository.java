package com.yupGG.repository;

import com.yupGG.entity.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {
    // 사용자 정의 메소드 추가 가능
    List<Match> findBySummoner_SummonerId(String summonerId);

    @Query("SELECT m FROM Match m WHERE m.gameId = :gameId")
    Match findGameId(@Param("gameId") Long gameId);
    @Modifying
    @Transactional
    @Query("UPDATE Match m SET m.gameId = :gameId, m.queueId = :queueId, m.gameEndTimestamp = :gameEndTimestamp, m.gameDuration = :gameDuration, m.championName = :championName, m.summoner1Id = :summoner1Id, m.summoner2Id = :summoner2Id, m.item0 = :item0, m.item1 = :item1, m.item2 = :item2, m.item3 = :item3, m.item4 = :item4, m.item5 = :item5, m.item6 = :item6, m.win = :win, m.kills = :kills, m.deaths = :deaths, m.assists = :assists, m.totalMinionsKilled = :totalMinionsKilled, m.visionScore = :visionScore WHERE m.id = :id")
    void updateMatch(
            @Param("id") Long id,
            @Param("gameId") Long gameId,
            @Param("queueId") Integer queueId,
            @Param("gameEndTimestamp") Long gameEndTimestamp,
            @Param("gameDuration") long gameDuration,
            @Param("championName") String championName,
            @Param("summoner1Id") Integer summoner1Id,
            @Param("summoner2Id") Integer summoner2Id,
            @Param("item0") Integer item0,
            @Param("item1") Integer item1,
            @Param("item2") Integer item2,
            @Param("item3") Integer item3,
            @Param("item4") Integer item4,
            @Param("item5") Integer item5,
            @Param("item6") Integer item6,
            @Param("win") Boolean win,
            @Param("kills") Integer kills,
            @Param("deaths") Integer deaths,
            @Param("assists") Integer assists,
            @Param("totalMinionsKilled") Integer totalMinionsKilled,
            @Param("visionScore") Integer visionScore
    );
}