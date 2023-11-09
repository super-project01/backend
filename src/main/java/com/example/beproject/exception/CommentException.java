package com.example.beproject.exception;

public class CommentException extends RuntimeException{
    public CommentException(String message) {
        super(message);
    }

    public static class CommentNotFoundException extends Exception{
        public CommentNotFoundException(Long id) {
            super("Comment not found with id: " + id);
        }//Exception 예외처리를 위한 클래스
    }
}
