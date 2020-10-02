package exceptions;

import enums.ExceptionGroup;

public class AnimatorNoContentException extends PXEditorException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public AnimatorNoContentException(){
		super("Animator cannot animate. ", ExceptionGroup.ANIMATION);
	}
	
}
