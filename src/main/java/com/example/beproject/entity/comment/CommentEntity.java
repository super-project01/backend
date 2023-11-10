package com.example.beproject.entity.comment;

import com.example.beproject.domain.comment.Comment;
import com.example.beproject.domain.comment.CommentStatus;
import com.example.beproject.domain.post.Post;
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

    @Column(name = "comment_write")
    private Long write;

    @Column(name = "comment_contents")
    private String contents;

    @Column(name = "comment_orgid")
    private Long orgid;

    @Column(name = "comment_subid")
    private Long subid;

    @Column(name = "comment_status")
    private CommentStatus status;

    @ManyToOne(targetEntity = PostEntity.class)// 다대일(One-to-Many) : 게시글-댓글
    @JoinColumn(name = "post_id") //게시글에서 외래키를 받아오기 위해 사용
    private Post post;; //게시글 아이디  클래스 이름과 필드 이름이 충돌할 수 있어 넣어봄.
    //PostEntity id 부분에 @Column git checkout이름 변경 부탁드리기. (=충돌예방과 가독성 이유)
    //PostEntity 필드를 넣으면 댓글이 어떤 게시글에 대한 댓글인지를 나타내며, 데이터베이스 테이블 간의 관계를 설정하는데 도움

    @Builder
    public CommentEntity(Long id, Long write, String contents, Long orgid, Long subid, CommentStatus status, Post post) {
        this.id = id;
        this.write = write;
        this.contents = contents;
        this.orgid = orgid;
        this.subid = subid;
        this.status = status;
        this.post = post;
    }

    public static CommentEntity from(Comment comment) {
        return CommentEntity.builder()
                .write(comment.getWrite())
                .contents(comment.getContents())
                .orgid(comment.getOrgid())
                .subid(comment.getSubid())
                .status(comment.getStatus())
                .post(Post.from(PostEntity.from(comment.getPost())))
                .build();
    }

    public Comment toDTO() {
        return Comment.builder()
                .id(this.id)
                .write(this.write)
                .contents(this.contents)
                .orgid(this.orgid)
                .subid(this.subid)
                .status(this.status)
                .post(this.post)//.toDTO() post는 직접받기
                .build();
    }
}