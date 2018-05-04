package content;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.xml.sax.SAXException;

import content.menu.EditAnimation;
import content.menu.EditBox;
import content.menu.EditEffect;
import content.menu.EditImage;
import content.menu.MyBonus;
import content.menu.MyMenuBar;
import content.menu.MyToolBox;
import content.misc.Console;
import content.misc.IToolObject;
import content.misc.Other;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollBar;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import main.Editor;
import math.BoundingRectangle;
import math.Vector2d;
import xml.XML_Box_Creater;
import xml.XML_Image_Creater;

public class MainContent extends ContentManager {

	// setup
	private GraphicsContext graphicsContext;
	private Image backGround = Other.loadImage("/images/raster.png");
	private Image cover = Other.loadImage("images/menu/IMG_Editor_Cover.png");
	public static final int MAX_SPRITE_TYPES = 32;

	private BoundingRectangle currPointer = null;
	public BoundingRectangle copiedPointer = null;
	private Stage mainStage;
	private final FileChooser fileChooser = new FileChooser();
	private File selectedFile = null;
	private XMLContent xmlReader;
	private XML_Box_Creater xml_box_writer;
	private XML_Image_Creater xml_image_writer;

	// Thread and Stuff
	private volatile Thread animatorExecuter;
	private volatile Animator animator;

	// Content from Character you are loading

	// Boxes
	private ArrayList<ArrayList<BoundingRectangle>> currentBox;
	private Map<String, ArrayList<ArrayList<BoundingRectangle>>> boxes;

	// Images
	private Map<String, ArrayList<LocatedImage>> images;

	// Basic Looping
	private Map<String, Boolean> loopBools = new HashMap<String, Boolean>();
	private Map<String, Integer> loopIndices = new HashMap<String, Integer>();

	// Times between Images
	private Map<String, ArrayList<Float>> times = new HashMap<String, ArrayList<Float>>();

	// Selected Contents
	private float currentDuration;
	private List<Float> currentTimes;
	private List<LocatedImage> currentImages;
	private LocatedImage currentImage;
	private int currentIndex = 0; // Image Key
	private String currentSeq = ""; // Name of Animation

	// Menu

	private MyMenuBar myMenuBar;
	private MyToolBox myToolBox;
	private EditImage myEditImage;
	private EditBox myEditBox;
	private EditAnimation myEditAnimation;
	private EditEffect myEditEffect;
	private MyBonus myBonus;

	public Button playAnimation = new Button("Play");
	public Button stopAnimation = new Button("Stop");

	// Misc
	private ScrollBar sc = new ScrollBar();
	public static Vector2d CENTER = new Vector2d(15f, 8f);
	private static final double PREVIOUS_IMAGE_OPACITY = 0.5;
	private String currentCharName = "";
	private IToolObject currentIToolBox = null;
	private boolean drawPreviousImage = false;
	private ColorAdjust monochrome;
	private boolean lastBoxesCopiedOnce;
	private boolean disableUpdate = false;
	public static int screen_width;
	public static int screen_height;

