package com.example.beproject.service.post;

import com.example.beproject.domain.post.CreatePost;
import com.example.beproject.domain.post.Post;
import com.example.beproject.domain.post.UpdatePost;

public interface PostService {

    //반환값은 front랑 정하기 나름임
    //create -> dto
    Post createPost(CreatePost post);


    //update delete -> String
    String updatePost(UpdatePost updatePost);
}
