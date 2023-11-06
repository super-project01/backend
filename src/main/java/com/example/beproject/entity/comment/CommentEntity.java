package com.example.beproject.entity.comment;

import com.example.beproject.entity.post.PostEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.*;

@Entity
@Getter
@Table(name="comment")
@NoArgsConstructor
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="comment_write")
    private Long write;

    @Column(name="comment_contents")
    private String contents;

    @Column(name="comment_orgid")
    private Long orgid;

    @Column(name="comment_subid")
    private Long subid;

    @Column(name="comment_status")
    private String status;

    @ManyToOne // 다대일(One-to-Many) : 게시글-댓글
    @JoinColumn(name="post_id") //게시글에서 외래키를 받아오기 위해 사용
    private PostEntity post; //게시글 아이디  클래스 이름과 필드 이름이 충돌할 수 있어 넣어봄.
    //PostEntity id 부분에 @Column 이름 변경 부탁드리기. (=충돌예방과 가독성 이유)
    //PostEntity 필드를 넣으면 댓글이 어떤 게시글에 대한 댓글인지를 나타내며, 데이터베이스 테이블 간의 관계를 설정하는데 도움

    @Builder
    public CommentEntity(Long id, Long write, String contents, Long orgid, Long subid, String status, PostEntity post) {
        this.id = id;
        this.write = write;
        this.contents = contents;
        this.orgid = orgid;
        this.subid = subid;
        this.status = status;
        this.post = post;  //게시글 아이디 초기화
    }
}
