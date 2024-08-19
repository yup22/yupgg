package com.yupGG.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yupGG.dto.match.InfoDto;
import com.yupGG.dto.match.MetadataDto;
import lombok.Data;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MatchDto {

    InfoDto infoDto;
    MetadataDto metadataDto;


}


