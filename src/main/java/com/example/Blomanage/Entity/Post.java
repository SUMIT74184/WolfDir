package com.example.Blomanage.Entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@Getter
@Entity
@Table(name="posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long authorId;

    private String title;


    @Column(columnDefinition = "TEXT")
    private String content;

    private String CoverImageUrl;

    private String authorName;

    private LocalDateTime createdAt;

    private int likeCount;
    private int commentCount;

    @Column(columnDefinition = "TEXT")
    private String Description;

    @Enumerated(EnumType.STRING)
    private PostStatus status;

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }

    private LocalDateTime updateAt;



    public void setId(Long id) {
        this.id = id;
    }


    public void setStatus(PostStatus status) {
        this.status = status;
    }


    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCoverImageUrl(String coverImageUrl) {
        CoverImageUrl = coverImageUrl;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

public void setDescription(String description){
        this.Description=description;
}


    @ManyToMany
    @JoinTable(
    name="post_tags",
    joinColumns = @JoinColumn(name="post_id"),
    inverseJoinColumns = @JoinColumn(name="tag_id")
    )
    private Set<Tag> tags = new HashSet<>();

public Post(){

}


}
