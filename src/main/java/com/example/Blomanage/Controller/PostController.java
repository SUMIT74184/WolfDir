package com.example.Blomanage.Controller;


import com.example.Blomanage.Entity.Post;
import com.example.Blomanage.dto.CreatePostRequest;
import com.example.Blomanage.dto.PostResponse;
import com.example.Blomanage.dto.UpdatePostRequest;
import com.example.Blomanage.service.PostService;
import com.example.Blomanage.service.*;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import com.example.Blomanage.Entity.Tag;

import java.security.Principal;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    private final PostService service;
    private final DeletePostService deletePostService;
    private final EnableSpringDataWebSupport.SpringDataWebSettingsRegistrar springDataWebSettingsRegistrar;
    private final ArchiveService archiveService;

    public PostController(PostService service, DeletePostService deletePostService, EnableSpringDataWebSupport.SpringDataWebSettingsRegistrar springDataWebSettingsRegistrar, ArchiveService archiveService){
        this.service = service;
        this.deletePostService = deletePostService;
        this.springDataWebSettingsRegistrar = springDataWebSettingsRegistrar;
        this.archiveService = archiveService;
    }

    @PostMapping
    public PostResponse create(@RequestBody @Valid CreatePostRequest req, Principal principal ){

        Post post = service.create(
                req,
                Long.valueOf(principal.getName()),
                "Author Name"
        );
        return mapToResponse(post);
    }

    private PostResponse mapToResponse(Post post){
        PostResponse res = new PostResponse();
        res.setId(post.getId());
        res.setTitle(post.getTitle());
        res.setDescription(post.getDescription());
        res.setContent(post.getContent());
        res.setCoverImageUrl(post.getCoverImageUrl());
        res.setAuthorName(post.getAuthorName());
        res.setCreatedAt(post.getCreatedAt());
        res.setLikeCount(post.getLikeCount());
        res.setCommentCount(post.getCommentCount());
        res.setTags(
                post.getTags().stream().map(Tag::getName).collect(Collectors.toSet())
        );
        return res;
    }


    public PostResponse update(@PathVariable Long id,
                               @RequestBody UpdatePostRequest req,
                               Principal principal
    ){
        Post post = service.updatePost(id,req,Long.valueOf(principal.getName())
        );
        return mapToResponse(post);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id , Principal principal){
      deletePostService.deletePost(id,Long.valueOf(principal.getName()));
    }

    @PostMapping("/{id}/archive")
    public void archive(@PathVariable Long id, Principal principal){
        archiveService.archivePost(id,Long.valueOf(principal.getName()));
    }
}
