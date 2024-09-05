package com.yupGG.config;

import ch.qos.logback.core.util.StringUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.util.concurrent.RateLimiter;
import com.yupGG.dto.ResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.NoSuchElementException;

@Component
public class RiotHttpClient {
    private static final Logger logger = LoggerFactory.getLogger(RiotHttpClient.class);
    @Value("${riot.url}")
    private String riotUrl;
    private String riotAsiaUrl = "https://asia.api.riotgames.com";
    @Value("${riot.api.key}")
    private String riotApiKey;

    // 초당 19번 요청허용하는 RateLimiter
    private final RateLimiter rateLimiter = RateLimiter.create(19.0 / 1.0); // 초당 19번
    // 2분동안 99번
    private final RateLimiter twoMinuteRateLimiter = RateLimiter.create(99.0 / 120.0); // 2분에 99번


    // 라이엇 서버연결해서 조회
    public ResponseDto getRiotResponse(String server, String requestURL) {
        ResponseDto responseDto = null;
        StringBuilder httpURL = new StringBuilder();
        ObjectMapper mapper = new ObjectMapper();

        logger.info("Riot URL: {}", riotUrl);

        try {
            //요청전에 RateLimiter로 속도제한 확인
            rateLimiter.acquire();
            twoMinuteRateLimiter.acquire();

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
        } catch (NoSuchElementException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            responseDto = new ResponseDto(500, e.getCause() + " " + StringUtil.nullStringToEmpty(e.getMessage()));
        }
        return responseDto;
    }
}
/*
package com.yupGG.config;

import com.yupGG.dto.ResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.NoSuchElementException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.common.util.concurrent.RateLimiter;

@Component
public class RiotHttpClient {
    private static final Logger logger = LoggerFactory.getLogger(RiotHttpClient.class);

    @Value("${riot.url}")
    private String riotUrl;

    private String riotAsiaUrl = "https://asia.api.riotgames.com";

    @Value("${riot.api.key}")
    private String riotApiKey;

    // 초당 19번 요청 허용하는 RateLimiter
    private final RateLimiter rateLimiter = RateLimiter.create(19.0); // 초당 19번

    // 2분 동안 99번 요청 허용하는 RateLimiter
    private final RateLimiter twoMinuteRateLimiter = RateLimiter.create(99.0 / 120.0); // 2분에 99번

    // 라이엇 서버와 연결해서 조회
    public ResponseDto getRiotResponse(String server, String requestURL) {
        ResponseDto responseDto = null;
        StringBuilder httpURL = new StringBuilder();
        ObjectMapper mapper = new ObjectMapper();

        logger.info("Riot URL: {}", riotUrl);
        logger.info("Full Request URL: {}", buildFullUrl(server, requestURL)); // 추가된 로그

        try {
            // 요청 전에 RateLimiter로 속도 제한 확인
            rateLimiter.acquire();
            twoMinuteRateLimiter.acquire();

            // 서버 + URI + parameter + API key 주소 생성
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
        } catch (NoSuchElementException e) {
            logger.error("No such element exception: ", e); // 개선된 로깅
        } catch (IOException e) {
            logger.error("IO Exception occurred: ", e); // 추가된 예외 처리
        } catch (Exception e) {
            logger.error("Exception occurred: ", e); // 개선된 로깅
            responseDto = new ResponseDto(500, e.getMessage()); // e.getMessage()로 수정
        }
        return responseDto;
    }

    // URL 생성 로직을 분리하여 코드 가독성 향상
    private String buildFullUrl(String server, String requestURL) {
        return new StringBuilder(server)
                .append(requestURL)
                .append(requestURL.contains("?") ? "&" : "?")
                .append("api_key=")
                .append(riotApiKey)
                .toString();
    }
}
*/
