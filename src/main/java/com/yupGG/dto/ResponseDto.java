package com.yupGG.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true) // JSON필드 널값 무시한다고 보면될듯
public class ResponseDto {
    // 응답 오류시
    private StatusDto status;
    // 응답 성공시
    private String responseBody;


    public ResponseDto(String responseBody) {
        this.responseBody = responseBody;
    }

    public ResponseDto(int status_code, String message) {
        this.status = new StatusDto();
        this.responseBody = message;
    }

    //응답 성공여부
    public boolean isOK(){
        if (this.status != null) {
            return false;
        }
        return true;
    }
}
