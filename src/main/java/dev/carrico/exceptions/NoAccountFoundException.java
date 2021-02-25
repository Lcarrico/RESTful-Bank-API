package dev.carrico.exceptions;

public class NoAccountFoundException extends RuntimeException{
    public NoAccountFoundException(){
        super("No account has been found with specified ID");
    }
}
