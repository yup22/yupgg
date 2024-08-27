package com.yupGG.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String header;
    private String title;
    private String Content;
    private String author;
    private int viewCount;
    private int likeCount;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    public Post(String title,String header, String Content,String author, int viewCount, int likeCount, LocalDateTime now) {
    }
}
