package com.example.Blomanage.service;

import com.example.Blomanage.Entity.Post;
import com.example.Blomanage.Entity.PostStatus;
import com.example.Blomanage.Repository.PostRepository;
import org.springframework.stereotype.Service;

@Service
public class ArchiveService{
    private final PostRepository postRepository;

    public ArchiveService(PostRepository postRepository){
      this.postRepository=postRepository;
    }

    public void archivePost(Long postId, Long userId) {
        Post post = postRepository.findById(postId).orElseThrow();

        if (!post.getAuthorId().equals(userId)) {
            throw new RuntimeException("Not allowed");
        }

        post.setStatus(PostStatus.ARCHIVED);
    }


}
