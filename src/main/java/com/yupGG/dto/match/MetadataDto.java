package com.yupGG.dto.match;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MetadataDto {
    private String dataVersion;
    private String matchId;
    private List<String> participants;
}