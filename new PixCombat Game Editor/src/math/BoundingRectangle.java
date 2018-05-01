package math;

import content.MainContent;
import content.misc.IToolObject;
import content.misc.Other;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import main.Editor;

/**
 * 
 * A simple rectangular bounding box for the PixelCombat Editor. It holds more options to be parameterized.
 *
 * 
 * @author BillyG
 * @version 1.1
 */
public class BoundingRectangle implements BoundingBoxInterface, IToolObject {

	private Vector2d upperLeft;
	private Vector2d lowerRight;

	private float width;
	private float height;
	private boolean marked;
	private Vector2d pos;
	private boolean hurts = false;

	private Button scaleUpperLeft;
	private Button scaleUpperRight;
	private Button scaleDownLeft;
	private Button scaleDownRight;

	private Button scaleLeft;
	private Button scaleRight;
	private Button scaleUp;
	private Button scaleDown;
	private MainContent mainContent;

	private float oldX = 0f;
	private float oldY = 0f;
	public float currX;
	public float currY;
	public float lastX;;
	public float lastY;;
	private Vector2d lastPosition = new Vector2d();
	private int DIAGONAL_DELTA = 25;

	/**
	 * Constructor of BoundingRectangle
	 * 
	 * @param upperLeft
	 *            position of upper left corner
	 * @param lowerRight
	 *            position of lower right corner
	 */
	public BoundingRectangle(Vector2d upperLeft, Vector2d lowerRight) {
		this.upperLeft = upperLeft;
		this.lowerRight = lowerRight;

		this.pos = new Vector2d((upperLeft.x - lowerRight.x) / 2f, (upperLeft.y - lowerRight.y) / 2f);

		currX = pos.x * Editor.FIELD_SIZE;
		currY = pos.y * Editor.FIELD_SIZE;
		lastX = pos.x * Editor.FIELD_SIZE;
		;
		lastY = pos.y * Editor.FIELD_SIZE;
		;

		lastPosition.copy(pos);

		height = (upperLeft.y - lowerRight.y) * Editor.FIELD_SIZE;
		width = (upperLeft.x - lowerRight.x) * Editor.FIELD_SIZE;

		initScaleButtons();

	}

	/**
	 * Constructor of BoundingRectangle
	 * 
	 * @param pos,
	 *            the middle point of the bottom line
	 * @param height,
	 *            height of the Rect
	 * @param width,
	 *            width of the Rect
	 */
	public BoundingRectangle(Vector2d pos, float height, float width) {
		this.upperLeft = new Vector2d(pos.x - width / 2f, pos.y - height);
		this.lowerRight = new Vector2d(pos.x + width / 2f, pos.y);
		this.pos = new Vector2d((upperLeft.x - lowerRight.x) / 2f, (upperLeft.y - lowerRight.y) / 2f);

		currX = pos.x * Editor.FIELD_SIZE;
		currY = pos.y * Editor.FIELD_SIZE;
		lastX = pos.x * Editor.FIELD_SIZE;
		;
		lastY = pos.y * Editor.FIELD_SIZE;
		;

		lastPosition.copy(pos);

		this.height = height * Editor.FIELD_SIZE;
		this.width = width * Editor.FIELD_SIZE;

		initScaleButtons();
	}

	/**
	 * Constructor of BoundingRectangle
	 * 
	 * @param height,
	 *            height of the Rect
	 * @param pos,
	 *            the middle point of the whole Rectangle Volume
	 * @param width,
	 *            width of the Rect
	 */
	public BoundingRectangle(float height, Vector2d pos, float width) {
		this.upperLeft = new Vector2d(pos.x - width / 2f, pos.y - height / 2f);
		this.lowerRight = new Vector2d(pos.x + width / 2f, pos.y + height / 2f);
		this.pos = pos;

		currX = pos.x * Editor.FIELD_SIZE;
		currY = pos.y * Editor.FIELD_SIZE;
		lastX = pos.x * Editor.FIELD_SIZE;
		;
		lastY = pos.y * Editor.FIELD_SIZE;
		;

		lastPosition.copy(pos);

		this.height = height * Editor.FIELD_SIZE;
		this.width = width * Editor.FIELD_SIZE;

		initScaleButtons();
	}

	public float getHeight() {
		return (lowerRight.y - upperLeft.y);

	}

