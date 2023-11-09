package com.example.beproject.repository.post;

import com.example.beproject.domain.post.Post;

import java.util.List;

public interface PostRepository {

    //등록
    Post save(Post post); //이현아 구현 ~ getpost까지

    //수정
    Post update(Post post);

    //삭제
    void delete(Long postId);
    //조회
    List<Post> findAll();

    Post getPost(Long postId);
}
