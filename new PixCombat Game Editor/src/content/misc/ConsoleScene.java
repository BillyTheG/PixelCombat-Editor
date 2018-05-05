package content.misc;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;

public class ConsoleScene extends Scene{

	private Canvas 				      canvas;
	private Group				      root;
	private Console				      console;
	public  static final	Image 	  bground = Other.loadImage("/images/menu/IMG_MenuBox_Console.png");
	private static final 	ImageView CLEAR_HOVERED = new ImageView(Other.BUTTONICON_CLEAR_HOVERED);
	private static final 	ImageView CLEAR_UNHOVERED = new ImageView(Other.BUTTONICON_CLEAR);
	public Button clear;
		
	public ConsoleScene(Group root, Console console, Canvas canvas) {
		super(root, bground.getWidth(), bground.getHeight());
		this.root = root;
		this.console = console;
		this.canvas = canvas;
		canvas.setHeight(getHeight());
		canvas.setWidth(getWidth());
		initStuff();		
	}


	private void initStuff() {
		canvas.getGraphicsContext2D().drawImage(bground, 0, 0);
		initClearButton();	
		root.getChildren().add(clear);
	}


	private void initClearButton() {
		//init
		clear = new Button("", CLEAR_UNHOVERED);
		clear.setBackground(Background.EMPTY);
		//position
		clear.setLayoutX(bground.getWidth()-110);
		clear.setLayoutY(11);
		//hovering property
		clear.setOnMouseEntered(e -> clear.setGraphic(CLEAR_HOVERED));
		clear.setOnMouseExited(e -> clear.setGraphic(CLEAR_UNHOVERED));	
		//action property
		clear.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				console.clear();
			}
		});
	}

	

}