	public boolean clickedOnArea(float x, float y) {
		float x1 = upperLeft.x * Editor.FIELD_SIZE;
		float y1 = upperLeft.y * Editor.FIELD_SIZE;
		float x2 = lowerRight.x * Editor.FIELD_SIZE;
		float y2 = lowerRight.y * Editor.FIELD_SIZE;

		return (x1 - 5f <= x) && (x <= x2 + 5f) && (y1 - 5f <= y) && (y <= y2 + 5f);

	}
	
	public boolean clickedOnBorder(float x, float y) {
		float x1 = upperLeft.x * Editor.FIELD_SIZE;
		float y1 = upperLeft.y * Editor.FIELD_SIZE;
		float x2 = lowerRight.x * Editor.FIELD_SIZE;
		float y2 = lowerRight.y * Editor.FIELD_SIZE;

		 return (x1 - 5f <= x && x <= x1 + 5f) && (y1 - 5f <= y && y <= y2 + 5f) || (x2 - 5f <= x && x <= x2 + 5f) && (y1 - 5f <= y && y <= y2 + 5f)
		 || (x1 - 5f <= x && x <= x2 + 5f) && (y1 - 5f <= y && y <= y1 + 5f) || (x1 - 5f <= x && x <= x2 + 5f) && (y2 - 5f <= y && y <= y2 + 5f);
	}

	
	

	public Vector2d getUpperLeft() {
		return upperLeft;
	}

	public void setUpperLeft(Vector2d upperLeft) {
		this.upperLeft = upperLeft;
	}

	public float getWidth() {
		return (lowerRight.x - upperLeft.x);

	}

	public Vector2d getLowerRight() {
		return lowerRight;
	}

	public void setLowerRight(Vector2d lowerRight) {
		this.lowerRight = lowerRight;
	}

	/**
	 * Returns whether or not the bounding box collides with the vector
	 * 
	 * @param v
	 *            vector
	 * @return does collide
	 */

	public boolean isCollision(Vector2d v) {
		if (upperLeft.x <= v.x && upperLeft.y <= v.y && lowerRight.x >= v.x && lowerRight.y >= v.y) {
			return true;
		}
		return false;
	}

	@Override
	public void draw(GraphicsContext g) {
		normalize();
		updateCorner();
		float x1 = upperLeft.x * Editor.FIELD_SIZE;
		float y1 = upperLeft.y * Editor.FIELD_SIZE;
		float x2 = lowerRight.x * Editor.FIELD_SIZE;
		float y2 = lowerRight.y * Editor.FIELD_SIZE;
		g.setLineWidth(5);
		if (!isMarked()) {
			if (hurts)
				g.setStroke(Color.RED);
			else
				g.setStroke(Color.YELLOWGREEN);
		} else {

			g.setStroke(Color.YELLOW);
			g.setLineWidth(2);
			// diagonal only if marked

			int n = (int) ((lowerRight.y - upperLeft.y) * Editor.FIELD_SIZE / DIAGONAL_DELTA);
			float relation = (float) height / (float) width;

			// //show tangents of relation
			// for(int i = 0; i< n; i++)
			// g.strokeLine(x1 +i*DIAGONAL_DELTA, y2 , x2 , y2- i*(DIAGONAL_DELTA/relation));

			if (width < DIAGONAL_DELTA || height < DIAGONAL_DELTA) {
				g.strokeLine(x1, y2, x2, y1);
			} else {
				// upper Half
				for (int i = 0; i < n; i++)
					g.strokeLine(x1, y2 - i * DIAGONAL_DELTA, x2 - i * (DIAGONAL_DELTA / relation), y1);
				// lower Half
				for (int i = 0; i < n; i++)
					g.strokeLine(x1 + i * (DIAGONAL_DELTA / relation), y2, x2, y1 + i * (DIAGONAL_DELTA));

			}
		}
		// horizontal
		g.strokeLine(x1, y1, x1 + width, y1);
		g.strokeLine(x1, y2, x1 + width, y2);
		// vertical
		g.strokeLine(x1, y1, x1, y2);
		g.strokeLine(x2, y1, x2, y2);

		// corner
		// drawCorePoints(g, x1, y1, x2, y2);

	}

	public void drawCorePoints(GraphicsContext g, float x1, float y1, float x2, float y2) {
		g.setFill(Color.PURPLE);
		g.fillRect(x1 - 5, y1 - 5, 10, 10);
		g.fillRect(x1 - 5, y2 - 5, 10, 10);
		g.fillRect(x2 - 5, y2 - 5, 10, 10);
		g.fillRect(x2 - 5, y1 - 5, 10, 10);

		g.fillRect((x1 + x2) / 2 - 5, y1 - 5, 10, 10);
		g.fillRect((x1 + x2) / 2 - 5, y2 - 5, 10, 10);
		g.fillRect(x1 - 5, (y1 + y2) / 2 - 5, 10, 10);
		g.fillRect(x2 - 5, (y1 + y2) / 2 - 5, 10, 10);
	}

