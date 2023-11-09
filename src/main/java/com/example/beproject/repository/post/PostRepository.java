package com.example.beproject.repository.post;

import com.example.beproject.domain.post.Post;
import java.util.List;
import java.util.Optional;

public interface PostRepository {

    Post save(Post post);

    Post updatePost(Post post);

    void deletePost(Long postId);

     //이현아 구현 ~findById

    List<Post> findAll();

    Post createPost(Post post);

    void delete(Long postId);

    Post update(Post post);

    //이현아 추가
    Post getPost(Long postId);

    Post createPost(Long postId);

}