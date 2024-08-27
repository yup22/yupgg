package com.yupGG.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
    private Long id;
    private String header;
    private String title;
    private String Content;
    private String author;
    private int viewCount;
    private int likeCount;
    private String createdDate;


}
