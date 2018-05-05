package content.menu;

import content.MainContent;
import content.misc.Other;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import main.Editor;
import math.BoundingRectangle;
import math.NumberUtils;
import math.Vector2d;

public class EditBox extends MenuObject {

	public  static final 	Image bground = Other.loadImage("/images/menu/IMG_MenuBox_EditBox.png");
	private static final 	ImageView RESET_HOVERED = new ImageView(Other.BUTTONICON_RESET_HOVERED);
	private static final 	ImageView RESET_UNHOVERED = new ImageView(Other.BUTTONICON_RESET);

	private Button reset;
	
	private TextField x_input;

	private TextField y_input;

	private TextField width_input;

	private TextField height_input;

	private ChoiceBox<String> hits;

	private BoundingRectangle currentBox = null;

	private float xOffset;
	private float yOffset;

	public EditBox(Vector2d pos, Group root, MainContent contentManager) {
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
		this.currentBox = contentManager.getCurrPointer();

		if (currentBox != null) {
			this.x_input.setText("" + NumberUtils.round(currentBox.getPos().x, 2));
			this.y_input.setText("" + NumberUtils.round(currentBox.getPos().y, 2));
			this.width_input.setText("" + NumberUtils.round(currentBox.getWidth(), 2));
			this.height_input.setText("" + NumberUtils.round(currentBox.getHeight(), 2));
			this.hits.setValue("" + currentBox.getHurts());
		}

	}

	@Override
	public void init() {
		this.currentBox = contentManager.getCurrPointer();

		yOffset = ((float) (contentManager.getScreen_height() - bground.getHeight() - 20)) / Editor.FIELD_SIZE + 1f;
		xOffset = ((float) EditImage.bground.getWidth()) / Editor.FIELD_SIZE;

		setUpElements();
		setUpPositions();
		setUpFunctionalities();
		addToRoot();

	}

	private void setUpElements() {

		this.x_input = new TextField();
		this.y_input = new TextField();
		this.width_input = new TextField();
		this.height_input = new TextField();
		this.hits = new ChoiceBox<String>();

		this.x_input.setText("0");
		this.y_input.setText("0");
		this.width_input.setText("0");
		this.height_input.setText("0");

		this.hits.getItems().add("true");
		this.hits.getItems().add("false");
		if (hits.getValue() == null)
			this.hits.setValue("false");

		this.x_input.setMaxWidth(75);
		this.y_input.setMaxWidth(75);
		this.width_input.setMaxWidth(75);
		this.height_input.setMaxWidth(75);
		this.hits.setMaxWidth(75);
		
		reset = new Button("", RESET_UNHOVERED);
		reset.setBackground(Background.EMPTY);
		reset.setOnMouseEntered(e -> reset.setGraphic(RESET_HOVERED));
		reset.setOnMouseExited(e -> reset.setGraphic(RESET_UNHOVERED));

	}

	private void setUpPositions() {

		int xPos = (int) ((getPos().x + xOffset) * Editor.FIELD_SIZE) + 200;
		int yPos = (int) ((getPos().y + yOffset) * Editor.FIELD_SIZE) + 105;

		this.x_input.setLayoutX(xPos);
		this.x_input.setLayoutY(yPos);

		yPos += 60;

		this.y_input.setLayoutX(xPos);
		this.y_input.setLayoutY(yPos);

		yPos += 60;

		this.hits.setLayoutX(xPos);
		this.hits.setLayoutY(yPos);

		yPos -= 120;
		xPos += 285;

		this.width_input.setLayoutX(xPos);
		this.width_input.setLayoutY(yPos);

		yPos += 60;

		this.height_input.setLayoutX(xPos);
		this.height_input.setLayoutY(yPos);
		
		xPos = (int) ((getPos().x + xOffset) * Editor.FIELD_SIZE) + 200 + 25;
		yPos = (int) ((getPos().y + yOffset) * Editor.FIELD_SIZE) + 15;

		reset.setLayoutX(xPos);
		reset.setLayoutY(yPos);

	}

	private void setUpFunctionalities() {

		reset.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (currentBox == null)
					return;
				x_input.setText("" + MainContent.CENTER.x);
				y_input.setText("" + MainContent.CENTER.y);
				width_input.setText("" + 1);
				height_input.setText("" + 1);
				contentManager.setCurrPointer(null);
				contentManager.updateImportant();
			}
		});
		
		this.x_input.textProperty().addListener((observable, oldValue, newValue) -> {

			if (currentBox == null || oldValue.equals(newValue))
				return;

			try {
				currentBox.edit(currentBox.getHeight(), new Vector2d(Float.parseFloat(x_input.getText()), currentBox.getPos().y), currentBox.getWidth());
				contentManager.updateImportant();
			} catch (NumberFormatException e) {

			} catch (NullPointerException e) {

			}

		});
		this.y_input.textProperty().addListener((observable, oldValue, newValue) -> {
			if (currentBox == null || oldValue.equals(newValue))
				return;
			try {
				currentBox.edit(currentBox.getHeight(), new Vector2d(currentBox.getPos().x, Float.parseFloat(y_input.getText())), currentBox.getWidth());
				contentManager.updateImportant();
			} catch (NumberFormatException e) {

			} catch (NullPointerException e) {

			}

		});
		this.width_input.textProperty().addListener((observable, oldValue, newValue) -> {
			if (currentBox == null || oldValue.equals(newValue))
				return;
			try {
				currentBox.edit(currentBox.getHeight(), new Vector2d(currentBox.getPos().x, currentBox.getPos().y), Float.parseFloat(width_input.getText()));
				contentManager.updateImportant();
			} catch (NumberFormatException e) {

			} catch (NullPointerException e) {

			}
		});
		this.height_input.textProperty().addListener((observable, oldValue, newValue) -> {
			if (currentBox == null || oldValue.equals(newValue))
				return;
			try {
				currentBox.edit(Float.parseFloat(height_input.getText()), new Vector2d(currentBox.getPos().x, currentBox.getPos().y), currentBox.getWidth());
				contentManager.updateImportant();
			} catch (NumberFormatException e) {

			} catch (NullPointerException e) {

			}
		});

		this.hits.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> ov, String oldValue, String newValue) {
				if (currentBox == null || oldValue.equals(newValue))
					return;
				currentBox.setHurts(getBooleanVal(newValue));
				contentManager.updateImportant();

			}
		});

	}

	private void addToRoot() {
		root.getChildren().addAll(x_input, y_input, width_input, height_input, hits,reset);
	}

	private boolean getBooleanVal(String value) {
		if (value.equals("true"))
			return true;
		else
			return false;
	}

	@Override
	public void disableObjects(boolean disable) {
		this.x_input.setDisable(disable);
		this.y_input.setDisable(disable);
		this.width_input.setDisable(disable);
		this.height_input.setDisable(disable);
		this.hits.setDisable(disable);
		this.reset.setDisable(disable);
		
	}

}
