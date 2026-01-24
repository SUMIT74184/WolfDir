package com.example.Blomanage.service;

import com.example.Blomanage.Entity.Post;
import com.example.Blomanage.Repository.PostRepository;
import com.example.Blomanage.Repository.TagRepository;
import com.example.Blomanage.dto.CreatePostRequest;
import com.example.Blomanage.dto.UpdatePostRequest;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import com.example.Blomanage.Entity.Tag;

import java.time.LocalDateTime;
import java.util.Set;

import java.util.stream.Collectors;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final TagRepository tagRepository;

    public PostService(PostRepository postRepo,TagRepository tagRepo){
        this.postRepository=postRepo;
        this.tagRepository=tagRepo;
    }

    public Post create(CreatePostRequest dto,Long UserId,String authorName){
        Post post = new Post();
        post.setTitle(dto.getTitle());
        post.setDescription(dto.getDescription());
        post.setCoverImageUrl(dto.getCoverImageUrl());
        post.setContent(dto.getContent());


        post.setAuthorName(authorName);
        post.setAuthorId(post.getAuthorId());
        post.setCreatedAt(LocalDateTime.now());

        Set<Tag> tags=dto.getTags().stream()
                .map(this::findOrCreateTag)
                .collect(Collectors.toSet());

        post.setTags(tags);

        return postRepository.save(post);
    }

    private Tag findOrCreateTag(String name){
        return tagRepository.findByName(name)
                .orElseGet(()->tagRepository.save(new Tag(name)));
    }

    @Transactional
    public Post updatePost(Long postId,
                           UpdatePostRequest dto,
                           Long userId) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        if (!post.getAuthorId().equals(userId)) {
            throw new RuntimeException("Not allowed");
        }

        post.setTitle(dto.getTitle());
        post.setDescription(dto.getDescription());
        post.setContent(dto.getContent());
        post.setUpdateAt(LocalDateTime.now());

        if (dto.getTags() != null) {
            Set<Tag> tags = dto.getTags().stream()
                    .map(this::findOrCreateTag)
                    .collect(Collectors.toSet());
            post.setTags(tags);
        }

        return post;
    }



}
