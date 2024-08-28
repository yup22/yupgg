package com.yupGG.service;

import com.yupGG.entity.Comment;
import com.yupGG.entity.Post;
import com.yupGG.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    CommentRepository commentRepository;

    public Comment saveComment(Comment comment) {
        return commentRepository.save(comment);
    }

    //특정 게시글에 달린 댓글조회
    public List<Comment> getCommentByPost(Post post) {
        return commentRepository.findByPost(post);
    }
}