	@Override
	public boolean isMarked() {
		return marked;
	}

	public void mark(Group root) {
		oldX = pos.x;
		marked = true;
		root.getChildren().addAll(scaleUpperLeft, scaleDownLeft, scaleUpperRight, scaleDownRight, scaleDown, scaleUp, scaleLeft, scaleRight);

	}

	public void unmark(Group root) {
		marked = false;
		root.getChildren().removeAll(scaleUpperLeft, scaleDownLeft, scaleUpperRight, scaleDownRight, scaleDown, scaleUp, scaleLeft, scaleRight);

	}

	public Vector2d distancePoint(Vector2d point) {
		Vector2d min1 = GeometryUtils.PointLineDist2(point, upperLeft, new Vector2d(upperLeft.x + width / 50f, upperLeft.y));
		Vector2d min2 = GeometryUtils.PointLineDist2(point, upperLeft, new Vector2d(upperLeft.x, upperLeft.y + height / 50f));
		Vector2d min3 = GeometryUtils.PointLineDist2(point, new Vector2d(lowerRight.x - width / 50f, lowerRight.y), lowerRight);
		Vector2d min4 = GeometryUtils.PointLineDist2(point, new Vector2d(lowerRight.x, lowerRight.y - height / 50f), lowerRight);
		float minimum = Math.min(Math.min(min1.length(), min2.length()), Math.min(min3.length(), min4.length()));

		if (minimum == min1.length())
			return min1;
		if (minimum == min2.length())
			return min2;
		if (minimum == min3.length())
			return min3;
		if (minimum == min4.length())
			return min4;

		return null;

	}

	public void update(Vector2d dir) {

		pos = pos.add(dir);

		updateCorner();

	}

	@Override
	public void updateCorner() {
		upperLeft.x = pos.x - width / 100f;
		upperLeft.y = pos.y - height / 100f;
		lowerRight.x = pos.x + width / 100f;
		lowerRight.y = pos.y + height / 100f;

		setUpPositions();
	}

	public Vector2d getPos() {
		return pos;
	}

	public void setPos(Vector2d pos) {
		this.pos = pos;
		updateCorner();
	}

	public void edit(float height, Vector2d point, float width) {
		this.pos.x = point.x;
		this.pos.y = point.y;

		this.upperLeft = new Vector2d(pos.x - width / 2f, pos.y - height / 2f);
		this.lowerRight = new Vector2d(pos.x + width / 2f, pos.y + height / 2f);

		this.height = height * Editor.FIELD_SIZE;
		this.width = width * Editor.FIELD_SIZE;

		resetAnchorPoints(pos);

	}

	public void scaleHorizontal(Vector2d point) {
		float x = point.x;

		if (oldX == x)
			return;

		if (x < lowerRight.x && x > upperLeft.x && oldX - x > 0)
			lowerRight.x = x;
		else if (x < lowerRight.x && x > upperLeft.x && oldX - x < 0)
			upperLeft.x = x;
		else if (x < upperLeft.x && x < lowerRight.x)
			upperLeft.x = x;
		else if (x > lowerRight.x && x > upperLeft.x)
			lowerRight.x = x;

		oldX = x;

		normalize();

	}

	public void scaleVertical(Vector2d point) {
		float y = point.y;

		if (oldY == y)
			return;

		if (y < lowerRight.y && y > upperLeft.y && oldY - y > 0)
			lowerRight.y = y;
		else if (y < lowerRight.y && y > upperLeft.y && oldY - y < 0)
			upperLeft.y = y;
		else if (y < upperLeft.y && y < lowerRight.y)
			upperLeft.y = y;
		else if (y > lowerRight.y && y > upperLeft.y)
			lowerRight.y = y;

		oldY = y;

		normalize();

	}

	public void scaleDiagonal(Vector2d point) {
		scaleVertical(point);
		scaleHorizontal(point);
		normalize();

	}

