package com.example.beproject.entity.post;

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
    private Long id;

    @Column(name="post_subject")
    private String subject;

    @Column(name="post_detail")
    private String detail;

    @Column(name="post_writer")
    private Long writer;

    @Column(name="post_tag")
    private String tag;

    @Builder
    public PostEntity(Long id, String subject, String detail, Long writer, String tag) {
        this.id = id;
        this.subject = subject;
        this.detail = detail;
        this.writer = writer;
        this.tag = tag;
    }
}
