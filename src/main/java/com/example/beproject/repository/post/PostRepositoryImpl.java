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
    public Post save(Post post) {
        return postJpaRepository.save(PostEntity.from(post)).toDTO();
    }

    @Override
    public Post updatePost(Post post) {
        return null;
    }

    @Override
    public void deletePost(Long postId) {

    }

    @Override
    public Post createPost(Post post) {
        return postJpaRepository.save(PostEntity.from(post)).toDTO();
    }

    @Override
    public void delete(Long postId) {//이현아 추가
        postJpaRepository.deleteById(postId);
    }


    // 주어진 ID에 해당하는 게시글을 조회하여 Optional<Post>로 반환
    @Override
    public Post update(Post post) {
        PostEntity existingPostEntity = postJpaRepository.findById(post.getId())
                .orElseThrow(() -> new CommentNotFoundException.PostNotFoundException(post.getId()));

        PostEntity updatedEntity = postJpaRepository.save(existingPostEntity);
        return updatedEntity.toDTO();
    }

    @Override
    public List<Post> findAll() {
        List<PostEntity> postEntities = postJpaRepository.findAll();
        return postEntities.stream()
                .map(PostEntity::toDTO)
                .collect(Collectors.toList());
    }

    @Override //이현아 추가
    public Post getPost(Long postId) {
        return postJpaRepository.findById(postId)
                .map(PostEntity::toDTO)
                .orElse(null);
    }

    @Override
    public Post createPost(Long postId) {
        return null;//이현아 추가
    }


}