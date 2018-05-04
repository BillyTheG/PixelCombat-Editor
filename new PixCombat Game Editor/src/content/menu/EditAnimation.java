package content.menu;

import java.util.Collections;

import content.LocatedImage;
import content.MainContent;
import content.misc.Other;
import exceptions.AnimatorNoContentException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import main.Editor;
import math.Vector2d;

public class EditAnimation extends MenuObject {

	public  static final Image bground = Other.loadImage("/images/menu/IMG_MenuBox_EditAnimation.png");

	private ChoiceBox<String> loopBool_input;
	private ChoiceBox<String> loopIndex_input;
	private ChoiceBox<String> selected_sprites_input;
	
	private static final ImageView PLAY_HOVERED 	= new ImageView(Other.BUTTONICON_PLAY_HOVERED);
	private static final ImageView PLAY_UNHOVERED 	= new ImageView(Other.BUTTONICON_PLAY);

	private static final ImageView STOP_HOVERED = new ImageView(Other.BUTTONICON_STOP_HOVERED);
	private static final ImageView STOP_UNHOVERED = new ImageView(Other.BUTTONICON_STOP);

	private Button playAnimation;
	private Button stopAnimation;

	
	private String currentLoopBool = "";
	private String currentLoopIndex = "";

	
	
	private float  xOffset;
	private float  yOffset;

	public EditAnimation(Vector2d pos, Group root, MainContent contentManager) {
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

		if (contentManager.getLoopBools() != null && contentManager.getCurrentSeq() != null && contentManager.getLoopBools().get(contentManager.getCurrentSeq()) != null) {
			getLoopBool_input().setValue(contentManager.getLoopBools().get(contentManager.getCurrentSeq()).toString());
			currentLoopBool = "" + contentManager.getLoopBools().get(contentManager.getCurrentSeq());
		}
		if (contentManager.getLoopIndices() != null && contentManager.getCurrentSeq() != null && contentManager.getLoopIndices().get(contentManager.getCurrentSeq()) != null) {
			getLoopIndex_input().setValue(contentManager.getLoopIndices().get(contentManager.getCurrentSeq()).toString());
			currentLoopIndex = "" + contentManager.getLoopIndices().get(contentManager.getCurrentSeq());
		}
		
	}

	@Override
	public void init() {
		yOffset = ((float)(contentManager.getScreen_height() - bground.getHeight()+1 -20))/ Editor.FIELD_SIZE +1f;
		xOffset = ((float)(EditImage.bground.getWidth() + EditBox.bground.getWidth() ))/ Editor.FIELD_SIZE;
		
		createButtons();
		createChoiceBoxes();
		setPositions();
		setFunctionalities();
		addToRoot();

	}

	@Override
	public void disableObjects(boolean disable) {
		
		for(LocatedImage img : contentManager.getCurrentImages())
			img.unmark(getRoot());
		
		this.loopIndex_input.setDisable(disable);
		this.loopBool_input.setDisable(disable);
		this.selected_sprites_input.setDisable(disable);
	}

	
	private void createButtons() {
		playAnimation = new Button("", PLAY_UNHOVERED);
		playAnimation.setBackground(Background.EMPTY);
		playAnimation.setOnMouseEntered(e -> playAnimation.setGraphic(PLAY_HOVERED));
		playAnimation.setOnMouseExited(e -> playAnimation.setGraphic(PLAY_UNHOVERED));

		stopAnimation = new Button("", STOP_UNHOVERED);
		stopAnimation.setBackground(Background.EMPTY);
		stopAnimation.setOnMouseEntered(e -> stopAnimation.setGraphic(STOP_HOVERED));
		stopAnimation.setOnMouseExited(e -> stopAnimation.setGraphic(STOP_UNHOVERED));
		stopAnimation.setDisable(true);
		
	}

	private void createChoiceBoxes() {
		
		loopBool_input 			= new ChoiceBox<String>();
		loopIndex_input 		= new ChoiceBox<String>();
		selected_sprites_input	= new ChoiceBox<String>();
		
		
		this.getLoopBool_input().getItems().add("true");
		this.getLoopBool_input().getItems().add("false");
		if (getLoopBool_input().getValue() == null)
			this.getLoopBool_input().setValue("true");

		this.getLoopIndex_input().getItems().add("-1");
		if (getLoopIndex_input().getValue() == null)
			this.getLoopIndex_input().setValue("-1");

		this.loopBool_input.setMaxWidth(75);
		this.loopIndex_input.setMaxWidth(75);
		this.selected_sprites_input.setMaxWidth(200);
	}
	
