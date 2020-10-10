package content.menu;

import java.util.List;

import content.LocatedImage;
import content.MainContent;
import content.misc.Other;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import main.Editor;
import math.NumberUtils;
import math.Vector2d;

public class EditImage extends MenuObject {

	public static final LocatedImage bground = Other.loadImage("/images/menu/IMG_MenuBox_EditImage.png");

	private float xOffset;
	private float yOffset;
	private List<Float> currentTimes;
	private TextField duration_input;
	private TextField imageX_input;
	private TextField imageY_input;
	private LocatedImage currentImage = null;

	private static final ImageView RESET_HOVERED = new ImageView(Other.BUTTONICON_RESET_HOVERED.image);
	private static final ImageView RESET_UNHOVERED = new ImageView(Other.BUTTONICON_RESET.image);

	private Button reset;

	public EditImage(Vector2d pos, Group root, MainContent contentManager) {
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

		this.currentImage = contentManager.getCurrentImage();
		this.currentTimes = contentManager.getCurrentTimes();

		if (currentImage != null) {
			this.imageX_input.setText("" + NumberUtils.round(currentImage.getOffsetPos().x, 2));
			this.imageY_input.setText("" + NumberUtils.round(currentImage.getOffsetPos().y, 2));
		}

		if (currentTimes != null) {
			try {
				contentManager.setCurrentDuration(currentTimes.get(contentManager.getCurrentIndex()));
				if (!("" + currentTimes.get(contentManager.getCurrentIndex())).equals(getDuration_input().getText().toString()))
					getDuration_input().setText("" + NumberUtils.round(currentTimes.get(contentManager.getCurrentIndex()),2));
			} catch (IndexOutOfBoundsException e) {

			}
		}
	}

	@Override
	public void init() {
		this.xOffset = 0f;
		this.yOffset = ((float) (contentManager.getScreen_height() - bground.image.getHeight() - 20)) / Editor.FIELD_SIZE + 1f;
		this.currentTimes = contentManager.getCurrentTimes();
		this.currentImage = contentManager.getCurrentImage();

		createElements();
		setupPositions();
		createFunctionalities();
		addToRoot();
	}

	private void setupPositions() {

		int xPos = (int) ((getPos().x + xOffset) * Editor.FIELD_SIZE) + 200;
		int yPos = (int) ((getPos().y + yOffset) * Editor.FIELD_SIZE) + 105;

		this.imageX_input.setLayoutX(xPos);
		this.imageX_input.setLayoutY(yPos);

		yPos += 60;

		this.imageY_input.setLayoutX(xPos);
		this.imageY_input.setLayoutY(yPos);

		yPos += 60;

		this.getDuration_input().setLayoutX(xPos);
		this.getDuration_input().setLayoutY(yPos);

		xPos = (int) ((getPos().x + xOffset) * Editor.FIELD_SIZE) + 200 + 50;
		yPos = (int) ((getPos().y + yOffset) * Editor.FIELD_SIZE) + 15;

		reset.setLayoutX(xPos);
		reset.setLayoutY(yPos);
	}

	private void createElements() {
		this.setDuration_input(new TextField());
		this.getDuration_input().setText("0");
		this.imageX_input = new TextField();
		this.imageX_input.setText("0");
		this.imageY_input = new TextField();
		this.imageY_input.setText("0");

		this.getDuration_input().setMaxWidth(75);
		this.imageX_input.setMaxWidth(75);
		this.imageY_input.setMaxWidth(75);

		reset = new Button("", RESET_UNHOVERED);
		reset.setBackground(Background.EMPTY);
		reset.setOnMouseEntered(e -> reset.setGraphic(RESET_HOVERED));
		reset.setOnMouseExited(e -> reset.setGraphic(RESET_UNHOVERED));

	}

