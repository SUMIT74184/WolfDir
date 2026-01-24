package com.example.Blomanage.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.Blomanage.Entity.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment>findByPostId(Long postId);

}
