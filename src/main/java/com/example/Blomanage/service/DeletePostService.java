package com.example.Blomanage.service;

import com.example.Blomanage.Entity.Post;
import com.example.Blomanage.Entity.PostStatus;
import com.example.Blomanage.Repository.PostRepository;
import org.springframework.stereotype.Service;

@Service
public  class DeletePostService{
    private final PostRepository postRepository;

    // Constructor injection for the repository
    public DeletePostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public void deletePost(Long postId, Long userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(()->new RuntimeException(("Post not found")));

        if (!post.getAuthorId().equals(userId)) {
            throw new RuntimeException("Not allowed");
        }

        post.setStatus(PostStatus.DELETED);
    }
}


