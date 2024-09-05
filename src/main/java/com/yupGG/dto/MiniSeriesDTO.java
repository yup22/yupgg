package com.yupGG.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MiniSeriesDTO {
    private int losses;
    private String progress;
    private int target;
    private int wins;
}