	// Conctructor
	public MainContent(Group root, Canvas canvas, Console console, Stage mainStage, int width, int height) {
		super(root, canvas, console);
		MainContent.screen_height = height;
		MainContent.screen_width = width;

		CENTER.x = MainContent.screen_width / 100f;

		try {
			setXmlReader(new XMLContent(this));
			this.setXml_box_writer(new XML_Box_Creater(this));
			this.setXml_image_writer(new XML_Image_Creater(this));
			this.setCurrentBox(new ArrayList<ArrayList<BoundingRectangle>>());
			this.setImages(new HashMap<String, ArrayList<LocatedImage>>());
			this.setBoxes(new HashMap<String, ArrayList<ArrayList<BoundingRectangle>>>());
			this.setCurrentImages(Collections.synchronizedList(new ArrayList<LocatedImage>()));
			this.setCurrentTimes(Collections.synchronizedList(new ArrayList<Float>()));
			this.setMainStage(mainStage);
			getFileChooser().getExtensionFilters().addAll(new FileChooser.ExtensionFilter("XML", "*.xml"));
			init(canvas);

		} catch (SAXException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void init(Canvas canvas) {
		console.println("Content loading...");
		canvas.setHeight(screen_height);
		canvas.setWidth(screen_width);
		graphicsContext = canvas.getGraphicsContext2D();
		root.getChildren().add(canvas);
		createMenu();
		createMisc();
		console.println("Content loaded succesfully");
		setAnimator(new Animator(this));
		repaint();

	}

	public void clearComboBox() {
		myEditAnimation.getSelected_sprites_input().getItems().clear();
	}

	public void clear() {
		if (!getCurrentBox().get(getCurrentIndex()).isEmpty()) {

			for (BoundingRectangle box : getCurrentBox().get(getCurrentIndex()))
				box.unmark(root);

			getCurrentBox().get(getCurrentIndex()).clear();
			setCurrPointer(null);

			console.println("Cleared All Boxes in this Image");
		}
	}

	public void clearAll() {
		if (!getBoxes().isEmpty()) {

			for (String boxes : getBoxes().keySet()) {
				for (ArrayList<BoundingRectangle> boxList : getBoxes().get(boxes)) {
					for (BoundingRectangle box : boxList)
						box.unmark(root);
					boxList.clear();
				}

			}
			setCurrPointer(null);
			console.println("Cleared All BoxLists and Boxes");
		}

	}

	public boolean getVal(String value) {
		if (value.equals("true"))
			return true;
		else
			return false;
	}

	public synchronized void repaint() {
		graphicsContext.drawImage(backGround, 0, -1, screen_width,screen_height);
		graphicsContext.drawImage(cover, screen_width - cover.getWidth(), 0);

		myMenuBar.repaint(graphicsContext);
		myToolBox.repaint(graphicsContext);
		myEditImage.repaint(graphicsContext);
		myEditBox.repaint(graphicsContext);
		myEditAnimation.repaint(graphicsContext);
		myEditEffect.repaint(graphicsContext);
		myBonus.repaint(graphicsContext);

		if (getCurrentImage() != null)
			drawImage();
		if (getCurrentBox() != null && getCurrentIndex() < getCurrentBox().size())
			drawBoxes();
	}

	public void createMenu() {
		myMenuBar = new MyMenuBar(new Vector2d(), root, this);
		myToolBox = new MyToolBox(new Vector2d(), root, this);
		myEditImage = new EditImage(new Vector2d(), root, this);
		myEditBox = new EditBox(new Vector2d(), root, this);
		myEditAnimation = new EditAnimation(new Vector2d(), root, this);
		myEditEffect = new EditEffect(new Vector2d(), root, this);
		myBonus = new MyBonus(new Vector2d(), root, this);
	}

	public void createMisc() {
		// ScrollBar
		getSc().setMin(0);
		getSc().setMax(0);
		getSc().setValue(0);
		
		getSc().setMinWidth(265);
		getSc().setMaxWidth(265);
		getSc().setMinHeight(30);
		getSc().setMaxHeight(30);
		
		
		getSc().setLayoutX(myEditAnimation.getxOffset()*Editor.FIELD_SIZE + 309);
		getSc().setLayoutY(myEditAnimation.getyOffset()*Editor.FIELD_SIZE + 163);
		
		
		getSc().valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {
				if (t != t1) {
					lastBoxesCopiedOnce = false;
				}

				setCurrentIndex((Integer) t1.intValue());

				console.println("Sequence " + getCurrentSeq() + " selected.");
				console.println("Picture " + getCurrentIndex() + " selected.");
				setCurrentImage(getCurrentImages().get(getCurrentIndex()));
				setCurrentDuration(getCurrentTimes().get(getCurrentIndex()));
				myEditImage.getDuration_input().setText("" + getCurrentDuration());
				updateImportant();
			}
		});
		
		
		
		root.getChildren().add(getSc());
		console.println("Scrollbar loaded succesfully");
	}

	public void drawBoxes() {
		
		if(animator.isRunning())
			return;
		
		for (int i = 0; i < getCurrentBox().get(getCurrentIndex()).size(); i++)
			getCurrentBox().get(getCurrentIndex()).get(i).draw(graphicsContext);
	}

	private void drawImage() {

		float x = 0;
		float y = 0;

		if (currentIndex > 0 && drawPreviousImage) {

			x = (getCurrentImages().get(currentIndex - 1).getPos().x) * Editor.FIELD_SIZE - (float) getCurrentImages().get(currentIndex - 1).getWidth() / 2f;
			y = (getCurrentImages().get(currentIndex - 1).getPos().y) * Editor.FIELD_SIZE - (float) getCurrentImages().get(currentIndex - 1).getHeight() / 2f;

			monochrome = new ColorAdjust();
			monochrome.setSaturation(-1);
			graphicsContext.setGlobalAlpha(PREVIOUS_IMAGE_OPACITY);
			graphicsContext.setEffect(monochrome);
			graphicsContext.drawImage(getCurrentImages().get(currentIndex - 1), x, y - fixPic(getCurrentImages().get(currentIndex - 1)));
			graphicsContext.setEffect(null);
			graphicsContext.setGlobalAlpha(1);
		}

		x = (getCurrentImage().getPos().x) * Editor.FIELD_SIZE - (float) getCurrentImage().getWidth() / 2f;
		y = (getCurrentImage().getPos().y) * Editor.FIELD_SIZE - (float) getCurrentImage().getHeight() / 2f;

		graphicsContext.drawImage(getCurrentImage(), x, y - fixPic(getCurrentImage()));
		getCurrentImage().drawBorder(graphicsContext);
	}

