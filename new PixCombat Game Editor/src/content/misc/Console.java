package content.misc;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;



public class Console extends BorderPane {
    protected final TextArea textArea = new TextArea();   
    protected final List<String> history = new ArrayList<>();
    protected int historyPointer = 0;
    protected int screen_width;
    protected int screen_height;
    public Console(int width, int height) {
    	this.screen_height = height;
    	this.screen_width  = width;;
    	
    	setLayoutX(10);
    	setLayoutY(80);
        textArea.setEditable(false);
        textArea.setMaxSize(screen_width-20, screen_height-90);
        textArea.setMinSize(screen_width-20, screen_height-90);
        textArea.setStyle(""
                + "-fx-font-size: 14px;"
                + "-fx-font-style: consolas;"
                + "-fx-font-weight: bold;"
                + "-fx-font-family: consolas;"
                + "-fx-text-fill: white;"
                + "-fx-control-inner-background: black");
        setCenter(textArea);
        
    }

    @Override
    public void requestFocus() 
    {
        super.requestFocus();   
    }

    public void setOnMessageReceivedHandler(final Consumer<String> onMessageReceivedHandler) {
    }

    public void clear() 
    {
    	GUIUtils.runSafe(() -> textArea.clear());
    }

    public void print(final String text) 
    {
        Objects.requireNonNull(text, "text");
        GUIUtils.runSafe(() -> textArea.appendText(text));
    }

    public void println(final String text) {
        Objects.requireNonNull(text, "text");
        GUIUtils.runSafe(() -> textArea.appendText(text + System.lineSeparator()));
    }

    public void println() {
    	GUIUtils.runSafe(() -> textArea.appendText(System.lineSeparator()));
    }
    
    
   
    
    
}
