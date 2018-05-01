package content.menu;

import content.MainContent;
import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;
import math.Vector2d;

public abstract class MenuObject {

	protected 	Vector2d pos;
	protected 	Group root;
	protected  	MainContent contentManager;

	public MenuObject(Vector2d pos, Group root,MainContent contentManager) {
		this.setPos(pos);
		this.setRoot(root);
		this.contentManager = contentManager;
		init();
	}

	public abstract void repaint(GraphicsContext graphicsContext);
	public abstract void update();
	public abstract void init();
	public abstract void disableObjects(boolean disable);
	
	
		
	public Vector2d getPos() {
		return pos;
	}

	public void setPos(Vector2d pos) {
		this.pos = pos;
	}

	public Group getRoot() {
		return root;
	}

	public void setRoot(Group root) {
		this.root = root;
	}

}
;