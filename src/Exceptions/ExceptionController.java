package Exceptions;

public class ExceptionController extends RuntimeException{
    public ExceptionController(String errorMessage) {
        super(errorMessage);
    }
}