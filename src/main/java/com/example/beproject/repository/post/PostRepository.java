package com.example.beproject.repository.post;

import com.example.beproject.domain.post.Post;

import java.util.List;

public interface PostRepository {

    //등록 ->return DTO
    Post createPost(Post post);

    //수정


    //삭제

    //조회
    public List<Post> findAll();
}
