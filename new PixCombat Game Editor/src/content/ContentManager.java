package content;

import content.misc.Console;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public abstract class ContentManager {

	//setup
	protected GraphicsContext graphicsContext;
	protected Group root;
	protected Canvas canvas;
	public Console console;
	public float currX;
	public float currY;
	public float lastX;
	public float lastY;

	public ContentManager(Group root, Canvas canvas, Console console) {
		this.root = root;
		this.canvas = canvas;
		this.console = console;
		
	}

	public ContentManager(Group root) {
		this.root = root;
	}
	
	public abstract void init(Canvas canvas);

	public abstract void update(float x, float y);

	public Group getRoot() {
		return root;
	}
	
	

	

}
