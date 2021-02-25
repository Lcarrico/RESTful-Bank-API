package dev.carrico.exceptions;

public class NoClientFoundException extends RuntimeException{

    public NoClientFoundException(){
        super("No client exists with specified ID");
    }

}
