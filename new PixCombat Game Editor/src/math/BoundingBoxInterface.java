package math;

import javafx.scene.canvas.GraphicsContext;



/**
 * Interface for bounding boxes like bounding rectangles or bounding circles
 *
 * 
 * 
 * @author BillyG
 * @version 0.1
 */
public interface BoundingBoxInterface {

	/**
	 * Checks if the vector is inside of the bounding box
	 * 
	 * @param v vector
	 * @return is inside bounding box
	 */
	public boolean isCollision(Vector2d v);
	
	public abstract void draw(GraphicsContext g);

	public abstract boolean isMarked();
}