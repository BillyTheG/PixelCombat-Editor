package content.menu;

import content.LocatedImage;
import content.MainContent;
import content.misc.Other;
import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;
import main.Editor;
import math.Vector2d;

public class EditEffect extends MenuObject {

	public  static final LocatedImage bground = Other.loadImage("/images/menu/IMG_MenuBox_EditEffect.png");

	private float  xOffset = 0f;
	private float  yOffset = 0f;

	public EditEffect(Vector2d pos, Group root, MainContent contentManager) {
		super(pos, root, contentManager);
		yOffset = ((float)(contentManager.getScreen_height() - bground.image.getHeight() -20))/ Editor.FIELD_SIZE +1f;
		xOffset = ((float)(EditImage.bground.image.getWidth() + EditBox.bground.image.getWidth()+EditAnimation.bground.image.getWidth() ))/ Editor.FIELD_SIZE;
	}

	@Override
	public void repaint(GraphicsContext graphicsContext) {
		int xPos = (int) ((getPos().x + xOffset) * Editor.FIELD_SIZE);
		int yPos = (int) ((getPos().y + yOffset) * Editor.FIELD_SIZE);

		graphicsContext.drawImage(bground.image, xPos, yPos);
	}

	@Override
	public void update() {

	}

	@Override
	public void init() {

		addToRoot();

	}

	private void addToRoot() {
		root.getChildren().addAll();
	}

	@Override
	public void disableObjects(boolean disable) {
		// TODO Auto-generated method stub
		
	}

	

}
