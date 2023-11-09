package com.example.beproject.repository.post;

import com.example.beproject.domain.post.Post;
import com.example.beproject.entity.post.PostEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepository {

    private final PostJpaRepository postJpaRepository;

    @Override//이현아 수정 ~getPost까지
    public Post save(Post post) {
        return null;
    }

    @Override
    public void delete(Long postId) {
        postJpaRepository.deleteById(postId);
    }

    @Override
    public Post update(Post post) {
        return null;
    }

    @Override
    public List<Post> findAll() {
        List<PostEntity> postEntities = postJpaRepository.findAll();
        return postEntities.stream()
                .map(PostEntity::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Post getPost(Long postId) {
        return postJpaRepository.findById(postId)
                .map(PostEntity::toDTO)
                .orElse(null);
    }
}