package com.yupGG.repository;

import com.yupGG.entity.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {
    // 사용자 정의 메소드 추가 가능
    List<Match> findBySummonerId(Long summonerId);
}