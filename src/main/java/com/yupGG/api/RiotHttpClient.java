package com.yupGG.api;

import ch.qos.logback.core.util.StringUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yupGG.dto.ResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.net.http.HttpRequest;

public class RiotHttpClient {
    private static final Logger logger = LoggerFactory.getLogger(RiotHttpClient.class);
    @Value("${riot.url}")
    private String riotUrl;
    @Value("${riot.api.key}")
    private String riotApiKey;
    //소환사 puuid 조회
    public ResponseDto getSummonersName(String name,String tag) {
        StringBuilder sb = new StringBuilder("/riot/account/v1/accounts/by-riot-id/" + name + "/" + tag);

        return getRiotResponse(riotUrl,sb.toString());
    }

    // 라이엇 서버연결해서 조회
    public ResponseDto getRiotResponse(String server, String requestURL) {
        ResponseDto responseDto = null;
        StringBuilder httpURL = new StringBuilder();
        ObjectMapper mapper = new ObjectMapper();

        try {
            // 서버 + URI + parameter + api key 주소 생성
            httpURL.append(server)
                    .append(requestURL)
                    .append(requestURL.contains("?") ? "&" : "?")
                    .append("api_key=")
                    .append(riotApiKey);


        }
    }
}
