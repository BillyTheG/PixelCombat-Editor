package content;

import content.misc.IToolObject;
import javafx.scene.Group;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import main.Editor;
import math.Vector2d;

public class LocatedImage  implements IToolObject{
    private final String url;
    private boolean isMarked = false;
    private Vector2d pos;
    private Vector2d offsetPos  = new Vector2d();
    private Vector2d upperLeft	= new Vector2d();
    private Vector2d lowerRight	= new Vector2d();
    public float currX  = MainContent.CENTER.x * Editor.FIELD_SIZE;
	public float currY  = MainContent.CENTER.y * Editor.FIELD_SIZE;
	public 	float lastX  = MainContent.CENTER.x * Editor.FIELD_SIZE;;
	public 	float lastY  = MainContent.CENTER.y * Editor.FIELD_SIZE;;
    private Vector2d lastPosition 	= new Vector2d(MainContent.CENTER.x,MainContent.CENTER.y);
	public  Image image;
	private float oldScale = 1f;
    
    public LocatedImage(Image image,String url) {
        this.url = url;
        this.image = image;
        this.pos = new Vector2d(MainContent.CENTER.x,MainContent.CENTER.y);   
    }

    public void update(Vector2d dir){
    	
    	pos.x  = dir.x;
    	pos.y = dir.y;
    	
    	updateCorner();
    	
    }


	public void resize(float sCALE_FACTOR) {
		
		this.image = scale(this.image,((int)( this.image.getWidth()/((double)oldScale))), ((int)(this.image.getHeight()/((double)oldScale))), true);
		
		
		float x_old = (pos.x -MainContent.CENTER.x) / oldScale;
		float y_old = (pos.y -MainContent.CENTER.y) / oldScale;
		
		
		this.pos = new Vector2d( MainContent.CENTER.x+ x_old * sCALE_FACTOR, MainContent.CENTER.y + y_old * sCALE_FACTOR);
		this.image = scale(this.image,((int)( this.image.getWidth()*((double)sCALE_FACTOR))), ((int)(this.image.getHeight()*((double)sCALE_FACTOR))), true);
		this.oldScale = sCALE_FACTOR;
	}
    
    
	private void updateOffset() {
	   offsetPos.x = pos.x - MainContent.CENTER.x;
	   offsetPos.y = pos.y - MainContent.CENTER.y;
	}
	
	public void checkNewOffset(MainContent contentManager) {
		float oldOffsetX = Float.parseFloat(contentManager.getMyEditImage().getImageX_input().getText());
		float oldOffsetY = Float.parseFloat(contentManager.getMyEditImage().getImageY_input().getText());
		
		Vector2d oldOffset = new Vector2d(oldOffsetX,oldOffsetY);
		
		if(offsetPos.distance(oldOffset)> 0.1){
			contentManager.getMyEditImage().getImageX_input().setText(""+offsetPos.x);		
			contentManager.getMyEditImage().getImageY_input().setText(""+offsetPos.y);		
			
		}
			
	}

	@Override
	public void updateCorner() {
		upperLeft.x 	= pos.x - (float)image.getWidth()/100f;
    	upperLeft.y 	= pos.y - (float)image.getHeight()/100f;
    	
    	lowerRight.x 	= pos.x + (float)image.getWidth()/100f;
    	lowerRight.y 	= pos.y + (float)image.getHeight()/100f;
    	updateOffset();
	}
    
    public boolean clickedOnThis(float x, float y){
    	updateCorner();
    	
    	float x1 = upperLeft.x 	* Editor.FIELD_SIZE;
		float y1 = upperLeft.y 	* Editor.FIELD_SIZE;
		float x2 = lowerRight.x * Editor.FIELD_SIZE;
		float y2 = lowerRight.y * Editor.FIELD_SIZE;
		
		return (x1 <= x && x <= x2) && (y1 <= y && y <= y2);

    }
    
    public void drawBorder(GraphicsContext g){
    	if(!this.isMarked)
    		return;
    	
    	float x1 = upperLeft.x 	* Editor.FIELD_SIZE;
		float y1 = upperLeft.y 	* Editor.FIELD_SIZE;
		float x2 = lowerRight.x * Editor.FIELD_SIZE;
		float y2 = lowerRight.y * Editor.FIELD_SIZE;
		
		g.setLineWidth(2);
    	
    	g.setStroke(Color.PINK);
		//horizontal
		g.strokeLine(x1 , y1, x1 + image.getWidth(), y1);
		g.strokeLine(x1,  y2, x1 + image.getWidth(), y2);
		//vertical
		g.strokeLine(x1, y1, x1, y2);
		g.strokeLine(x2, y1, x2, y2);
    	
    }
    
    public void moveImage(float currX2, float currY2,MainContent contentManager) {
    	
    	if(currX == currX2 && currY2 == currY)
    		return;
    	
    	
		lastX = currX;
		lastY = currY;
		this.currX = currX2;
		this.currY = currY2;

		
		
		if(contentManager.getCurrPointer() != null)
			contentManager.getCurrPointer().unmark(contentManager.root);
		
		if (this.isMarked()) {
			
			this.update(new Vector2d((currX) / 50f, (currY ) / 50f));
			contentManager.repaint();
		}
	}
    
    private void updatePos() {
		pos.copy(MainContent.CENTER.add(offsetPos));
		updateCorner();
		
	}
    

	public void mark(Group root) {
		isMarked = true;
	}
    
    public void unmark(Group root) {
    	isMarked = false;
	}
    
    public boolean isMarked() {
		return isMarked;
	}

	public String getURL() {
        return url;
    }

	public Vector2d getPos() {
		return pos;
	}

	public void setPos(Vector2d pos) {
		this.pos = pos;
	}
    
	public void setXPos(float x) {
		this.offsetPos.x = x;
		updatePos();
	}
	public void setYPos(float y) {
		this.offsetPos.y = y;
		updatePos();
	}

	public Vector2d getOffsetPos() {
		return offsetPos;
	}

	public void setOffsetPos(Vector2d offsetPos) {
		this.offsetPos = offsetPos;
	}

	@Override
	public Vector2d getLastPos() {

		currX= lastPosition.x *Editor.FIELD_SIZE;
		currY= lastPosition.y *Editor.FIELD_SIZE;
		lastX= lastPosition.x *Editor.FIELD_SIZE;
		lastY= lastPosition.y *Editor.FIELD_SIZE;
		
		return lastPosition;
	}

	@Override
	public void setLastPos(Vector2d pos) {
		this.lastPosition = pos;
	}

	public Image scale(Image source, int targetWidth, int targetHeight, boolean preserveRatio) {
	    ImageView imageView = new ImageView(source);
	    imageView.setPreserveRatio(preserveRatio);
	    imageView.setFitWidth(targetWidth);
	    imageView.setFitHeight(targetHeight);
	    SnapshotParameters parameters = new SnapshotParameters();
	    parameters.setFill(Color.TRANSPARENT);
	    return imageView.snapshot(parameters, null);
	}

    
}