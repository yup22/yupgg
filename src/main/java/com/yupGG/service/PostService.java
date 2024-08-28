package com.yupGG.service;

import com.yupGG.dto.PostDto;
import com.yupGG.entity.Post;
import com.yupGG.repository.PostRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

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

    public Post incrementLikeCount(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        post.setLikeCount(post.getLikeCount() + 1);
        postRepository.save(post);
        return post;
    }

    public void createPost(PostDto postDto) {
        Post post = new Post(postDto.getTitle(), postDto.getHeader(), postDto.getContent(), postDto.getAuthor(), 0,0,LocalDateTime.now());
        postRepository.save(post);
    }

    public Page<Post> findPosts(int page) {
        // 페이지 번호(page)는 0부터 시작하며, 페이지 크기는 10으로 설정
        Pageable pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "createdDate"));
        return postRepository.findAll(pageable);
    }

    public void savePost(PostDto postDto) {
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime createdDate = LocalDateTime.parse(postDto.getCreatedDate(), formatter);
        post.setCreatedDate(createdDate);
        post.setHeader(postDto.getHeader());
        post.setAuthor(postDto.getAuthor());

        postRepository.save(post);
    }

    public void updatePostView(Long id, Post post1) {
        // 기존 포스트를 데이터베이스에서 조회
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Post not found"));


        // 조회수 증가 처리 (예시로 추가)
        post.setViewCount(post.getViewCount() + 1);

        // 변경된 값 저장
        postRepository.save(post);
    }

    public Optional<Post> getPostById(Long id) {
        return postRepository.findById(id);
    }

    public void deletePost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Post not found"));

        postRepository.delete(post);
    }
}
