package content.misc;


public class WordWrapConsole extends Console {
    public WordWrapConsole(int width, int height) {
        super(width,height);
        textArea.setWrapText(true);
    }
    
    
}