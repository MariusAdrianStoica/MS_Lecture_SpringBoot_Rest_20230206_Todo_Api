package se.lexicon.ms_lecture_springboot_rest_20230206_todo_api.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
public class ApiError {
    private HttpStatus status;// status is an enumeration, so it is better to use HttpStatus
    private String statusText;
    private LocalDateTime timestamp;

    public ApiError() {
        this.timestamp=LocalDateTime.now();
    }

    public ApiError(HttpStatus status, String statusText) {
        this();
        this.status = status;
        this.statusText = statusText;
    }
}
