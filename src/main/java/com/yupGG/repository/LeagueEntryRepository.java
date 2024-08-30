package com.yupGG.repository;

import com.yupGG.entity.LeagueEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeagueEntryRepository extends JpaRepository<LeagueEntry, Long> {
    // 사용자 정의 메소드 추가 가능
    LeagueEntry findBySummonerId(Long summonerId);
}