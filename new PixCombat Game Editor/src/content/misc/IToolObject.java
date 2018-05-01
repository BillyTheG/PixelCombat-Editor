package content.misc;

import javafx.scene.Group;
import math.Vector2d;

public interface IToolObject {

	
	public void 	mark(Group root);
	public void 	unmark(Group root);
	public boolean	isMarked();
	
	public Vector2d	getLastPos();
	public void		setLastPos(Vector2d pos);
	public void		updateCorner();
	public void		setPos(Vector2d pos);
	
	default void repositionate(){
		if(!isMarked())
			return;
		setPos(new Vector2d(getLastPos().x,getLastPos().y));
		updateCorner();
	}
	
	
}
