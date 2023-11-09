package com.example.beproject.exception;

public class CommentNotFoundException extends RuntimeException {
    public CommentNotFoundException(Long Id) {
        super("찾을 수 없습니다." + Id);
    }

    public static class PostNotFoundException extends Throwable {
        public PostNotFoundException(Long postId) {
            super("찾을 수 없습니다." + postId);   //중복을 피하기 위해 postid로 묶어버림
        }

    }
}