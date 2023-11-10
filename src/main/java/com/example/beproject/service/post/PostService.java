package com.example.beproject.service.post;

import com.example.beproject.domain.post.CreatePost;
import com.example.beproject.domain.post.Post;
import com.example.beproject.domain.post.ResponsePost;
import com.example.beproject.domain.post.UpdatePost;

public interface PostService {

    //반환값은 front랑 정하기 나름임
    //create -> dto
    Post createPost(CreatePost post);

    Post updatePost(Long id, UpdatePost updatePost);

}
