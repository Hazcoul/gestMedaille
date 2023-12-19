package bf.gov.gcob.medaille.exception;

public class CreateNewElementException extends RuntimeException{
    public CreateNewElementException(){
        super("un nouvel element ne peut pas avoir un id");
    }
}
