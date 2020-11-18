package exceptions;

import enums.ExceptionGroup;

public class ContentNullException extends PXEditorException{

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ContentNullException(String picture_seq) {
		super("The content for "+picture_seq +" is null for some parts. Check your XML-File.", ExceptionGroup.NULLPOINTER);
	}

}
