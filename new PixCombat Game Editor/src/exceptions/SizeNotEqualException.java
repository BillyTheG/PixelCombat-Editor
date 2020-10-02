package exceptions;

import enums.ExceptionGroup;

public class SizeNotEqualException extends PXEditorException {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	public SizeNotEqualException(int expected, int wrong) {
		super("Expected Elements: "+ expected+ ", Got: "+wrong, ExceptionGroup.WRONG_SIZE);
		
	}

	
}
