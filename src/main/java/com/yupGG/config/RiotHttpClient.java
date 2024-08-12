package com.yupGG.config;

import ch.qos.logback.core.util.StringUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yupGG.dto.ResponseDto;
import com.yupGG.dto.SummonerDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class RiotHttpClient {
    private static final Logger logger = LoggerFactory.getLogger(RiotHttpClient.class);
//    @Value("${riot.url}")
    private String riotUrl = "https://kr.api.riotgames.com";

    private String riotAsiaUrl = "https://asia.api.riotgames.com";
//    @Value("${riot.api.key}")
    private String riotApiKey = "RGAPI-18e2a8bf-93c8-4b3f-b86c-8b2a1f0683e1";


    //소환사 puuid 조회
    public ResponseDto getPuuid(String name,String tag) {
        StringBuilder sb = new StringBuilder("/riot/account/v1/accounts/by-riot-id/" + name + "/" + tag);


        return getRiotResponse(riotAsiaUrl,sb.toString());
    }

    public ResponseDto getSummonerName(String puuid) {
        StringBuilder sb = new StringBuilder("/lol/summoner/v4/summoners/by-puuid/" + puuid);

        return getRiotResponse(riotUrl, sb.toString());
    }
    // 라이엇 서버연결해서 조회
    public ResponseDto getRiotResponse(String server, String requestURL) {
        ResponseDto responseDto = null;
        StringBuilder httpURL = new StringBuilder();
        ObjectMapper mapper = new ObjectMapper();

        logger.info("Riot URL: {}", riotUrl);
        logger.info("Riot API Key: {}", riotApiKey);

        try {
            // 서버 + URI + parameter + api key 주소 생성
            httpURL.append(server)
                    .append(requestURL)
                    .append(requestURL.contains("?") ? "&" : "?")
                    .append("api_key=")
                    .append(riotApiKey);

            HttpRequest httpRequest = HttpRequest.newBuilder(URI.create(httpURL.toString())).build();
            HttpResponse<String> httpResponse = HttpClient.newHttpClient()
                    .send(httpRequest, HttpResponse.BodyHandlers.ofString());

            // 정상 (=200)이 아니면 오류 발생
            if (httpResponse.statusCode() != 200) {
                responseDto = mapper.readValue(httpResponse.body(), ResponseDto.class);
                throw new Exception(responseDto.getStatus().getMessage());
            }
            // 반환 객체에 저장
            responseDto = new ResponseDto(httpResponse.body());
        } catch (Exception e) {
            e.printStackTrace();
            responseDto = new ResponseDto(500, e.getCause() + " " + StringUtil.nullStringToEmpty(e.getMessage()));
        }
        return responseDto;
    }
}