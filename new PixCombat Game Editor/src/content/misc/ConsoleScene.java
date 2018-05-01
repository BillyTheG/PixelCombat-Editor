package content.misc;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;

public class ConsoleScene extends Scene{

	private Canvas 				canvas;
	public  static final Image 	bground = Other.loadImage("/images/menu/IMG_MenuBox_Console.png");
	
	public ConsoleScene(Group root, Canvas canvas) {
		super(root, bground.getWidth(), bground.getHeight());
		this.canvas = canvas;
		canvas.setHeight(getHeight());
		canvas.setWidth(getWidth());
		initStuff();
		
	}


	private void initStuff() {
		canvas.getGraphicsContext2D().drawImage(bground, 0, 0);		
	}

	

}
