package content.menu;

import java.util.Random;

import content.MainContent;
import content.misc.Other;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import main.Editor;
import math.BoundingRectangle;
import math.Vector2d;

public class MyToolBox extends MenuObject {

	private static final Image bground = Other.loadImage("/images/menu/IMG_MenuBox_Tools.png").image;

	private static final ImageView NEWCOLBOX_HOVERED = new ImageView(Other.loadImage("/images/menu/IMG_MenuButton_New_Hovered.png").image);
	private static final ImageView NEWCOLBOX_UNHOVERED = new ImageView(Other.loadImage("/images/menu/IMG_MenuButton_New_Unhovered.png").image);

	private static final ImageView NEWHITBOX_HOVERED = new ImageView(Other.loadImage("/images/menu/IMG_MenuButton_NewR_Hovered.png").image);
	private static final ImageView NEWHITBOX_UNHOVERED = new ImageView(Other.loadImage("/images/menu/IMG_MenuButton_NewR_Unhovered.png").image);

	private static final ImageView COPYBOX_HOVERED = new ImageView(Other.loadImage("/images/menu/IMG_MenuButton_Copy_Hovered.png").image);
	private static final ImageView COPYBOX_UNHOVERED = new ImageView(Other.loadImage("/images/menu/IMG_MenuButton_Copy_Unhovered.png").image);

	private static final ImageView INSERTBOX_HOVERED = new ImageView(Other.loadImage("/images/menu/IMG_MenuButton_Insert_Hovered.png").image);
	private static final ImageView INSERTBOX_UNHOVERED = new ImageView(Other.loadImage("/images/menu/IMG_MenuButton_Insert_Unhovered.png").image);

	private static final ImageView DELETEBOX_HOVERED = new ImageView(Other.loadImage("/images/menu/IMG_MenuButton_Delete_Hovered.png").image);
	private static final ImageView DELETEBOX_UNHOVERED = new ImageView(Other.loadImage("/images/menu/IMG_MenuButton_Delete_Unhovered.png").image);

	private static final ImageView DELETEALLBOX_HOVERED = new ImageView(Other.loadImage("/images/menu/IMG_MenuButton_DeleteAll_Hovered.png").image);
	private static final ImageView DELETEALLBOX_UNHOVERED = new ImageView(Other.loadImage("/images/menu/IMG_MenuButton_DeleteAll_Unhovered.png").image);

	// Buttons
	private Button newColBox;
	private Button newHitBox;
	public  Button copyBox;
	public  Button insertBox;
	private Button deleteBox;
	private Button deleteAllBox;

	private float  xOffset = -0.2f;
	private float  yOffset = 0f;

	public MyToolBox(Vector2d pos, Group root, MainContent contentManager) {
		super(pos, root, contentManager);

	}

	@Override
	public void repaint(GraphicsContext graphicsContext) {
		int xPos = (int) ((getPos().x + xOffset) * Editor.FIELD_SIZE);
		int yPos = (int) ((getPos().y + yOffset) * Editor.FIELD_SIZE);

		graphicsContext.drawImage(bground, xPos, yPos);
	}

	@Override
	public void update() {

	}

	@Override
	public void init() {

		createLoadButtons();
		setPositions();
		addToRoot();

	}

	private void addToRoot() {
		root.getChildren().addAll(newColBox, newHitBox, copyBox, insertBox, deleteBox, deleteAllBox);
	}

	@Override
	public void disableObjects(boolean disable) {
		this.newColBox.setDisable(disable);
		this.newHitBox.setDisable(disable);
		this.copyBox.setDisable(disable);
		
		this.insertBox.setDisable(disable);
		this.deleteBox.setDisable(disable);
		this.deleteAllBox.setDisable(disable);
		
	}
	
	private void setPositions() {
		int xOffset2 = 10;
		int yOffset2 = 80;

		newColBox.setLayoutX((getPos().x + xOffset) * Editor.FIELD_SIZE + xOffset2);
		newColBox.setLayoutY((getPos().y + yOffset) * Editor.FIELD_SIZE + yOffset2);

		newHitBox.setLayoutX((getPos().x + xOffset) * Editor.FIELD_SIZE + xOffset2);
		newHitBox.setLayoutY((getPos().y + yOffset) * Editor.FIELD_SIZE + yOffset2 + 120 * 1);

		copyBox.setLayoutX((getPos().x + xOffset) * Editor.FIELD_SIZE + xOffset2);
		copyBox.setLayoutY((getPos().y + yOffset) * Editor.FIELD_SIZE + yOffset2 + 120 * 2);

		insertBox.setLayoutX((getPos().x + xOffset) * Editor.FIELD_SIZE + xOffset2);
		insertBox.setLayoutY((getPos().y + yOffset) * Editor.FIELD_SIZE + yOffset2 + 120 * 3);

		deleteBox.setLayoutX((getPos().x + xOffset) * Editor.FIELD_SIZE + xOffset2);
		deleteBox.setLayoutY((getPos().y + yOffset) * Editor.FIELD_SIZE + yOffset2 + 120 * 4);

		deleteAllBox.setLayoutX((getPos().x + xOffset) * Editor.FIELD_SIZE + xOffset2);
		deleteAllBox.setLayoutY((getPos().y + yOffset) * Editor.FIELD_SIZE + yOffset2 + 120 * 5);

	}