	private void createFunctionalities() {

		reset.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (currentImage == null)
					return;
				imageX_input.setText("" + 0);
				imageY_input.setText("" + 0);
			}
		});

		this.getDuration_input().textProperty().addListener((observable, oldValue, newValue) -> {
			try {
				if (!termsFulfilledForDuration(newValue))
					return;
				contentManager.setCurrentDuration(Float.parseFloat(newValue));
				contentManager.console.println("Duration changed from " + oldValue + " to " + newValue);

			} catch (NumberFormatException e) {
				contentManager.console.println("Check your Input. Invalid Float Number");
			} catch (IllegalArgumentException e) {
				contentManager.console.println("Check your Input. Invalid Float Number");
			}
			if (!currentTimes.isEmpty()) {
				currentTimes.set(contentManager.getCurrentIndex(), ((float) contentManager.getCurrentDuration()));
			}
			contentManager.updateImportant();
		});

		this.imageX_input.textProperty().addListener((observable, oldValue, newValue) -> {

			try {
				if (!termsFulfilledForXPos(newValue))
					return;

				currentImage.setXPos(Float.parseFloat(newValue));
				contentManager.console.println("X-Pos changed from " + oldValue + " to " + newValue);

			} catch (IllegalArgumentException e) {
				contentManager.console.println("Check your Input. Invalid Float Number");
			} catch (NullPointerException e) {
				contentManager.console.println("No Image found");
			}

			contentManager.updateImportant();
		});

		this.imageY_input.textProperty().addListener((observable, oldValue, newValue) -> {

			try {
				if (!termsFulfilledForYPos(newValue))
					return;

				currentImage.setYPos(Float.parseFloat(newValue));
				contentManager.console.println("Y-Pos changed from " + oldValue + " to " + newValue);
			} catch (IllegalArgumentException e) {
				contentManager.console.println("Check your Input. Invalid Float Number");
			} catch (NullPointerException e) {
				contentManager.console.println("No Image found");
			}
			contentManager.updateImportant();
		});

	}

	private boolean termsFulfilledForDuration(String newValue) {

		boolean tooSmall = (Float.parseFloat(newValue) < Other.PERMITTED_MIN_VALUE_DURATION);
		if (tooSmall) {

			contentManager.setCurrentDuration(Other.PERMITTED_MIN_VALUE_DURATION);
			if (!currentTimes.isEmpty())
				currentTimes.set(contentManager.getCurrentIndex(), ((float) contentManager.getCurrentDuration()));
			return false;
		}

		boolean tooBig = (Float.parseFloat(newValue) > Other.PERMITTED_MAX_VALUE_DURATION);
		if (tooBig) {

			contentManager.setCurrentDuration(Other.PERMITTED_MAX_VALUE_DURATION);
			if (!currentTimes.isEmpty())
				currentTimes.set(contentManager.getCurrentIndex(), ((float) contentManager.getCurrentDuration()));
			return false;
		}

		return true;
	}

	private boolean termsFulfilledForXPos(String newValue) {

		boolean tooSmall = (Float.parseFloat(newValue) < Other.PERMITTED_MIN_VALUE_XPOS);
		if (tooSmall) {
			currentImage.setXPos(Other.PERMITTED_MIN_VALUE_XPOS);
			return false;
		}

		boolean tooBig = (Float.parseFloat(newValue) > Other.PERMITTED_MAX_VALUE_XPOS);
		if (tooBig) {
			currentImage.setXPos(Other.PERMITTED_MAX_VALUE_XPOS);
			return false;
		}
		return true;
	}

	private boolean termsFulfilledForYPos(String newValue) {

		boolean tooSmall = (Float.parseFloat(newValue) < Other.PERMITTED_MIN_VALUE_YPOS);
		if (tooSmall) {
			currentImage.setYPos(Other.PERMITTED_MIN_VALUE_YPOS);
			return false;
		}

		boolean tooBig = (Float.parseFloat(newValue) > Other.PERMITTED_MAX_VALUE_YPOS);
		if (tooBig) {
			currentImage.setYPos(Other.PERMITTED_MAX_VALUE_YPOS);
			return false;
		}
		return true;
	}

	private void addToRoot() {
		root.getChildren().addAll(getDuration_input(), imageX_input, imageY_input, reset);
	}

	public TextField getImageX_input() {
		return imageX_input;
	}

	public TextField getImageY_input() {
		return imageY_input;
	}

	public TextField getDuration_input() {
		return duration_input;
	}

	public void setDuration_input(TextField duration_input) {
		this.duration_input = duration_input;
	}

	@Override
	public void disableObjects(boolean disable) {
		this.getDuration_input().setDisable(disable);
		this.imageX_input.setDisable(disable);
		this.imageY_input.setDisable(disable);
		this.reset.setDisable(disable);
	}

}
