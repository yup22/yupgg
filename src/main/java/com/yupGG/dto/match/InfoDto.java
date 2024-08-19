package com.yupGG.dto.match;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yupGG.dto.match.info.ParticipantDto;
import com.yupGG.dto.match.info.TeamDto;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class InfoDto {
    private String endOfGameResult; // 게임이 종료된 원인을 나타내는 문자열
    private long gameCreation; // 게임 서버에서 게임이 생성된 시간의 Unix 타임스탬프 (즉, 로딩 화면)
    private long gameDuration; // 게임 길이. 패치 11.20 이전에는 밀리초 단위로 반환되었으며, 이후에는 가장 오래 플레이한 참가자의 시간(초 단위)으로 반환됨
    private long gameEndTimestamp; // 게임 서버에서 게임이 종료된 시간의 Unix 타임스탬프
    private long gameId; // 게임 ID
    private String gameMode; // 게임 모드를 나타내는 문자열 (게임 상수 문서를 참조)
    private String gameName; // 게임 이름
    private long gameStartTimestamp; // 게임 서버에서 게임이 시작된 시간의 Unix 타임스탬프
    private String gameType; // 게임 유형을 나타내는 문자열
    private String gameVersion; // 패치를 확인할 수 있는 게임 버전 (앞의 두 부분으로 패치를 확인 가능)
    private int mapId; // 맵 ID (게임 상수 문서를 참조)
    private List<ParticipantDto> participants; // 참가자들의 목록
    private String platformId; // 게임이 플레이된 플랫폼
    private int queueId; // 큐 ID (게임 상수 문서를 참조)
    private List<TeamDto> teams; // 팀들의 목록
    private String tournamentCode; // 토너먼트 코드 (패치 11.13에서 추가됨)
}