package com.example.beproject.service.post;

import com.example.beproject.domain.post.*;
import com.example.beproject.entity.post.PostEntity;
import com.example.beproject.exception.PostNotFoundException;
import com.example.beproject.exception.PostNotUpdatedException;
import com.example.beproject.repository.post.PostRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

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
    public Post updatePost(Long id, UpdatePost updatePost) {
        // id로 게시글 정보를 조회
        Post orginPost = postRepository.findById(id);

        if (orginPost != null) {

            // 게시글 정보 업데이트
            orginPost.updatePost(updatePost.getSubject(), updatePost.getDetail(), updatePost.getTag());

            // 게시글 정보 저장 및 반환
            return postRepository.createPost(orginPost);

        }
        // 조회한 게시글이 존재하지 않는 경우 null 반환
        return null;
    }


    @Override
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

}

