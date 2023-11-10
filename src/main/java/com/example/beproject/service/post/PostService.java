package com.example.beproject.service.post;

import com.example.beproject.domain.post.CreatePost;
import com.example.beproject.domain.post.Post;
import com.example.beproject.domain.post.ResponsePost;
import com.example.beproject.domain.post.UpdatePost;

public interface PostService {

    Post createPost(CreatePost post);

    Post updatePost(Long id, UpdatePost updatePost);

    void deletePost(Long id);
}
