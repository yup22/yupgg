package com.yupGG.repository;

import com.yupGG.entity.Summoner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SummonerRepository extends JpaRepository<Summoner, Long> {
    // 사용자 정의 메소드 추가 가능
    Summoner findByGameName(String gameName);
}