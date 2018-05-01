package content.misc;

import java.io.File;

import content.MainContent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
 
public final class FileOpener extends Stage {
 
    private final FileChooser fileChooser = new FileChooser();    
    final Button openButton = new Button("Open XML-File");
    private MainContent mainContent;
    
    private FileOpener selfPointer = this;
    private File file;
   
    public FileOpener(MainContent mainContent) {
    	this.mainContent = mainContent;
    	fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("XML", "*.xml"));
        this.setTitle("Choose a XML-File");
        
        
        openButton.setOnAction(
            new EventHandler<ActionEvent>() {
                @Override
                public void handle(final ActionEvent e) {
                    File file = fileChooser.showOpenDialog(selfPointer);
                    if (file != null) {
                    	int ch = file.getName().indexOf(".xml");
                    	selfPointer.mainContent.setCurrentChar(file.getName().substring(0, ch));
                    	selfPointer.mainContent.setFile(file);
                    	selfPointer.mainContent.updateFile();
                    	selfPointer.close();
                    }
                }
            });
 
  
 
 
        final GridPane inputGridPane = new GridPane();
 
        GridPane.setConstraints(openButton, 0, 0);
        inputGridPane.setHgap(6);
        inputGridPane.setVgap(6);
        inputGridPane.getChildren().addAll(openButton);
 
        final Pane rootGroup = new VBox(12);
        rootGroup.getChildren().addAll(inputGridPane);
        rootGroup.setPadding(new Insets(12, 12, 12, 12));
 
        this.setScene(new Scene(rootGroup));
        this.show();
    }

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}
 
    
}