	public void normalize() {
		this.width = Math.abs(this.upperLeft.x - this.lowerRight.x) * Editor.FIELD_SIZE;
		this.height = Math.abs(this.upperLeft.y - this.lowerRight.y) * Editor.FIELD_SIZE;

		this.pos.x = (this.upperLeft.x + this.lowerRight.x) / 2;
		this.pos.y = (this.upperLeft.y + this.lowerRight.y) / 2;
		resetAnchorPoints(pos);
	}

	private void initScaleButtons() {
		createButtons();
		setUpPositions();
		setUpFunctionalities();
	}

	private void createButtons() {

		// Creation
		this.scaleUpperLeft = new Button("", new ImageView(Other.BUTTONICON_SCALE_UPLEFT));
		this.scaleUpperRight = new Button("", new ImageView(Other.BUTTONICON_SCALE_UPRIGHT));
		this.scaleDownLeft = new Button("", new ImageView(Other.BUTTONICON_SCALE_DOWNLEFT));
		this.scaleDownRight = new Button("", new ImageView(Other.BUTTONICON_SCALE_DOWNRIGHT));

		this.scaleLeft = new Button("", new ImageView(Other.BUTTONICON_SCALE_LEFT));
		this.scaleRight = new Button("", new ImageView(Other.BUTTONICON_SCALE_RIGHT));
		this.scaleDown = new Button("", new ImageView(Other.BUTTONICON_SCALE_DOWN));
		this.scaleUp = new Button("", new ImageView(Other.BUTTONICON_SCALE_UP));

		// Setting Size
		this.scaleUpperLeft.setMaxSize(30, 30);
		this.scaleUpperRight.setMaxSize(30, 30);
		this.scaleDownLeft.setMaxSize(30, 30);
		this.scaleDownRight.setMaxSize(30, 30);

		this.scaleLeft.setMaxSize(30, 30);
		this.scaleRight.setMaxSize(30, 30);
		this.scaleDown.setMaxSize(30, 30);
		this.scaleUp.setMaxSize(30, 30);

		this.scaleUpperLeft.setMinSize(30, 30);
		this.scaleUpperRight.setMinSize(30, 30);
		this.scaleDownLeft.setMinSize(30, 30);
		this.scaleDownRight.setMinSize(30, 30);

		this.scaleLeft.setMinSize(30, 30);
		this.scaleRight.setMinSize(30, 30);
		this.scaleDown.setMinSize(30, 30);
		this.scaleUp.setMinSize(30, 30);

		// Removing Background
		this.scaleUpperLeft.setBackground(Background.EMPTY);
		this.scaleUpperRight.setBackground(Background.EMPTY);
		this.scaleDownLeft.setBackground(Background.EMPTY);
		this.scaleDownRight.setBackground(Background.EMPTY);

		this.scaleLeft.setBackground(Background.EMPTY);
		this.scaleRight.setBackground(Background.EMPTY);
		this.scaleDown.setBackground(Background.EMPTY);
		this.scaleUp.setBackground(Background.EMPTY);

		// HoveredProperty
		this.scaleUpperLeft.setOnMouseEntered(e -> this.scaleUpperLeft.setGraphic(new ImageView(Other.BUTTONICON_SCALE_UPLEFT_HOVERED)));
		this.scaleUpperLeft.setOnMouseExited(e -> this.scaleUpperLeft.setGraphic(new ImageView(Other.BUTTONICON_SCALE_UPLEFT)));

		this.scaleUpperRight.setOnMouseEntered(e -> this.scaleUpperRight.setGraphic(new ImageView(Other.BUTTONICON_SCALE_UPRIGHT_HOVERED)));
		this.scaleUpperRight.setOnMouseExited(e -> this.scaleUpperRight.setGraphic(new ImageView(Other.BUTTONICON_SCALE_UPRIGHT)));

		this.scaleDownLeft.setOnMouseEntered(e -> this.scaleDownLeft.setGraphic(new ImageView(Other.BUTTONICON_SCALE_DOWNLEFT_HOVERED)));
		this.scaleDownLeft.setOnMouseExited(e -> this.scaleDownLeft.setGraphic(new ImageView(Other.BUTTONICON_SCALE_DOWNLEFT)));

		this.scaleDownRight.setOnMouseEntered(e -> this.scaleDownRight.setGraphic(new ImageView(Other.BUTTONICON_SCALE_DOWNRIGHT_HOVERED)));
		this.scaleDownRight.setOnMouseExited(e -> this.scaleDownRight.setGraphic(new ImageView(Other.BUTTONICON_SCALE_DOWNRIGHT)));

		this.scaleLeft.setOnMouseEntered(e -> this.scaleLeft.setGraphic(new ImageView(Other.BUTTONICON_SCALE_LEFT_HOVERED)));
		this.scaleLeft.setOnMouseExited(e -> this.scaleLeft.setGraphic(new ImageView(Other.BUTTONICON_SCALE_LEFT)));

		this.scaleRight.setOnMouseEntered(e -> this.scaleRight.setGraphic(new ImageView(Other.BUTTONICON_SCALE_RIGHT_HOVERED)));
		this.scaleRight.setOnMouseExited(e -> this.scaleRight.setGraphic(new ImageView(Other.BUTTONICON_SCALE_RIGHT)));

		this.scaleUp.setOnMouseEntered(e -> this.scaleUp.setGraphic(new ImageView(Other.BUTTONICON_SCALE_UP_HOVERED)));
		this.scaleUp.setOnMouseExited(e -> this.scaleUp.setGraphic(new ImageView(Other.BUTTONICON_SCALE_UP)));

		this.scaleDown.setOnMouseEntered(e -> this.scaleDown.setGraphic(new ImageView(Other.BUTTONICON_SCALE_DOWN_HOVERED)));
		this.scaleDown.setOnMouseExited(e -> this.scaleDown.setGraphic(new ImageView(Other.BUTTONICON_SCALE_DOWN)));

	}

