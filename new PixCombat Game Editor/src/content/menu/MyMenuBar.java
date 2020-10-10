package content.menu;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

import content.LocatedImage;
import content.MainContent;
import content.misc.Other;
import exceptions.ContentNullException;
import exceptions.SizeNotEqualException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import main.Editor;
import math.BoundingRectangle;
import math.Vector2d;

public class MyMenuBar extends MenuObject {

	private static final LocatedImage bground = Other.loadImage("/images/menu/IMG_MenuBox_Menu.png");

	private static final ImageView HELP_UNHOVERED = new ImageView(Other.loadImage("/images/menu/IMG_MenuPoint_Help_Unhovered.png").image);

	private static final ImageView TOGGLEFULLSCREEN_HOVERED = new ImageView(Other.loadImage("/images/menu/IMG_MenuPointSelection_ToggleFullscreen_Unhovered.png").image);
	private static final ImageView TOGGLECONSOLE_HOVERED = new ImageView(Other.loadImage("/images/menu/IMG_MenuPointSelection_ToggleConsole_Unhovered.png").image);

	
	private static final ImageView HELPITEM_HOVERED = new ImageView(Other.loadImage("/images/menu/IMG_MenuPointSelection_ToggleFullscreen_Unhovered.png").image);

	private static final ImageView OPTIONS_UNHOVERED = new ImageView(Other.loadImage("/images/menu/IMG_MenuPoint_Options_Unhovered.png").image);
	
	private static final ImageView LOADPROJECTILE_UNHOVERED = new ImageView(Other.loadImage("/images/menu/IMG_MenuPointSelection_LoadSpecialEffect_Unhovered.png").image);
	private static final ImageView LOADBOXES_UNHOVERED = new ImageView(Other.loadImage("/images/menu/IMG_MenuPointSelection_LoadBoxes_Unhovered.png").image);
	private static final ImageView LOADFILE_UNHOVERED = new ImageView(Other.loadImage("/images/menu/IMG_MenuPoint_LoadFile_Unhovered.png").image);

	private static final ImageView SAVEPROJECTILE_UNHOVERED = new ImageView(Other.loadImage("/images/menu/IMG_MenuPointSelection_SaveProjectile_Unhovered.png").image);
	private static final ImageView SAVECHARACTER_UNHOVERED = new ImageView(Other.loadImage("/images/menu/IMG_MenuPointSelection_SaveCharacter_Unhovered.png").image);
	private static final ImageView SAVEFILE_UNHOVERED = new ImageView(Other.loadImage("/images/menu/IMG_MenuPoint_SaveFile_Unhovered.png").image);

	private static final ImageView NEWSPECIALEFFECT_UNHOVERED = new ImageView(Other.loadImage("/images/menu/IMG_MenuPointSelection_NewSpecialEffect_Unhovered.png").image);
	private static final ImageView NEWPROJECTILE_UNHOVERED = new ImageView(Other.loadImage("/images/menu/IMG_MenuPointSelection_NewProjectile_Unhovered.png").image);
	private static final ImageView NEWCHARACTER_UNHOVERED = new ImageView(Other.loadImage("/images/menu/IMG_MenuPointSelection_NewCharacter_Unhovered.png").image);
	private static final ImageView MAKEFILE_UNHOVERED = new ImageView(Other.loadImage("/images/menu/IMG_MenuPoint_MakeFile_Unhovered.png").image);

	// Menu
	private MenuBar menuBar;

	private Menu makeFile;
	private MenuItem newCharacter;
	private MenuItem newProjectile;
	private MenuItem newSpecialEffect;

	private Menu saveFile;
	private MenuItem saveCharacter;
	private MenuItem saveProjectile;

	private Menu loadFile;
	private MenuItem loadBoxes;
	private MenuItem loadSpecialEffect;

	private Menu options;
	private MenuItem options_fullscreen;
	private MenuItem options_console;

	private Menu help;
	private MenuItem menuHELP;

	private float xOffset = 3.5f;
	private float yOffset = 0f;

	public MyMenuBar(Vector2d pos, Group root, MainContent contentManager) {
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

		createMenuItems();
		setPositions();
		addToRoot();

	}

	private void addToRoot() {
		makeFile.getItems().addAll(newCharacter, newProjectile, newSpecialEffect);
		saveFile.getItems().addAll(saveCharacter, saveProjectile);
		loadFile.getItems().addAll(loadBoxes, loadSpecialEffect);
		options.getItems().addAll(options_fullscreen,options_console);
		help.getItems().addAll(menuHELP);
		menuBar.getMenus().addAll(makeFile, saveFile, loadFile, options, help);
		root.getChildren().add(menuBar);
	}

	@Override
	public void disableObjects(boolean disable) {
		this.newProjectile.setDisable(disable);
		this.newCharacter.setDisable(disable);
		this.newSpecialEffect.setDisable(disable);

		this.saveCharacter.setDisable(disable);
		this.saveProjectile.setDisable(disable);

		this.options_fullscreen.setDisable(disable);
		this.options_console.setDisable(disable);

		this.menuHELP.setDisable(disable);

		this.makeFile.setDisable(disable);
		this.saveFile.setDisable(disable);
		this.loadFile.setDisable(disable);
		this.options.setDisable(disable);
		this.help.setDisable(disable);

		this.menuBar.setDisable(disable);

	}