	private void createLoadButtons() {
		newColBox = new Button("", NEWCOLBOX_UNHOVERED);
		newColBox.setBackground(Background.EMPTY);
		newColBox.setOnMouseEntered(e -> newColBox.setGraphic(NEWCOLBOX_HOVERED));
		newColBox.setOnMouseExited(e -> newColBox.setGraphic(NEWCOLBOX_UNHOVERED));

		newHitBox = new Button("", NEWHITBOX_UNHOVERED);
		newHitBox.setBackground(Background.EMPTY);
		newHitBox.setOnMouseEntered(e -> newHitBox.setGraphic(NEWHITBOX_HOVERED));
		newHitBox.setOnMouseExited(e -> newHitBox.setGraphic(NEWHITBOX_UNHOVERED));

		copyBox = new Button("", COPYBOX_UNHOVERED);
		copyBox.setBackground(Background.EMPTY);
		copyBox.setOnMouseEntered(e -> copyBox.setGraphic(COPYBOX_HOVERED));
		copyBox.setOnMouseExited(e -> copyBox.setGraphic(COPYBOX_UNHOVERED));

		insertBox = new Button("", INSERTBOX_UNHOVERED);
		insertBox.setBackground(Background.EMPTY);
		insertBox.setOnMouseEntered(e -> insertBox.setGraphic(INSERTBOX_HOVERED));
		insertBox.setOnMouseExited(e -> insertBox.setGraphic(INSERTBOX_UNHOVERED));

		deleteBox = new Button("", DELETEBOX_UNHOVERED);
		deleteBox.setBackground(Background.EMPTY);
		deleteBox.setOnMouseEntered(e -> deleteBox.setGraphic(DELETEBOX_HOVERED));
		deleteBox.setOnMouseExited(e -> deleteBox.setGraphic(DELETEBOX_UNHOVERED));

		deleteAllBox = new Button("", DELETEALLBOX_UNHOVERED);
		deleteAllBox.setBackground(Background.EMPTY);
		deleteAllBox.setOnMouseEntered(e -> deleteAllBox.setGraphic(DELETEALLBOX_HOVERED));
		deleteAllBox.setOnMouseExited(e -> deleteAllBox.setGraphic(DELETEALLBOX_UNHOVERED));

		contentManager.console.println("Tools loaded succesfully");

		setUpFunctionalities();

	}

	private void setUpFunctionalities() {

		// button action
		newColBox.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Random random = new Random();
				BoundingRectangle rect = new BoundingRectangle(1,new Vector2d(MainContent.CENTER.x -1+random.nextFloat()*2,MainContent.CENTER.y-1+random.nextFloat()*2),1,contentManager.getSCALE_FACTOR());
				rect.setHurts(false);
				rect.setMainContent(contentManager);
				contentManager.addBox(rect);
				contentManager.updateImportant();
			}
		});
		
		newHitBox.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Random random = new Random();
				BoundingRectangle rect = new BoundingRectangle(1,new Vector2d(MainContent.CENTER.x -1+random.nextFloat()*2,MainContent.CENTER.y-1+random.nextFloat()*2),1,contentManager.getSCALE_FACTOR());
				rect.setHurts(true);
				rect.setMainContent(contentManager);
				contentManager.addBox(rect);
				contentManager.updateImportant();
	

			}
		});
		
		copyBox.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (contentManager.getCurrPointer() == null)
					return;

				contentManager.copiedPointer 	= contentManager.getCurrPointer();

			}
		});
		insertBox.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (contentManager.copiedPointer == null)
					return;
				copyBox(contentManager.copiedPointer,true);
				contentManager.updateImportant();
			}
		});


		deleteBox.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				contentManager.clear();
				contentManager.updateImportant();
			}
		});
		
		
		deleteAllBox.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				contentManager.clearAll();
				contentManager.updateImportant();
			}
		});
	}

	public void copyBox(BoundingRectangle copiedPointer, boolean randomly) {
		
		float randX = 0;
		float randY = 0;
		
		if(randomly){
			Random random					= new Random();
			randX = -1 + random.nextFloat()*2;
			randY = -1 + random.nextFloat()*2;					
		}		
		
		float x 						= copiedPointer.getPos().x;
		float y 						= copiedPointer.getPos().y;
		float width 					= copiedPointer.getWidth();
		float height 					= copiedPointer.getHeight();
		boolean hurts 					= copiedPointer.getHurts();
		float oldSclae					= copiedPointer.getOldScale();
		
		BoundingRectangle	newRect		= new BoundingRectangle(height, new Vector2d(x +randX, y +randY), width,1);
		newRect.setMainContent(contentManager);
		newRect.setHurts(hurts);
		newRect.setOldScale(oldSclae);
		contentManager.addBox(newRect);
	}

}
