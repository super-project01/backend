package com.example.beproject.repository.post;

import com.example.beproject.entity.post.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostJpaRepository extends JpaRepository<PostEntity, Long> {
}
