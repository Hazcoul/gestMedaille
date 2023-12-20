package bf.gov.gcob.medaille.exception;

public class CreateNewElementException extends RuntimeException{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CreateNewElementException(){
        super("un nouvel element ne peut pas avoir un id");
    }
}
