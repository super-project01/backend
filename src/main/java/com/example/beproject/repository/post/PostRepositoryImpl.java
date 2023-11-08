package com.example.beproject.repository.post;

import com.example.beproject.domain.post.Post;
import com.example.beproject.entity.post.PostEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepository {

    private final PostJpaRepository postJpaRepository;

    @Override
    public Post save(Post post) {
        return null;
    }

    @Override
    public List<Post> findAll() {
        return null;
    }
}
