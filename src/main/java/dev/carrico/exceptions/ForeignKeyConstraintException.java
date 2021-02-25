package dev.carrico.exceptions;

public class ForeignKeyConstraintException extends RuntimeException{
    public ForeignKeyConstraintException(){
        super("Update or delete on table violates foreign key constraint");
    }
}
