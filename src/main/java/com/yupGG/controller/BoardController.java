package com.yupGG.controller;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yupGG.dto.CommentDto;
import com.yupGG.dto.PostDto;
import com.yupGG.entity.Comment;
import com.yupGG.entity.Post;
import com.yupGG.repository.PostRepository;
import com.yupGG.service.CommentService;
import com.yupGG.service.MemberService;
import com.yupGG.service.PostService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
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
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private PostService postService;
    @Autowired
    private CommentService commentService;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private MemberService memberService;

    @GetMapping("/freeBoard")
    public String freeBoard(Model model, @RequestParam(name = "page", defaultValue = "0")int page) {
        Page<Post> postPage = postService.findPosts(page);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String user = authentication.getName();
        System.out.println("로그인 유저 :" + user);
        model.addAttribute("user", user);

        model.addAttribute("posts", postPage.getContent());
        model.addAttribute("page", postPage);
        return "/board/freeBoard";
    }

    @GetMapping("/boardWrite")
    public String writePost(Model model,Principal principal) {
        System.out.println("글쓰기 컨트롤러");

        model.addAttribute("user", principal.getName());
        return "/board/boardWrite";
    }

    @PostMapping("/save")
    public String save(@RequestParam(name = "title") String title,
                       @RequestParam(name = "header") String header,
                       @RequestParam(name = "content") String content) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUser = authentication.getName();
        System.out.println("ㅎㅇ save메서드야 ");
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

 /*   @GetMapping("/userBoard/{id}")
    public String userBoard(@PathVariable("id") Long id, Model model) {
        return postService.getPostById(id)
                .map(post -> {
                    model.addAttribute("post", post);
                    return "/board/userBoard"; // Thymeleaf 템플릿 파일 이름
                })
                .orElse("error"); // 에러 페이지 또는 다른 페이지로 리다이렉트
    }*/
    @GetMapping("/userBoard/{id}")
    public String userBoard(@PathVariable("id") Long id, Model model) {
        Optional<Post> postOptional = postService.getPostById(id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUser = authentication.getName();
        model.addAttribute("user", currentUser);

        if (!postOptional.isPresent()) {
            return "error"; // 에러 페이지 또는 다른 페이지로 리다이렉트
        }

        Post post = postOptional.get();
        List<Comment> comments = commentService.getCommentByPost(post);
        postService.updatePostView(id,post);
        System.out.println("조회수 :" + post.getViewCount());
        model.addAttribute("post", post);
        model.addAttribute("id", id);
        model.addAttribute("comments", comments);

        return "/board/userBoard"; // Thymeleaf 템플릿 파일 이름
    }
    @PostMapping("/userBoard/{postId}/comment")
    public ResponseEntity<Comment> createComment(@RequestBody CommentDto commentDto) {
        Optional<Post> postOptional = postService.getPostById(commentDto.getPostId());
        System.out.println("안녕~~~ 댓글 컨트롤러야");
        // Post가 없으면 400에러
        if (!postOptional.isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        System.out.println("안녕 난 commentController야");
        // post 존재하면 댓글생성
        Post post = postOptional.get();
        System.out.println(post);
        Comment comment = new Comment();
        comment.setAuthor(commentDto.getAuthor());
        comment.setContent(commentDto.getContent());
        comment.setPost(post);

        System.out.println("작성자 : " +comment.getAuthor());
        System.out.println("e댓글내용 : " +comment.getContent());
        System.out.println("글 : " +comment.getPost().getId());

        Comment savedComment = commentService.saveComment(comment);
        return ResponseEntity.status(201).body(savedComment);
    }

    @PostMapping("/userBoard/{postId}/like")
    public ResponseEntity<Post> likePost(@PathVariable("postId") Long id){
        Post updatedPost = postService.incrementLikeCount(id);
        return ResponseEntity.ok(updatedPost);
    }

    @DeleteMapping("/userBoard/{postId}/delete")
    public ResponseEntity<Void> deletePost(@PathVariable("postId") Long postId) {
        Optional<Post> postOptional = postService.getPostById(postId);
        Post post=postOptional.get();
        if (post != null) {
            postService.deletePost(postId);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/userBoard/{postId}/edit")
    public String editPost(@PathVariable("postId") Long postId,Model model) {
        Optional<Post> postOptional = postService.getPostById(postId);
        Post post=postOptional.get();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUser = authentication.getName();
        System.out.println(currentUser);
        model.addAttribute("user", currentUser);
        model.addAttribute("post", post);

        return "/board/boardWrite";
    }

    @PostMapping("/userBoard/{postId}/edit")
    public String updatePost(@PathVariable("postId") Long postId,
                             @RequestParam("title") String title,
                             @RequestParam(name = "header") String header,
                             @RequestParam("content") String content,
                             Authentication authentication) {
        // 현재 사용자 이름 가져오기
        String currentUser = authentication.getName();

        // 포스트 가져오기
        Optional<Post> postOptional = postService.getPostById(postId);
        System.out.println("ㅎㅇㅎㅇ");
        if (!postOptional.isPresent()) {
            // 포스트가 없을 때 적절한 처리를 추가합니다. 예: 에러 페이지로 리디렉션
            return "redirect:/error";
        }
        System.out.println("ㅎ2ㅎ2");


        postService.updatePost(postId,title,header,content);

        System.out.println("ㅎ2ㅎ23");

        // 업데이트 후 포스트 보기 페이지로 리디렉션
        return "redirect:/board/userBoard/" + postId;
    }


    @PostMapping
    public void createPost(@RequestBody PostDto postDTO) {
        postService.createPost(postDTO);
    }
}

