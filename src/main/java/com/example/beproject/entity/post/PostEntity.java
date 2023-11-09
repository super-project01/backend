package com.example.beproject.entity.post;

import com.example.beproject.domain.post.Post;
import com.example.beproject.domain.post.PostStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Table(name="post")
@NoArgsConstructor
public class PostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @Column(name="post_subject")
    private String subject;

    @Column(name="post_detail")
    private String detail;

    @Column(name="post_writer")
    private Long writer;

    @Column(name="post_tag")
    private String tag;

    @Column(name="post_status")
    @Enumerated(EnumType.STRING)
    private PostStatus status;


    @Builder
    public PostEntity(Long id, String subject, String detail, Long writer, String tag, PostStatus status) {
        this.id = id;
        this.subject = subject;
        this.detail = detail;
        this.writer = writer;
        this.tag = tag;
        this.status = status;
    }

    //DTO to Entity
    public static PostEntity from(Post post){
        return PostEntity.builder()
                .id(post.getId())
                .subject(post.getSubject())
                .detail(post.getDetail())
                .writer(post.getWriter())
                .tag(post.getTag())
                .status(post.getStatus())
                .build();
    }


    //Entity to DTO
    public Post toDTO(){
        return Post.builder()
                .id(this.id)
                .subject(this.subject)
                .detail(this.detail)
                .writer(this.writer)
                .tag(this.tag)
                .status(this.status)
                .build();
    }

    // 이현아 추가
    public Post getPost() {
        return toDTO();
    }

}
