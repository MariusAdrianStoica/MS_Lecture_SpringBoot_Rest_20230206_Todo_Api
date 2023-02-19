package se.lexicon.ms_lecture_springboot_rest_20230206_todo_api.exception;

import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.UUID;

@ControllerAdvice
public class MyExceptionHandler extends ResponseEntityExceptionHandler {
    //step1: add @ControllerAdvice  at the top of the class name
    //step2: add -> extends ResponseEntityExceptionHandler
    //step3: add @ExceptionHandler and write a method to handle custom exception (DataNotFound..)
    //step4: add a new class in Exception - APIError -to manage all exceptions
    // look for the type of Exception in ResponseEntityExceptionHandler
    // -> make it public from protected, and remove return statement and customize it



    @Override
    public ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        //return this.handleExceptionInternal(ex, (Object)null, headers, status, request);
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "Malformed Json request!");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    @ExceptionHandler(DataNotFoundException.class)
    // @ExceptionHandler - handle custom exceptions
    public ResponseEntity<Object> dataNotFound(DataNotFoundException ex){
        //return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        // -> if we don't wnt to return the message, we will create ApiError
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    @ExceptionHandler(DataDuplicateException.class)
      public ResponseEntity<Object> dataDuplicate(DataDuplicateException ex) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    @ExceptionHandler(IllegalArgumentException.class)
       public ResponseEntity<Object> illegalArgument(IllegalArgumentException ex) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    //in order to handle duplicate exceptions for DB unique fields
    public ResponseEntity<Object> constraintViolation(ConstraintViolationException ex) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }


    @ExceptionHandler(Exception.class)
    // for any other unexpected or global exception
    public ResponseEntity<Object> globalException(Exception ex) {
        //ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        String errorCode = UUID.randomUUID().toString();
        ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "internal error (code: " + errorCode +")");
        // todo: add the Application error data into a file - How (Log4J, sl4J)
        // - don't save error in DB, in order to not affect the functionality, DB become slower

        System.out.println("INTERNAL_SERVER_ERROR : "+ errorCode + " , "+ ex);
        //printing the error - > in order to find it in application RunTime
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiError);
    }

    //validation part2 - constraints on fields
    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        /*
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "Invalid argument");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
         */
        StringBuilder stringBuilder = new StringBuilder();
        // StringBuilder used to collect errors from all constraints of the field
        // -> (name in RoleDto has 2 validations : @NotEmpty, @Size
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        for (FieldError fieldError: fieldErrors){

            stringBuilder.append(fieldError.getField());
            stringBuilder.append(": ");
            stringBuilder.append(fieldError.getDefaultMessage());
            stringBuilder.append(", ");
        }
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, stringBuilder.toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "Type mismatch");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }
}
