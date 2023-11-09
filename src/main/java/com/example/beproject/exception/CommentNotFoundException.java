package com.example.beproject.exception;

public class CommentNotFoundException extends RuntimeException {
    public CommentNotFoundException(Long Id) {
        super("찾을 수 없습니다." + Id);
    }

    public static class PostNotFoundException extends RuntimeException {
        public PostNotFoundException(String message) {
            super("댓글을 찾을 수 없습니다. " + message);
        }
    }
}