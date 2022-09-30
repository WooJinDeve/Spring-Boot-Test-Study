package spring.testcode.web.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import spring.testcode.dto.response.CommonResponse;
import spring.testcode.dto.response.ErrorResponse;
import spring.testcode.exception.BookSaveValidateException;
import spring.testcode.exception.NoSuchBookItemException;
import spring.testcode.web.BookApiController;

@RestControllerAdvice(basePackageClasses = BookApiController.class)
public class BookControllerExceptionHandler {

    @ExceptionHandler(BookSaveValidateException.class)
    public ResponseEntity<?> bookSaveException(BookSaveValidateException e){
        return new ResponseEntity<>(CommonResponse.fail(ErrorResponse.builder().errorCode(400)
                .errorMessage(e.getMessage()).build()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchBookItemException.class)
    public ResponseEntity<?> findBookException(NoSuchBookItemException e){
        return new ResponseEntity<>(CommonResponse.fail(ErrorResponse.builder().errorCode(400)
                .errorMessage(e.getMessage()).build()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> apiException(RuntimeException e){
        return new ResponseEntity<>(CommonResponse.fail(ErrorResponse.builder().errorCode(400)
                .errorMessage(e.getMessage()).build()), HttpStatus.BAD_REQUEST);
    }
}