	private void setUpPositions() {

		// Left Side

		int distanceButtonBorder = 6;
		int offSetX = (int) scaleUp.getMaxWidth() + distanceButtonBorder;
		int offSetY = (int) scaleUp.getMaxHeight() + distanceButtonBorder;

		int xPos = (int) ((upperLeft.x) * Editor.FIELD_SIZE - offSetX);
		int yPos = (int) ((upperLeft.y) * Editor.FIELD_SIZE - offSetY);

		scaleUpperLeft.setLayoutX(xPos);
		scaleUpperLeft.setLayoutY(yPos);

		xPos = (int) ((upperLeft.x) * Editor.FIELD_SIZE - offSetX);
		yPos = (int) (((lowerRight.y + upperLeft.y) / 2) * Editor.FIELD_SIZE - (scaleUp.getMaxHeight() / 2));

		scaleLeft.setLayoutX(xPos);
		scaleLeft.setLayoutY(yPos);

		xPos = (int) ((upperLeft.x) * Editor.FIELD_SIZE - offSetX);
		yPos = (int) ((lowerRight.y) * Editor.FIELD_SIZE + distanceButtonBorder);

		scaleDownLeft.setLayoutX(xPos);
		scaleDownLeft.setLayoutY(yPos);

		// Right Side

		xPos = (int) ((lowerRight.x) * Editor.FIELD_SIZE + distanceButtonBorder);
		yPos = (int) ((upperLeft.y) * Editor.FIELD_SIZE - offSetY);

		scaleUpperRight.setLayoutX(xPos);
		scaleUpperRight.setLayoutY(yPos);

		xPos = (int) ((lowerRight.x) * Editor.FIELD_SIZE + distanceButtonBorder);
		yPos = (int) (((lowerRight.y + upperLeft.y) / 2) * Editor.FIELD_SIZE - scaleUp.getMaxHeight() / 2);

		scaleRight.setLayoutX(xPos);
		scaleRight.setLayoutY(yPos);

		xPos = (int) ((lowerRight.x) * Editor.FIELD_SIZE + distanceButtonBorder);
		yPos = (int) ((lowerRight.y) * Editor.FIELD_SIZE + distanceButtonBorder);

		scaleDownRight.setLayoutX(xPos);
		scaleDownRight.setLayoutY(yPos);

		// Top and Down

		xPos = (int) (((lowerRight.x + upperLeft.x) / 2) * Editor.FIELD_SIZE - (scaleUp.getMaxWidth() / 2));
		yPos = (int) ((upperLeft.y) * Editor.FIELD_SIZE - offSetY);

		scaleUp.setLayoutX(xPos);
		scaleUp.setLayoutY(yPos);

		xPos = (int) (((lowerRight.x + upperLeft.x) / 2) * Editor.FIELD_SIZE - (scaleUp.getMaxWidth() / 2));
		yPos = (int) ((lowerRight.y) * Editor.FIELD_SIZE + distanceButtonBorder);

		scaleDown.setLayoutX(xPos);
		scaleDown.setLayoutY(yPos);

	}

