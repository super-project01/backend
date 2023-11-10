package com.example.beproject.repository.post;

import com.example.beproject.domain.post.Post;
import com.example.beproject.entity.post.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository {

    //등록
    Post createPost(Post post);

    //수정
    Post updatePost(Post updatedPost);

    //삭제
    void deleteById(Long id);

    //게시글id로 게시글 조회
    Optional<Post> findById(long id);


}
