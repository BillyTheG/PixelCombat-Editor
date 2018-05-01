package main;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import content.MainContent;
import content.misc.Console;
import content.misc.ConsoleScene;
import content.misc.ConsoleStage;
import content.misc.Other;
import content.misc.WordWrapConsole;
import controller.KeyController;
import controller.MouseController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Editor extends Application {

	public static final float 	FIELD_SIZE = 50f;
	private MainContent 		mainContent;
	private Scene 				mainScene;
	private Group 				mainRoot;
	private Stage 				mainStage;

	private Console 			console;
	private Scene 				consoleScene;
	private Group 				consoleRoot;
	private Stage 				consoleStage;

	private MouseController 	mouseController;
	private KeyController 		keyController;

	public float 				currX;
	public float 				currY;
	private int 				width;
	private int 				height;
	private GraphicsDevice 		gd;

	@Override
	public void start(Stage primaryStage) {

		// get Screen Size
		gd 							= GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		width 						= gd.getDisplayMode().getWidth();
		height 						= gd.getDisplayMode().getHeight();
		
		// consoleSetup
		this.consoleStage 			= new ConsoleStage();
		this.consoleRoot 			= new Group();			
		Canvas consoleCanvas 		= new Canvas();
		this.consoleRoot.getChildren().add(consoleCanvas);			
		this.consoleScene 			= new ConsoleScene(consoleRoot,consoleCanvas);			
		this.console 				= new WordWrapConsole((int)consoleScene.getWidth(),(int) consoleScene.getHeight());
		this.console.println("Loading Elements");		
		this.consoleRoot.getChildren().add(console);
		
		this.consoleStage.setTitle("PixelCombat Editor Console");
		this.consoleStage.getIcons().add(Other.loadImage("/images/menu/IMG_Editor_Icon.png"));
		this.consoleStage.setResizable(false);
		this.consoleStage.setScene(consoleScene);
		
		// main setup
		
		Canvas mainCanvas 			= new Canvas();
		
		this.mainStage 				= primaryStage;
		this.mainRoot 				= new Group();
		this.mainScene 				= new Scene(mainRoot, width, height);
		this.mainContent 			= new MainContent(mainRoot, mainCanvas, console, mainStage, width, height);
		this.mouseController 		= new MouseController(mainContent, console);
		this.keyController 			= new KeyController(mainContent, console);
		
		this.mainStage.setTitle("PixelCombat Editor");
		this.mainStage.setResizable(true);
		this.mainStage.setFullScreen(true);
		this.mainStage.setScene(mainScene);
		this.mainStage.getIcons().add(Other.loadImage("/images/menu/IMG_Editor_Icon.png"));
		this.mainStage.addEventHandler(MouseEvent.ANY, mouseController);
		this.mainStage.addEventHandler(KeyEvent.ANY, keyController);		
		
		//close console if main is closed
		this.mainStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent arg0) {
				consoleStage.close();
			}
		});
		
		//show both
		this.mainStage.show();
		this.consoleStage.show();
				
		console.println("Console loaded succesfully");
		console.println("Main 	 loaded succesfully");
	}

	public static void main(String[] args) {
		launch(args);
	}

	public Console getConsole() {
		return console;
	}

}