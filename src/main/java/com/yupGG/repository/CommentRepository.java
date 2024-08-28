package com.yupGG.repository;

import com.yupGG.entity.Comment;
import com.yupGG.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    // 특정 게시글에 속한 댓글 가져오는 메서드
    List<Comment> findByPost(Post post);

}