	public void enablePreviousImage() {
		this.drawPreviousImage = !drawPreviousImage;
	}

	public float fixPic(Image i) {
		float halfHeight = (float) (i.getHeight() / 2);
		float diff = 0f;
		if (halfHeight >= 300f)
			return 0f;
		if (100 < halfHeight)
			diff = halfHeight - 100;
		if (100 > halfHeight)
			diff = halfHeight - 100;
		return diff;
	}

	public void updateImages() {
		setCurrentIndex(0);
		if (getCurrentImages() != null) {
			float size = getCurrentImages().size();
			this.getSc().setMax(size - 1);
			this.getSc().setValue(0);
			setCurrentImage(getCurrentImages().get(getCurrentIndex()));
		}
	}

	@Override
	public void update(float x, float y) {
		
		if(isDisableUpdate())
			return;
		
		lastX = currX;
		lastY = currY;
		this.currX = x;
		this.currY = y;
		updateImportant();

	}

	public void updateImportant() {
		updateCurrentImage();
		updateBoxes();
		updateMenuObjects();
		repaint();
	}

	private void updateMenuObjects() {
		myMenuBar.update();
		myToolBox.update();
		myEditImage.update();
		myEditBox.update();
		myEditAnimation.update();
		myEditEffect.update();
		myBonus.update();
	}

	public void updateFile() {
		// Load Stuff
		setImages(getXmlReader().loadCharacter(this.getSelectedFile()));
		setTimes(getXmlReader().getXml_Image_Reader().getTimes());
		setLoopBools(getXmlReader().getXml_Image_Reader().getLoopBools());
		setLoopIndices(getXmlReader().getXml_Image_Reader().getLoopIndices());

		if (getImages() == null)
			console.println("Char went missing.");
		setupImages();
		updateImages();
		myEditAnimation.update();
		updateImportant();
	}

	private synchronized void setupImages() {
		console.println();
		console.println();

		int n = 0;
		for (Map.Entry<String, ArrayList<LocatedImage>> entry : getImages().entrySet()) {

			// String Schlüssel der Leiste hinzufügen
			setCurrentSeq(entry.getKey().toString());

			myEditAnimation.getSelected_sprites_input().getItems().add(getCurrentSeq());

			// Anzahl der Bilder
			n = getImages().get(getCurrentSeq()).size();

			myEditAnimation.getSelected_sprites_input().setValue(getCurrentSeq());

			console.println("Added " + getCurrentSeq());

			this.getBoxes().put(getCurrentSeq(), new ArrayList<ArrayList<BoundingRectangle>>());
			createEmptyLists(getCurrentSeq(), n);
		}

		for (int i = 0; i < n; i++) {
			myEditAnimation.getLoopIndex_input().getItems().add("" + i);
		}

		setCurrentTimes(getTimes().get(getCurrentSeq()));
		myEditAnimation.getLoopBool_input().setValue(getLoopBools().get(getCurrentSeq()).toString());
		myEditAnimation.getLoopIndex_input().setValue(getLoopIndices().get(getCurrentSeq()).toString());
		myEditImage.getDuration_input().setText("" + getCurrentTimes().get(getCurrentIndex()));
		setCurrentBox(getBoxes().get(getCurrentSeq()));

	}

	public void clearAllData() {
		// Clear Images
		for (Map.Entry<String, ArrayList<LocatedImage>> entry : getImages().entrySet()) {
			ArrayList<LocatedImage> images = getImages().get(entry);
			if (images != null)
				images.clear();
		}
		getImages().clear();

		// Clear Times
		for (Map.Entry<String, ArrayList<Float>> entry : getTimes().entrySet()) {
			ArrayList<Float> times = getTimes().get(entry);
			if (times != null)
				times.clear();
		}
		getTimes().clear();

		// Clear Loop Indices
		getLoopIndices().clear();

		// Clear Loop Bools
		getLoopBools().clear();
		System.gc();

	}

