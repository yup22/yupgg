package com.yupGG.service;

import com.yupGG.dto.PostDto;
import com.yupGG.entity.Post;
import com.yupGG.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public PostDto getPost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));
        String formattedDate = post.getCreatedDate().format(FORMATTER);
        return new PostDto(post.getId(),post.getHeader(), post.getTitle(), post.getContent(),post.getAuthor(), post.getViewCount(), post.getLikeCount(), formattedDate);
    }

    public void incrementViewCount(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));
        post.setViewCount(post.getViewCount() + 1);
        postRepository.save(post);
    }

    public void incrementLikeCount(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));
        post.setLikeCount(post.getLikeCount() + 1);
        postRepository.save(post);
    }

    public void createPost(PostDto postDto) {
        Post post = new Post(postDto.getTitle(), postDto.getHeader(), postDto.getContent(), postDto.getAuthor(), 0,0,LocalDateTime.now());
        postRepository.save(post);
    }

    public Page<Post> findPosts(int page) {
        // 페이지 번호(page)는 0부터 시작하며, 페이지 크기는 10으로 설정
        PageRequest pageRequest = PageRequest.of(page, 10);
        return postRepository.findAll(pageRequest);
    }

    public void savePost(PostDto postDto) {
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime createdDate = LocalDateTime.parse(postDto.getCreatedDate(), formatter);
        post.setCreatedDate(createdDate);

        postRepository.save(post);
    }
}
