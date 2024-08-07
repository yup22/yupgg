package com.yupGG.dto;

import lombok.Data;

@Data
public class StatusDto {
    private String message;
    private int status_code;

    public StatusDto(int status_code, String message) {
        this.message = message;
        this.status_code = status_code;
    }
}
