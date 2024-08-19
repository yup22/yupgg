package com.yupGG.dto.match.info;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ParticipantDto {
    // 노란색 교차된 검 (All-In Ping)
    private int allInPings;

    // 녹색 깃발 (Assist Me Ping)
    private int assistMePings;

    // 어시스트 수
    private int assists;

    // 바론 킬 수
    private int baronKills;

    // 현상금 레벨
    private int bountyLevel;

    // 챔피언 경험치
    private int champExperience;

    // 챔피언 레벨
    private int champLevel;

    // 챔피언 ID (패치 11.4 이전에는 유효하지 않은 ID 반환)
    private int championId;

    // 챔피언 이름
    private String championName;

    // 파란색 일반 핑 (Command Ping, ALT+클릭)
    private int commandPings;

    // 챔피언 변신 (현재 카인의 변신에만 사용됨)
    private int championTransform;

    // 구매한 소비품 수
    private int consumablesPurchased;


    // 건물에 가한 피해량
    private int damageDealtToBuildings;

    // 오브젝트에 가한 피해량
    private int damageDealtToObjectives;

    // 포탑에 가한 피해량
    private int damageDealtToTurrets;

    // 자기 피해량 감소
    private int damageSelfMitigated;

    // 죽음 수
    private int deaths;

    // 설치한 제어 와드 수
    private int detectorWardsPlaced;

    // 더블 킬 수
    private int doubleKills;

    // 드래곤 킬 수
    private int dragonKills;

    // 진척도 자격 여부
    private boolean eligibleForProgression;

    // 노란색 물음표 (Enemy Missing Ping)
    private int enemyMissingPings;

    // 빨간색 눈알 (Enemy Vision Ping)
    private int enemyVisionPings;

    // 첫 번째 킬 어시스트 여부
    private boolean firstBloodAssist;

    // 첫 번째 킬 여부
    private boolean firstBloodKill;

    // 첫 번째 포탑 어시스트 여부
    private boolean firstTowerAssist;

    // 첫 번째 포탑 킬 여부
    private boolean firstTowerKill;

    // 게임이 조기 항복으로 종료되었는지 여부
    private boolean gameEndedInEarlySurrender;

    // 게임이 항복으로 종료되었는지 여부
    private boolean gameEndedInSurrender;

    // 보유 핑
    private int holdPings;

    // 노란색 원에 수평선 (Get Back Ping)
    private int getBackPings;

    // 획득한 골드
    private int goldEarned;

    // 사용한 골드
    private int goldSpent;

    // 개인 포지션 (서버에서 계산된 플레이어의 포지션)
    private String individualPosition;

    // 억제기 킬 수
    private int inhibitorKills;

    // 억제기 철거 수
    private int inhibitorTakedowns;

    // 잃은 억제기 수
    private int inhibitorsLost;

    // 아이템 0 슬롯
    private int item0;

    // 아이템 1 슬롯
    private int item1;

    // 아이템 2 슬롯
    private int item2;

    // 아이템 3 슬롯
    private int item3;

    // 아이템 4 슬롯
    private int item4;

    // 아이템 5 슬롯
    private int item5;

    // 아이템 6 슬롯
    private int item6;

    // 구매한 아이템 수
    private int itemsPurchased;

    // 킬링 스프리 수
    private int killingSprees;

    // 킬 수
    private int kills;

    // 라인
    private String lane;

    // 가장 큰 치명타 피해량
    private int largestCriticalStrike;

    // 가장 긴 킬링 스프리
    private int largestKillingSpree;

    // 가장 큰 멀티 킬
    private int largestMultiKill;

    // 가장 오랜 생존 시간
    private int longestTimeSpentLiving;

    // 가한 마법 피해량
    private int magicDamageDealt;

    // 챔피언에게 가한 마법 피해량
    private int magicDamageDealtToChampions;

    // 받은 마법 피해량
    private int magicDamageTaken;

    // 처치한 중립 미니언 수
    private int neutralMinionsKilled;

    // 녹색 와드 (Need Vision Ping)
    private int needVisionPings;

    // 넥서스 킬 수
    private int nexusKills;

    // 넥서스 철거 수
    private int nexusTakedowns;

    // 잃은 넥서스 수
    private int nexusLost;

    // 오브젝트 훔친 수
    private int objectivesStolen;

    // 오브젝트 훔친 어시스트 수
    private int objectivesStolenAssists;

    // 파란색 화살표 (On My Way Ping)
    private int onMyWayPings;

    // 참가자 ID
    private int participantId;

    // 플레이어 점수 0
    private int playerScore0;

    // 플레이어 점수 1
    private int playerScore1;

    // 플레이어 점수 2
    private int playerScore2;

    // 플레이어 점수 3
    private int playerScore3;

    // 플레이어 점수 4
    private int playerScore4;

    // 플레이어 점수 5
    private int playerScore5;

    // 플레이어 점수 6
    private int playerScore6;

    // 플레이어 점수 7
    private int playerScore7;

    // 플레이어 점수 8
    private int playerScore8;

    // 플레이어 점수 9
    private int playerScore9;

    // 플레이어 점수 10
    private int playerScore10;

    // 펜타 킬 수
    private int pentaKills;

    // 가한 물리 피해량
    private int physicalDamageDealt;

    // 챔피언에게 가한 물리 피해량
    private int physicalDamageDealtToChampions;

    // 받은 물리 피해량
    private int physicalDamageTaken;

    // 플레이어 배치 순위
    private int placement;

    // 플레이어 증강 1
    private int playerAugment1;

    // 플레이어 증강 2
    private int playerAugment2;

    // 플레이어 증강 3
    private int playerAugment3;

    // 플레이어 증강 4
    private int playerAugment4;

    // 플레이어 서브팀 ID
    private int playerSubteamId;

    // 녹색 미니언 (Push Ping)
    private int pushPings;

    // 프로필 아이콘 ID
    private int profileIcon;

    // 플레이어의 유니버설 고유 ID
    private String puuid;

    // 쿼드라 킬 수
    private int quadraKills;

    // 라이엇 ID 게임 이름
    private String riotIdGameName;

    // 라이엇 ID 이름
    private String riotIdName;

    // 라이엇 ID 태그라인
    private String riotIdTagline;

    // 역할
    private String role;

    // 구매한 시야 와드 수
    private int sightWardsBoughtInGame;

    // 사용한 소환사 주문 1 횟수
    private int summoner1Casts;

    // 소환사 주문 1 ID
    private int summoner1Id;

    // 사용한 소환사 주문 2 횟수
    private int summoner2Casts;

    // 소환사 주문 2 ID
    private int summoner2Id;

    // 소환사 ID
    private String summonerId;

    // 소환사 레벨
    private int summonerLevel;

    // 소환사 이름
    private String summonerName;

    // 팀이 조기 항복했는지 여부
    private boolean teamEarlySurrendered;

    // 팀 ID
    private int teamId;

    // 팀 포지션 (서버에서 계산된 포지션)
    private String teamPosition;

    // 다른 플레이어에게 가한 CC 시간
    private int timeCCingOthers;

    // 플레이한 시간
    private int timePlayed;

    // 처치한 아군 정글 미니언 수
    private int totalAllyJungleMinionsKilled;

    // 가한 총 피해량
    private int totalDamageDealt;

    // 챔피언에게 가한 총 피해량
    private int totalDamageDealtToChampions;

    // 아군에게 받은 총 피해량
    private int totalDamageShieldedOnTeammates;

    // 받은 총 피해량
    private int totalDamageTaken;

    // 적 정글 미니언 총 처치 수
    private int totalEnemyJungleMinionsKilled;

    // 총 회복량
    private int totalHeal;

    // 아군에게 총 치료량
    private int totalHealsOnTeammates;

    // 총 처치한 미니언 수
    private int totalMinionsKilled;

    // CC를 통한 총 피해 시간
    private int totalTimeCCDealt;

    // 총 사망 시간
    private int totalTimeSpentDead;

    // 총 치료한 유닛 수
    private int totalUnitsHealed;

    // 트리플 킬 수
    private int tripleKills;

    // 가한 고유 피해량
    private int trueDamageDealt;

    // 챔피언에게 가한 고유 피해량
    private int trueDamageDealtToChampions;

    // 받은 고유 피해량
    private int trueDamageTaken;

    // 포탑 킬 수
    private int turretKills;

    // 포탑 철거 수
    private int turretTakedowns;

    // 잃은 포탑 수
    private int turretsLost;

    // 비현실적인 킬 수
    private int unrealKills;

    // 시야 점수
    private int visionScore;

    // 시야를 제거하는 핑
    private int visionClearedPings;

    // 게임 내에서 구매한 시야 와드 수
    private int visionWardsBoughtInGame;

    // 처치한 와드 수
    private int wardsKilled;

    // 설치한 와드 수
    private int wardsPlaced;

    // 승리 여부
    private boolean win;
}

