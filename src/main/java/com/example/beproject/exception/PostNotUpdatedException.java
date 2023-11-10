package com.example.beproject.exception;

public class PostNotUpdatedException extends RuntimeException{

    public PostNotUpdatedException(){
        super("해당 게시물을 수정할 수 없습니다. 제목이나 내용은 빈칸이 될 수 없습니다.");
    }

}
