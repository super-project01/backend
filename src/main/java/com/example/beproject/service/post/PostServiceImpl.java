package com.example.beproject.service.post;

import com.example.beproject.domain.post.CreatePost;
import com.example.beproject.domain.post.Post;
import com.example.beproject.domain.post.PostStatus;
import com.example.beproject.domain.post.UpdatePost;
import com.example.beproject.repository.post.PostRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@Service
@Builder
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Override
    @Transactional
    public Post createPost(CreatePost post) {
        //Controller에서 createPost로 받았으니까 PostRepository에 Post로 넘겨줘야하니까
        //createPost를 postDTO로 변환하는 작업필요.
        Post newPost = Post.builder()
                .subject(post.getSubject())
                .detail(post.getDetail())
                .writer(post.getWriter())
                .tag(post.getTag())
                .status(PostStatus.NEW)
                .build();

        return postRepository.createPost(newPost);
    }

    @Override
    public String updatePost(UpdatePost updatePost) {
        return null;
    }
}
