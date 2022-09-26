package com.pureenergy.exception;

public class NoSuchMovieException extends RuntimeException{
        public NoSuchMovieException(Long id){
            super("There is no movie belongs to id " + id);
        }
}