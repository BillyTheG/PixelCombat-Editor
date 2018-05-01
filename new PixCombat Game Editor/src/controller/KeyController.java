package controller;

import content.MainContent;
import content.misc.Console;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import math.BoundingRectangle;
import math.Vector2d;

public class KeyController implements EventHandler<KeyEvent> {
	private MainContent mainContent;
	private KeyEvent lastEvent;

	public KeyController(MainContent mainContent, Console console) {
		this.mainContent = mainContent;
	}

	@Override
	public void handle(KeyEvent e) {
		lastEvent = e;

		if (lastEvent.getEventType() == KeyEvent.KEY_RELEASED) {
			switch (lastEvent.getCode()) {
			case DELETE: {
				if (mainContent.getCurrPointer() != null) {
					mainContent.deleteBox(mainContent.getCurrPointer());
					mainContent.setCurrPointer(null);
					mainContent.repaint();

				}

			}
				break;
			case RIGHT: {
				if (mainContent.getCurrPointer() != null) {
					BoundingRectangle currB = mainContent.getCurrPointer();
					mainContent.getCurrPointer().edit(currB.getHeight(), new Vector2d(currB.getPos().x + 0.05f, currB.getPos().y), currB.getWidth());

					mainContent.repaint();

				}

			}
				break;
			case LEFT: {
				if (mainContent.getCurrPointer() != null) {
					BoundingRectangle currB = mainContent.getCurrPointer();
					mainContent.getCurrPointer().edit(currB.getHeight(), new Vector2d(currB.getPos().x - 0.05f, currB.getPos().y), currB.getWidth());

					mainContent.repaint();

				}

			}
				break;
			case UP: {
				if (mainContent.getCurrPointer() != null) {
					BoundingRectangle currB = mainContent.getCurrPointer();
					mainContent.getCurrPointer().edit(currB.getHeight(), new Vector2d(currB.getPos().x, currB.getPos().y - 0.05f), currB.getWidth());

					mainContent.repaint();

				}

			}
				break;
			case DOWN: {
				if (mainContent.getCurrPointer() != null) {
					BoundingRectangle currB = mainContent.getCurrPointer();
					mainContent.getCurrPointer().edit(currB.getHeight(), new Vector2d(currB.getPos().x, currB.getPos().y + 0.05f), currB.getWidth());

					mainContent.repaint();

				}

			}
				break;
			case C: {
				if (mainContent.getCurrPointer() != null) {

					mainContent.getMyToolBox().copyBox.fire();

				}

			}
				break;
			case V: {
				if (mainContent.getCurrPointer() != null) {

					mainContent.getMyToolBox().insertBox.fire();

				}

			}
				break;
			case Z: {
				if (mainContent.getCurrentIToolBox() != null) {
					mainContent.getCurrentIToolBox().repositionate();
					mainContent.updateImportant();
				}

			}
				break;	
			case F9:
				mainContent.getMainStage().setFullScreen(false);
				break;
			case F10:
				mainContent.getMainStage().setFullScreen(true);
				break;
			default:
				mainContent.updateImportant();
				break;

			}
		}

	}

}