	private void createEmptyLists(String next, int n) {
		for (int i = 0; i < n; i++) {
			this.getBoxes().get(next).add(new ArrayList<BoundingRectangle>());
		}
	}

	private void updateCurrentImage() {
		if (getCurrentImage() == null)
			return;

		setCurrentImage(getCurrentImages().get(currentIndex));
		
		if (getCurrentImage().clickedOnThis(currX, currY)) {

			if (getCurrPointer() != null && getCurrPointer().isMarked())
				getCurrPointer().unmark(root);

			this.setCurrentIToolBox(getCurrentImage());
			getCurrentImage().mark(root);
			return;
		} else
			getCurrentImage().unmark(root);

	}

	private synchronized void updateBoxes() {

		if (getCurrentIndex() >= getCurrentBox().size())
			return;
		for (int i = 0; i < getCurrentBox().get(getCurrentIndex()).size(); i++) {
			getCurrentBox().get(getCurrentIndex()).get(i).unmark(root);
		}

		for (int i = 0; i < getCurrentBox().get(getCurrentIndex()).size(); i++) {
			if (getCurrentBox().get(getCurrentIndex()).get(i).clickedOnBorder(currX, currY)) {

				if (currentImage != null && currentImage.isMarked())
					currentImage.unmark(root);

				getCurrentBox().get(getCurrentIndex()).get(i).mark(root);
				this.setCurrentIToolBox(getCurrentBox().get(getCurrentIndex()).get(i));
				this.currPointer = getCurrentBox().get(getCurrentIndex()).get(i);
				return;
			}
		}
		
		for (int i = 0; i < getCurrentBox().get(getCurrentIndex()).size(); i++) {
			if (getCurrentBox().get(getCurrentIndex()).get(i).clickedOnArea(currX, currY)) {

				if (currentImage != null && currentImage.isMarked())
					currentImage.unmark(root);

				getCurrentBox().get(getCurrentIndex()).get(i).mark(root);
				this.setCurrentIToolBox(getCurrentBox().get(getCurrentIndex()).get(i));
				this.currPointer = getCurrentBox().get(getCurrentIndex()).get(i);
				return;
			}
		}
		
		

	}

	public void addBox(BoundingRectangle box) {
		try {
			this.getCurrentBox().get(getCurrentIndex()).add(box);
		} catch (IndexOutOfBoundsException e) {
			console.println("Error, image file is missing.");
		}
	}

	public void copyPreviousBoxes() {
		if (currentIndex > 0 && !lastBoxesCopiedOnce) {
			ArrayList<BoundingRectangle> previousBoxList = currentBox.get(currentIndex - 1);
			for (BoundingRectangle box : previousBoxList)
				myToolBox.copyBox(box, false);

			lastBoxesCopiedOnce = true;
		}
	}

	// --------------------------------------------------------------------------
	// --------------------------------------------------------------------------
	// --------------------------------------------------------------------------

	public void deleteBox(BoundingRectangle box) {
		box.unmark(root);
		this.getCurrentBox().get(getCurrentIndex()).remove(box);
	}

	public BoundingRectangle getCurrPointer() {
		return currPointer;
	}

	public void setCurrPointer(BoundingRectangle currPointer) {
		this.currPointer = currPointer;
	}

	public void setFile(File file) {
		this.setSelectedFile(file);

	}

	public void setCurrentChar(String name) {
		this.setCurrentCharName(name);

	}

	public ScrollBar getSc() {
		return sc;
	}

	public void setSc(ScrollBar sc) {
		this.sc = sc;
	}

	public FileChooser getFileChooser() {
		return fileChooser;
	}

	public Stage getMainStage() {
		return mainStage;
	}

	public void setMainStage(Stage mainStage) {
		this.mainStage = mainStage;
	}

	public Map<String, ArrayList<ArrayList<BoundingRectangle>>> getBoxes() {
		return boxes;
	}

	public void setBoxes(Map<String, ArrayList<ArrayList<BoundingRectangle>>> boxes) {
		this.boxes = boxes;
	}

	public File getSelectedFile() {
		return selectedFile;
	}

	public void setSelectedFile(File selectedFile) {
		this.selectedFile = selectedFile;
	}

	public XML_Box_Creater getXml_box_writer() {
		return xml_box_writer;
	}

	public void setXml_box_writer(XML_Box_Creater xml_box_writer) {
		this.xml_box_writer = xml_box_writer;
	}

	public XML_Image_Creater getXml_image_writer() {
		return xml_image_writer;
	}

