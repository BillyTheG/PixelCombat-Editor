package exceptions;

import enums.ExceptionGroup;
import lombok.Getter;



public class PXEditorException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8584779087297181897L;
	@Getter
	private ExceptionGroup exceptionGroup;

	
	public PXEditorException(String message, ExceptionGroup exceptionGroup){
		super(message);
		this.exceptionGroup = exceptionGroup;		
	}
	
	
}
