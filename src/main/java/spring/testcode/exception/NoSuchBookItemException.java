package spring.testcode.exception;

public class NoSuchBookItemException extends RuntimeException{
    public NoSuchBookItemException(String message) {
        super(message);
    }
}
