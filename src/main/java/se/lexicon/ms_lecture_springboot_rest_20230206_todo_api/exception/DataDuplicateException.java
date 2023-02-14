package se.lexicon.ms_lecture_springboot_rest_20230206_todo_api.exception;

public class DataDuplicateException extends RuntimeException{
    public DataDuplicateException(String message) {
        super(message);
    }
}
