package spring.testcode.exception;

public class BookSaveValidateException extends RuntimeException{
    public BookSaveValidateException(String message) {
        super(message);
    }
}
