package com.yupGG.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String header;
    private String title;
    private String content;
    private String author;
    private int viewCount;
    private int likeCount;
    private int commentCount;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Comment> comments;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    public Post(String title,String header, String Content,String author, int viewCount, int likeCount, LocalDateTime now) {
    }
}
