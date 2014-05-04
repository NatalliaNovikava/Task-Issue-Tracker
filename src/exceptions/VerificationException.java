package exceptions;

public class VerificationException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public VerificationException() {}
    
	public VerificationException(String message) {
    	super(message);
    }
  
	public VerificationException(Throwable cause) {
    	super(cause);
    }
   
	public VerificationException(String message, Throwable cause) {
    	super(message, cause);
    }

}
