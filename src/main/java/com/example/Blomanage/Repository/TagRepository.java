package com.example.Blomanage.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.Blomanage.Entity.Tag;

import java.util.Optional;

public interface TagRepository extends JpaRepository <Tag,Long>{
    Optional<Tag> findByName(String name);
}