	private void setPositions() {
		int xOffset2 = 150;
		int yOffset2 = 105;

		loopBool_input.setLayoutX((getPos().x + xOffset) * Editor.FIELD_SIZE + xOffset2);
		loopBool_input.setLayoutY((getPos().y + yOffset) * Editor.FIELD_SIZE + yOffset2);

		xOffset2 += 325;
		
		loopIndex_input.setLayoutX((getPos().x + xOffset) * Editor.FIELD_SIZE + xOffset2);
		loopIndex_input.setLayoutY((getPos().y + yOffset) * Editor.FIELD_SIZE + yOffset2);

		xOffset2 -= 325;		
		yOffset2 += 120;
		
		selected_sprites_input.setLayoutX((getPos().x + xOffset) * Editor.FIELD_SIZE + xOffset2);
		selected_sprites_input.setLayoutY((getPos().y + yOffset) * Editor.FIELD_SIZE + yOffset2);
		
		xOffset2 = 342;		
		yOffset2 = 22;
		
		playAnimation.setLayoutX((getPos().x + xOffset) * Editor.FIELD_SIZE + xOffset2);
		playAnimation.setLayoutY((getPos().y + yOffset) * Editor.FIELD_SIZE + yOffset2);
		
		xOffset2 = (int) (342 + 5 + Other.BUTTONICON_PLAY.getWidth());		
		yOffset2 = 22;
		
		stopAnimation.setLayoutX((getPos().x + xOffset) * Editor.FIELD_SIZE + xOffset2);
		stopAnimation.setLayoutY((getPos().y + yOffset) * Editor.FIELD_SIZE + yOffset2);
		
		
	}

	
	private void setFunctionalities() {

		this.getSelected_sprites_input().valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> ov, String t, String t1) {
				
				if(contentManager.getCurrentImages() == null || contentManager.getImages() == null || contentManager.getImages().get(t1) == null)
					return;
				
				contentManager.console.println();
				contentManager.console.println(t1 + " selected.");
				contentManager.setCurrentSeq(t1);
				contentManager.setCurrentImages(Collections.synchronizedList(contentManager.getImages().get(t1)));
				contentManager.setCurrentTimes(Collections.synchronizedList(contentManager.getTimes().get(t1)));
				if (contentManager.getBoxes().get(contentManager.getCurrentSeq()) != null) {
					contentManager.setCurrentBox(contentManager.getBoxes().get(contentManager.getCurrentSeq()));

				}
				if (contentManager.getCurrentBox() == null)
					contentManager.console.println("Boxes couldn't be loaded correctly.");

				contentManager.updateImages();
				if (contentManager.getBoxes().get(contentManager.getCurrentSeq()) != null)
					updateIndexBoxes();
				update();
				contentManager.repaint();
			}
		});


		this.getLoopBool_input().valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> ov, String t, String t1) {
				contentManager.console.println();
				contentManager.console.println(t1 + " selected.");
				// select
				currentLoopBool = t1;
				// save
				contentManager.getLoopBools().put(contentManager.getCurrentSeq(), contentManager.getVal(currentLoopBool));
				contentManager.updateImportant();
			}
		});


		this.getLoopIndex_input().valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> ov, String t, String t1) {
				contentManager.console.println();
				contentManager.console.println(t1 + " selected.");
				// select
				currentLoopIndex = t1;
				if (currentLoopIndex == null)
					return;
				// save
				contentManager.getLoopIndices().put(contentManager.getCurrentSeq(), Integer.parseInt(currentLoopIndex));
				contentManager.updateImportant();;
			}
		});
		
		// button action
				playAnimation.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						
						try {
							contentManager.disableObjects(true);
							contentManager.getAnimator().setup(contentManager.getCurrentImages(), contentManager.getCurrentTimes());
							contentManager.getAnimator().setRunning(true);
							contentManager.setAnimatorExecuter(new Thread(contentManager.getAnimator()));
							contentManager.getAnimatorExecuter().setDaemon(true);
							contentManager.getAnimatorExecuter().start();
							playAnimation.setDisable(true);
							stopAnimation.setDisable(false);
						
						} catch (AnimatorNoContentException | IndexOutOfBoundsException e) {
							contentManager.console.println(""+e.getMessage());
						}
						
					}
				});
		
				
				stopAnimation.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						playAnimation.setDisable(false);
						
						
						try {
							contentManager.getAnimator().setRunning(false);
							contentManager.getAnimatorExecuter().interrupt();
							contentManager.getAnimatorExecuter().join();
						} catch (InterruptedException e) {
							
						}
						stopAnimation.setDisable(true);
						contentManager.disableObjects(false);						
						contentManager.updateImportant();
					}
				});
	}
	
	
	
	private void addToRoot() {
		root.getChildren().addAll(getLoopBool_input(),getLoopIndex_input(),getSelected_sprites_input(),playAnimation,stopAnimation);
	}
	
	
	public void updateIndexBoxes() {
		loopIndex_input.getItems().clear();
		for (int i = 0; i < contentManager.getCurrentImages().size(); i++) {
			loopIndex_input.getItems().add("" + i);
		}
		loopIndex_input.setValue(currentLoopIndex);
	}
	

	
	
	
	public float getxOffset() {
		return xOffset;
	}

	public float getyOffset() {
		return yOffset;
	}

	/**
	 * @return the loopIndex_input
	 */
	public ChoiceBox<String> getLoopIndex_input() {
		return loopIndex_input;
	}

	/**
	 * @param loopIndex_input the loopIndex_input to set
	 */
	public void setLoopIndex_input(ChoiceBox<String> loopIndex_input) {
		this.loopIndex_input = loopIndex_input;
	}

	/**
	 * @return the loopBool_input
	 */
	public ChoiceBox<String> getLoopBool_input() {
		return loopBool_input;
	}

	/**
	 * @param loopBool_input the loopBool_input to set
	 */
	public void setLoopBool_input(ChoiceBox<String> loopBool_input) {
		this.loopBool_input = loopBool_input;
	}

	/**
	 * @return the selected_sprites_input
	 */
	public ChoiceBox<String> getSelected_sprites_input() {
		return selected_sprites_input;
	}

	/**
	 * @param selected_sprites_input the selected_sprites_input to set
	 */
	public void setSelected_sprites_input(ChoiceBox<String> selected_sprites_input) {
		this.selected_sprites_input = selected_sprites_input;
	}

	
	

}