	public void setXml_image_writer(XML_Image_Creater xml_image_writer) {
		this.xml_image_writer = xml_image_writer;
	}

	public String getCurrentCharName() {
		return currentCharName;
	}

	public void setCurrentCharName(String currentCharName) {
		this.currentCharName = currentCharName;
	}

	public Map<String, ArrayList<LocatedImage>> getImages() {
		return images;
	}

	public void setImages(Map<String, ArrayList<LocatedImage>> images) {
		this.images = images;
	}

	public Map<String, Boolean> getLoopBools() {
		return loopBools;
	}

	public void setLoopBools(Map<String, Boolean> loopBools) {
		this.loopBools = loopBools;
	}

	public Map<String, Integer> getLoopIndices() {
		return loopIndices;
	}

	public void setLoopIndices(Map<String, Integer> loopIndices) {
		this.loopIndices = loopIndices;
	}

	public Map<String, ArrayList<Float>> getTimes() {
		return times;
	}

	public void setTimes(Map<String, ArrayList<Float>> times) {
		this.times = times;
	}

	public int getCurrentIndex() {
		return currentIndex;
	}

	public void setCurrentIndex(int currentIndex) {
		this.currentIndex = currentIndex;
	}

	public XMLContent getXmlReader() {
		return xmlReader;
	}

	public void setXmlReader(XMLContent xmlReader) {
		this.xmlReader = xmlReader;
	}

	public ArrayList<ArrayList<BoundingRectangle>> getCurrentBox() {
		return currentBox;
	}

	public void setCurrentBox(ArrayList<ArrayList<BoundingRectangle>> currentBox) {
		this.currentBox = currentBox;
	}

	public String getCurrentSeq() {
		return currentSeq;
	}

	public void setCurrentSeq(String currentSeq) {
		this.currentSeq = currentSeq;
	}

	public MyToolBox getMyToolBox() {
		return myToolBox;
	}

	public int getScreen_width() {
		return screen_width;
	}

	public int getScreen_height() {
		return screen_height;
	}

	public float getCurrentDuration() {
		return currentDuration;
	}

	public void setCurrentDuration(float currentDuration) {
		this.currentDuration = currentDuration;
	}

	public LocatedImage getCurrentImage() {
		return currentImage;
	}

	public void setCurrentImage(LocatedImage currentImage) {
		this.currentImage = currentImage;
	}

	public List<Float> getCurrentTimes() {
		return currentTimes;
	}

	public void setCurrentTimes(List<Float> currentTimes) {
		this.currentTimes = currentTimes;
	}

	public EditImage getMyEditImage() {
		return myEditImage;
	}

	public IToolObject getCurrentIToolBox() {
		return currentIToolBox;
	}

	public void setCurrentIToolBox(IToolObject currentIToolBox) {
		this.currentIToolBox = currentIToolBox;
	}

	/**
	 * @return the currentImages
	 */
	public List<LocatedImage> getCurrentImages() {
		return currentImages;
	}

	/**
	 * @param currentImages
	 *            the currentImages to set
	 */
	public void setCurrentImages(List<LocatedImage> currentImages) {
		this.currentImages = currentImages;
	}

	/**
	 * @return the animator
	 */
	public Animator getAnimator() {
		return animator;
	}

	/**
	 * @param animator
	 *            the animator to set
	 */
	public void setAnimator(Animator animator) {
		this.animator = animator;
	}

	/**
	 * @return the animatorExecuter
	 */
	public Thread getAnimatorExecuter() {
		return animatorExecuter;
	}

	/**
	 * @param animatorExecuter the animatorExecuter to set
	 */
	public void setAnimatorExecuter(Thread animatorExecuter) {
		this.animatorExecuter = animatorExecuter;
	}

	public void disableObjects(boolean disable) {
		
		setDisableUpdate(disable);
		
		myEditAnimation.disableObjects(disable);
		myEditBox.disableObjects(disable);
		myEditImage.disableObjects(disable);
		myEditEffect.disableObjects(disable);
		myBonus.disableObjects(disable);
		
		myMenuBar.disableObjects(disable);
		myToolBox.disableObjects(disable);

		getSc().setDisable(disable);
		getSc().setValue(0);
		
	}

	/**
	 * @return the disableUpdate
	 */
	public boolean isDisableUpdate() {
		return disableUpdate;
	}

	/**
	 * @param disableUpdate the disableUpdate to set
	 */
	public void setDisableUpdate(boolean disableUpdate) {
		this.disableUpdate = disableUpdate;
	}

}
