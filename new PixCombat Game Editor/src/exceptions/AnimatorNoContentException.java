package exceptions;

import java.util.List;

import content.LocatedImage;

public class AnimatorNoContentException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public AnimatorNoContentException(List<LocatedImage> images, List<Float> times){
		super("Error in Image List either in Time List. \n Images List: " + images + "\n Times List: "+ times);
	}
	
}