	private void setUpFunctionalities() {

		scaleLeft.setOnMouseDragged(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {

				mainContent.console.println("dragged to (" + event.getScreenX() + ", " + event.getScreenY() + ")");
				scaleHorizontal(new Vector2d((float) event.getScreenX() / Editor.FIELD_SIZE, (float) event.getScreenY() / Editor.FIELD_SIZE));
				mainContent.repaint();
				setUpPositions();
			}
		});
		scaleRight.setOnMouseDragged(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				mainContent.console.println("dragged to (" + event.getScreenX() + ", " + event.getScreenY() + ")");
				scaleHorizontal(new Vector2d((float) event.getScreenX() / Editor.FIELD_SIZE, (float) event.getScreenY() / Editor.FIELD_SIZE));
				mainContent.repaint();
				setUpPositions();
			}
		});

		scaleUp.setOnMouseDragged(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {

				mainContent.console.println("dragged to (" + event.getScreenX() + ", " + event.getScreenY() + ")");
				scaleVertical(new Vector2d((float) event.getScreenX() / Editor.FIELD_SIZE, (float) event.getScreenY() / Editor.FIELD_SIZE));
				mainContent.repaint();
				setUpPositions();
			}
		});
		scaleDown.setOnMouseDragged(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				mainContent.console.println("dragged to (" + event.getScreenX() + ", " + event.getScreenY() + ")");
				scaleVertical(new Vector2d((float) event.getScreenX() / Editor.FIELD_SIZE, (float) event.getScreenY() / Editor.FIELD_SIZE));
				mainContent.repaint();
				setUpPositions();
			}
		});

		scaleUpperRight.setOnMouseDragged(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				mainContent.console.println("dragged to (" + event.getScreenX() + ", " + event.getScreenY() + ")");
				scaleDiagonal(new Vector2d((float) event.getScreenX() / Editor.FIELD_SIZE, (float) event.getScreenY() / Editor.FIELD_SIZE));
				mainContent.repaint();
				setUpPositions();
			}
		});

		scaleUpperLeft.setOnMouseDragged(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				mainContent.console.println("dragged to (" + event.getScreenX() + ", " + event.getScreenY() + ")");
				scaleDiagonal(new Vector2d((float) event.getScreenX() / Editor.FIELD_SIZE, (float) event.getScreenY() / Editor.FIELD_SIZE));
				mainContent.repaint();
				setUpPositions();
			}
		});

		scaleDownRight.setOnMouseDragged(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				mainContent.console.println("dragged to (" + event.getScreenX() + ", " + event.getScreenY() + ")");
				scaleDiagonal(new Vector2d((float) event.getScreenX() / Editor.FIELD_SIZE, (float) event.getScreenY() / Editor.FIELD_SIZE));
				mainContent.repaint();
				setUpPositions();
			}
		});

		scaleDownLeft.setOnMouseDragged(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				mainContent.console.println("dragged to (" + event.getScreenX() + ", " + event.getScreenY() + ")");
				scaleDiagonal(new Vector2d((float) event.getScreenX() / Editor.FIELD_SIZE, (float) event.getScreenY() / Editor.FIELD_SIZE));
				mainContent.repaint();
				setUpPositions();
			}
		});

	}

	public void moveBox(float currX2, float currY2, MainContent mainContent) {
		if (currX == currX2 && currY2 == currY)
			return;

		lastX = currX;
		lastY = currY;
		this.currX = currX2;
		this.currY = currY2;

		if (mainContent.getCurrentImage() != null)
			mainContent.getCurrentImage().unmark(mainContent.getRoot());
		if (this.isMarked()) {
			this.update(new Vector2d((currX - lastX) / 50f, (currY - lastY) / 50f));
			mainContent.repaint();
		}
	}

	public boolean getHurts() {
		return hurts;
	}

	public void setHurts(boolean hurts) {
		this.hurts = hurts;
	}

	public void setMainContent(MainContent mainContent) {
		this.mainContent = mainContent;

	}

	@Override
	public Vector2d getLastPos() {
		resetAnchorPoints(lastPosition);
		return lastPosition;
	}

	private void resetAnchorPoints(Vector2d lastPosition) {
		currX = lastPosition.x * Editor.FIELD_SIZE;
		currY = lastPosition.y * Editor.FIELD_SIZE;
		lastX = lastPosition.x * Editor.FIELD_SIZE;
		lastY = lastPosition.y * Editor.FIELD_SIZE;
	}

	@Override
	public void setLastPos(Vector2d pos) {
		this.lastPosition = pos;
	}

}
