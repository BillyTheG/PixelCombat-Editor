package content.menu;

import content.LocatedImage;
import content.MainContent;
import content.misc.Other;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import main.Editor;
import math.Vector2d;

public class MyBonus extends MenuObject {

	private static final LocatedImage bground = Other.loadImage("/images/menu/IMG_Editor_Bonus_Cover.png");

	private static final ImageView PREIMAGE_HOVERED = new ImageView(Other.BUTTONICON_PRE_IMAGE_HOVERED.image);
	private static final ImageView PREIMAGE_UNHOVERED = new ImageView(Other.BUTTONICON_PRE_IMAGE.image);

	private static final ImageView PRE_BOXES_HOVERED = new ImageView(Other.BUTTONICON_PRE_BOX_HOVERED.image);
	private static final ImageView PRE_BOXES_UNHOVERED = new ImageView(Other.BUTTONICON_PRE_BOX.image);

	private static final ImageView MAGIC_HOVERED = new ImageView(Other.BUTTONICON_MAGIC_HOVERED.image);
	private static final ImageView MAGIC_UNHOVERED = new ImageView(Other.BUTTONICON_MAGIC.image);

	private static final ImageView REF_HOVERED = new ImageView(Other.BUTTONICON_REF_RESET_HOVERED.image);
	private static final ImageView REF_UNHOVERED = new ImageView(Other.BUTTONICON_REF_RESET.image);
	// Buttons
	private Button preImages;
	private Button preBoxes;
	public  Button magic;
	public  Button ref;


	private float  xOffset;
	private float  yOffset;

	public MyBonus(Vector2d pos, Group root, MainContent contentManager) {
		super(pos, root, contentManager);

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

		yOffset = ((float) (contentManager.getScreen_height() -EditImage.bground.image.getHeight()- bground.image.getHeight() - 20)) / Editor.FIELD_SIZE + 1f;
		xOffset = (float) (((float) EditImage.bground.image.getWidth() + EditBox.bground.image.getWidth()/2) / Editor.FIELD_SIZE);

		
		createLoadButtons();
		setPositions();
		addToRoot();

	}

	private void addToRoot() {
		root.getChildren().addAll(preImages, preBoxes, magic,ref);
	}

	@Override
	public void disableObjects(boolean disable) {
		preImages.setDisable(disable);
		preBoxes.setDisable(disable);
		magic.setDisable(disable);
		ref.setDisable(disable);
	}


	
	private void setPositions() {
		int xOffset2 = 150;
		int yOffset2 = 5;

		preImages.setLayoutX((getPos().x + xOffset) * Editor.FIELD_SIZE + xOffset2);
		preImages.setLayoutY((getPos().y + yOffset) * Editor.FIELD_SIZE + yOffset2);

		preBoxes.setLayoutX((getPos().x + xOffset) * Editor.FIELD_SIZE + xOffset2 + Other.BUTTONICON_PRE_IMAGE.image.getWidth()+2);
		preBoxes.setLayoutY((getPos().y + yOffset) * Editor.FIELD_SIZE + yOffset2);

		magic.setLayoutX((getPos().x + xOffset) * Editor.FIELD_SIZE + xOffset2+ Other.BUTTONICON_PRE_IMAGE.image.getWidth()+ Other.BUTTONICON_PRE_BOX.image.getWidth()+4);
		magic.setLayoutY((getPos().y + yOffset) * Editor.FIELD_SIZE + yOffset2);
		
		ref.setLayoutX((getPos().x + xOffset) * Editor.FIELD_SIZE + xOffset2+ Other.BUTTONICON_PRE_IMAGE.image.getWidth()+ Other.BUTTONICON_PRE_BOX.image.getWidth() + Other.BUTTONICON_MAGIC.image.getWidth()+6);
		ref.setLayoutY((getPos().y + yOffset) * Editor.FIELD_SIZE + yOffset2);

	}

	private void createLoadButtons() {
		preImages = new Button("", PREIMAGE_UNHOVERED);
		preImages.setBackground(Background.EMPTY);
		preImages.setOnMouseEntered(e -> preImages.setGraphic(PREIMAGE_HOVERED));
		preImages.setOnMouseExited(e -> preImages.setGraphic(PREIMAGE_UNHOVERED));

		preBoxes = new Button("", PRE_BOXES_UNHOVERED);
		preBoxes.setBackground(Background.EMPTY);
		preBoxes.setOnMouseEntered(e -> preBoxes.setGraphic(PRE_BOXES_HOVERED));
		preBoxes.setOnMouseExited(e -> preBoxes.setGraphic(PRE_BOXES_UNHOVERED));

		magic = new Button("", MAGIC_UNHOVERED);
		magic.setBackground(Background.EMPTY);
		magic.setOnMouseEntered(e -> magic.setGraphic(MAGIC_HOVERED));
		magic.setOnMouseExited(e -> magic.setGraphic(MAGIC_UNHOVERED));
		
		ref = new Button("", REF_UNHOVERED);
		ref.setBackground(Background.EMPTY);
		ref.setOnMouseEntered(e -> ref.setGraphic(REF_HOVERED));
		ref.setOnMouseExited(e -> ref.setGraphic(REF_UNHOVERED));

		
		
		contentManager.console.println("Tools loaded succesfully");

		setUpFunctionalities();

	}

	private void setUpFunctionalities() {

		// button action
		preImages.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				contentManager.enablePreviousImage();
				contentManager.updateImportant();
			}
		});
		
		preBoxes.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				contentManager.copyPreviousBoxes();
				contentManager.updateImportant();
	

			}
		});
		
		magic.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				boolean oldVal = contentManager.isBlendActive();
				contentManager.setBlendActive(!oldVal);
				contentManager.repaint();
			}
		});
		
		ref.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				boolean oldVal = contentManager.isDrawRefImage();
				contentManager.setDrawRefImage(!oldVal);
				contentManager.repaint();
			}
		});
		
	}

	
}
