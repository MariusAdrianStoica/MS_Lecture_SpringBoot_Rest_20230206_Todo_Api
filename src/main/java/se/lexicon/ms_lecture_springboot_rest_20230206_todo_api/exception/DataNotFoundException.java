package se.lexicon.ms_lecture_springboot_rest_20230206_todo_api.exception;

public class DataNotFoundException extends RuntimeException{
    public DataNotFoundException(String message) {
        super(message);
    }
}
