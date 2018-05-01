package controller;

import content.MainContent;
import content.misc.Console;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import main.Editor;
import math.Vector2d;

public class MouseController implements EventHandler<MouseEvent> {
	private MainContent mainContent;
	private MouseEvent lastEvent;
	private Console console;

	public MouseController(MainContent mainContent, Console console) {
		this.mainContent = mainContent;
		this.console = console;
	}

	@Override
	public void handle(MouseEvent e) {
		lastEvent = e;

		if (lastEvent.getEventType() == MouseEvent.MOUSE_CLICKED) {
			console.println("clicked on (" + lastEvent.getX() + ", " + lastEvent.getY() + ")");
			float currX = (float) e.getX();
			float currY = (float) e.getY();
			mainContent.update(currX, currY);

		} 
		
		else if (lastEvent.getEventType() == MouseEvent.MOUSE_PRESSED) {

			if(mainContent.isDisableUpdate())
				return;
			
			float currX = (float) e.getX();
			float currY = (float) e.getY();
			
			currX = (float) e.getScreenX();
			currY = (float) e.getScreenY();			
			
			if (mainContent.getCurrPointer() != null && mainContent.getCurrPointer().isMarked())
				mainContent.getCurrPointer().setLastPos(new Vector2d(currX/Editor.FIELD_SIZE, currY/Editor.FIELD_SIZE));
			else if (mainContent.getCurrentImage() != null && mainContent.getCurrentImage().isMarked())
				mainContent.getCurrentImage().setLastPos(new Vector2d(currX/Editor.FIELD_SIZE, currY/Editor.FIELD_SIZE));
			
			mainContent.update(currX, currY);
		} 
		
		else if (lastEvent.getEventType() == MouseEvent.MOUSE_RELEASED) {
		
		} 
	
		else if (lastEvent.getEventType() == MouseEvent.MOUSE_DRAGGED) {
			
			if(mainContent.isDisableUpdate())
				return;
			
			float currX = (float) e.getScreenX();
			float currY = (float) e.getScreenY();
						
			if (mainContent.getCurrPointer() != null && mainContent.getCurrPointer().isMarked())
				mainContent.getCurrPointer().moveBox(currX, currY, mainContent);
			else if (mainContent.getCurrentImage() != null && mainContent.getCurrentImage().isMarked())
				mainContent.getCurrentImage().moveImage(currX, currY, mainContent);
		
		}

	}

}