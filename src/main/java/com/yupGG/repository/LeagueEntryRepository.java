package com.yupGG.repository;

import com.yupGG.entity.LeagueEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface LeagueEntryRepository extends JpaRepository<LeagueEntry, Long> {
    // 사용자 정의 메소드 추가 가능
    LeagueEntry findBySummoner_SummonerId(String summonerId);

    @Modifying
    @Transactional
    @Query("UPDATE LeagueEntry l SET l.tier = :tier, l.rank = :rank, l.leaguePoints = :leaguePoints, l.wins = :wins, l.losses = :losses WHERE l.summoner.id = :summonerId")
    void updateLeagueEntry(
            @Param("summonerId") Long summonerId,
            @Param("tier") String tier,
            @Param("rank") String rank,
            @Param("leaguePoints") Integer leaguePoints,
            @Param("wins") Integer wins,
            @Param("losses") Integer losses
    );
}