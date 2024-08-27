package com.yupGG.controller;

import com.yupGG.dto.PostDto;
import com.yupGG.entity.Post;
import com.yupGG.service.MemberService;
import com.yupGG.service.PostService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private PostService postService;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private MemberService memberService;

    @GetMapping("/freeBoard")
    public String freeBoard(Model model, @RequestParam(name = "page", defaultValue = "0")int page) {
        Page<Post> postPage = postService.findPosts(page);
        model.addAttribute("posts", postPage.getContent());
        model.addAttribute("page", postPage);
        return "/board/freeBoard";
    }

    @GetMapping("/boardWrite")
    public String writePost(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUser = authentication.getName();
        System.out.println(currentUser);
        model.addAttribute("user", currentUser);
        return "/board/boardWrite";
    }

    @PostMapping("/save")
    public String save(@RequestParam(name = "title") String title,
                       @RequestParam(name = "header") String header,
                       @RequestParam(name = "content") String content) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUser = authentication.getName();

        PostDto postDto = new PostDto();
        postDto.setTitle(title);
        postDto.setHeader(header);
        postDto.setAuthor(currentUser);
        postDto.setContent(content);

        String formattedDate = LocalDateTime.now().format(FORMATTER);
        postDto.setCreatedDate(formattedDate);

        System.out.println("작성일 : " + formattedDate);
        postService.savePost(postDto);

        return "redirect:/board/freeBoard"; // 글 작성 후 게시판 목록으로 이동
    }

    @GetMapping("/userBoard/{id}")
    public String userBoard(@PathVariable("id") Long id, Model model) {
        return postService.getPostById(id)
                .map(post -> {
                    model.addAttribute("post", post);
                    return "/board/userBoard"; // Thymeleaf 템플릿 파일 이름
                })
                .orElse("error"); // 에러 페이지 또는 다른 페이지로 리다이렉트
    }
    @PostMapping("/{id}/like")
    public void likePost(@PathVariable Long id) {
        postService.incrementLikeCount(id);
    }

    @PostMapping
    public void createPost(@RequestBody PostDto postDTO) {
        postService.createPost(postDTO);
    }
}