	private void setPositions() {
		menuBar.setLayoutX((getPos().x + xOffset) * Editor.FIELD_SIZE + 320);
		menuBar.setLayoutY((getPos().y + yOffset) * Editor.FIELD_SIZE + 10);

	}

	private void createMenuItems() {
		menuBar = new MenuBar();
		menuBar.setBackground(Background.EMPTY);

		makeFile = new Menu("", MAKEFILE_UNHOVERED);
		newCharacter = new MenuItem("", NEWCHARACTER_UNHOVERED);
		newProjectile = new MenuItem("", NEWPROJECTILE_UNHOVERED);
		newSpecialEffect = new MenuItem("", NEWSPECIALEFFECT_UNHOVERED);

		saveFile = new Menu("", SAVEFILE_UNHOVERED);
		saveCharacter = new MenuItem("", SAVECHARACTER_UNHOVERED);
		saveProjectile = new MenuItem("", SAVEPROJECTILE_UNHOVERED);

		loadFile = new Menu("", LOADFILE_UNHOVERED);
		loadBoxes = new MenuItem("", LOADBOXES_UNHOVERED);
		loadSpecialEffect = new MenuItem("", LOADPROJECTILE_UNHOVERED);

		options = new Menu("", OPTIONS_UNHOVERED);
		options_fullscreen = new MenuItem("", TOGGLEFULLSCREEN_HOVERED);
		options_console	   = new MenuItem("", TOGGLECONSOLE_HOVERED);

		help = new Menu("", HELP_UNHOVERED);
		menuHELP = new MenuItem("", HELPITEM_HOVERED);

		newCharacter.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(final ActionEvent e) {
				File file = contentManager.getFileChooser().showOpenDialog(contentManager.getMainStage());
				if (file != null) {
					try { 
						contentManager.clearAllData();
						contentManager.clearComboBox();
						contentManager.clearAll();
						contentManager.getBoxes().clear();
						int ch = file.getName().indexOf(".xml");
						contentManager.setCurrentCharName(file.getName().substring(0, ch));
						contentManager.setSelectedFile(file);
						contentManager.updateFile();
					}
					catch(Exception e1){
						contentManager.console.println(e1.getMessage());
					}

				}
			}
		});

		saveCharacter.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(final ActionEvent e) {
				if (contentManager.getSelectedFile() != null) {
					try {
						contentManager.getXml_box_writer().createXML_Box(contentManager.getCurrentCharName(), contentManager.getBoxes());
						contentManager.getXml_image_writer().setVariables(contentManager.getImages(), contentManager.getLoopBools(), contentManager.getLoopIndices(), contentManager.getTimes());
						contentManager.getXml_image_writer().createXML_Image(contentManager.getCurrentCharName());
					} catch (SizeNotEqualException e1) {
						contentManager.console.println(e1.getMessage());
					} catch (ContentNullException e2) {
						contentManager.console.println(e2.getMessage());
					}
					catch(Exception e3){
						contentManager.console.println(e3.getMessage());
					}
				}
			}
		});

		loadBoxes.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(final ActionEvent e) {
				File file = contentManager.getFileChooser().showOpenDialog(contentManager.getMainStage());
				if (file != null) {
					contentManager.getBoxes().clear();
					contentManager.setBoxes(calibrate(contentManager.getXmlReader().loadBox(file)));
					contentManager.setCurrentIndex(0);
					contentManager.setCurrentBox(contentManager.getBoxes().get(contentManager.getCurrentSeq()));
					contentManager.updateImportant();
				}

				// FileOpener fileOpener = new FileOpener(selfPointer);

			}

			private Map<String, ArrayList<ArrayList<BoundingRectangle>>> calibrate(Map<String, ArrayList<ArrayList<BoundingRectangle>>> loadBox) {

				for (Map.Entry<String, ArrayList<ArrayList<BoundingRectangle>>> entry : loadBox.entrySet()) {

					ArrayList<ArrayList<BoundingRectangle>> boxlistlist = entry.getValue();
					for (int i = 0; i < boxlistlist.size(); i++) {
						ArrayList<BoundingRectangle> boxlist = boxlistlist.get(i);
						for (int j = 0; j < boxlist.size(); j++) {
							BoundingRectangle box = boxlist.get(j);
							float x = box.getPos().x + MainContent.CENTER.x;
							float y = box.getPos().y + MainContent.CENTER.y;

							box.edit(box.getHeight(), new Vector2d(x, y), box.getWidth());
						}

					}

				}

				return loadBox;
			}
		});

		// menuHELP.setOnAction(new EventHandler<ActionEvent>() {
		//
		// @Override
		// public void handle(final ActionEvent e) {
		// if (contentManager.getSc().getValue() == contentManager.getSc().getMax())
		// contentManager.getSc().setValue(contentManager.getSc().getMin());
		// else
		// contentManager.getSc().increment();
		// }
		//
		// });

		options_fullscreen.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(final ActionEvent e) {
				boolean fullscreen = contentManager.getMainStage().isFullScreen();
				contentManager.getMainStage().setFullScreen(!fullscreen);
			}
		});
		
		options_console.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(final ActionEvent e) {
				if(!contentManager.getConsoleStage().isShowing())
						contentManager.getConsoleStage().show();
			}
		});

		contentManager.console.println("Menu loaded succesfully");

	}

	public MenuBar getMenuBar() {
		return menuBar;
	}

}
