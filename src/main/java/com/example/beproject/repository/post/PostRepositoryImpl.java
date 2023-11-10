package com.example.beproject.repository.post;

import com.example.beproject.domain.post.Post;
import com.example.beproject.entity.post.PostEntity;
import com.example.beproject.exception.CommentNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepository {

    private final PostJpaRepository postJpaRepository;

    @Override
    public Post createPost(Post post) {
        return postJpaRepository.save(PostEntity.from(post)).toDTO();
    }

    @Override
    public Post updatePost(Post updatedPost) {return postJpaRepository.save(PostEntity.from(updatedPost)).toDTO();
    }


    // 주어진 ID에 해당하는 게시글을 조회하여 Optional<Post>로 반환
    @Override
    public Post findById(long id) {
        Post post = postJpaRepository.findById(id).orElse(null).toDTO();

        return post;
    }

    @Override
    public List<Post> findAll() {
        List<PostEntity> postEntities = postJpaRepository.findAll();
        return postEntities.stream()
                .map(PostEntity::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        postJpaRepository.deleteById(id);
    }

}