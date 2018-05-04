package content;

import java.util.ArrayList;
import java.util.List;

import exceptions.AnimatorNoContentException;

public class Animator implements Runnable {

	private ArrayList<AnimFrame> frames;
	private int currFrameIndex;
	private float animTime;
	private float totalDuration;
	public 	boolean once;
	public 	static float ANIMATION_DIVISOR = 2000f;
	private long deltaTime = 0;
	private long previousTime = 0;
	private boolean isRunning = true;
	private volatile MainContent mainContent;
	private int 	FPS = 60;
	private float 	frameBuffer = 0f;	
	private float 	FPS_Duration = 1f/(float)FPS;
	
	public Animator(MainContent mainContent)
	{
		this.setMainContent(mainContent);
		init();
	}



	public void init() {
		frames = new ArrayList<AnimFrame>();
		totalDuration = 0f;		
	}
	
	public boolean canBeLoaded(){
		return frames != null && frames.size() > 0;
	}
	

	public synchronized void setup(List<LocatedImage> currentImages, List<Float> currentTimes) throws AnimatorNoContentException
	{
		if(currentImages.isEmpty() || currentTimes.isEmpty())
			throw new AnimatorNoContentException(currentImages, currentTimes);
		
		
		mainContent.console.println("setup wurde aufgerufen");
		if(frames!= null)	frames = new ArrayList<AnimFrame>();
		else 				frames.clear();
		totalDuration = 0f;
		this.once = false;
		start();
		loadFrames(currentImages,currentTimes);
		
		mainContent.setCurrentImage(getImage());
		mainContent.repaint();
		
		mainContent.console.println(frames.size() + "-Bilder geladen");
		
	}
	
	
	private synchronized void loadFrames(List<LocatedImage> currentImages, List<Float> currentTimes)
	{	
		for(int i = 0; i< currentImages.size();i++){
			addFrame(currentImages.get(i), currentTimes.get(i) / 2000f);		
		}	
	}


	public synchronized void start()
	{
		animTime = 0f;
		currFrameIndex = 0;
	}
	
	public synchronized void addFrame(LocatedImage image, float duration)
	{
		totalDuration += duration;
		frames.add(new AnimFrame(image,totalDuration));		
	}
	

	private class AnimFrame
	{
		LocatedImage image;	
		float endTime;
	
		public AnimFrame(LocatedImage image, float endTime){
			this.image = image;
			this.endTime = endTime;
		}
	}
	
	public LocatedImage getImage()
	{
		if(frames.size() == 0)
			return null;				
		else
			return this.getFrame(currFrameIndex).image;
	}
	
	public void update(float delta)
	{
		mainContent.setCurrentImage(getImage());
		
		
		
		if(frames.size() > 1)
			animTime += delta;
		
		
		if(animTime >= totalDuration)
		{
			animTime = 0;
			currFrameIndex = 0;
			mainContent.setCurrentImage(getImage());
			mainContent.repaint();
		//	this.mainContent.getSc().setValue(0);
		}
		
		while(animTime > getFrame(currFrameIndex).endTime)
		{
			currFrameIndex++;
			mainContent.setCurrentImage(getImage());
			mainContent.repaint();
		}

		
		
	}


	private AnimFrame getFrame(int currFrameIndex) 
	{
		return (AnimFrame) frames.get(currFrameIndex);
	}
	
	
	
	
	/**
	 * @return the totalDuration
	 */
	public synchronized float getTotalDuration() {
		return totalDuration;
	}


	/**
	 * @param totalDuration the totalDuration to set
	 */
	public void setTotalDuration(float totalDuration) {
		this.totalDuration = totalDuration;
	}


	/**
	 * @return the animTime
	 */
	public synchronized float getAnimTime() {
		return animTime;
	}



	@Override
	public void run() 
	{
		
		while(isRunning)
		{
			// count fps
			long currentTime = System.nanoTime();
			deltaTime = currentTime - previousTime;
			previousTime = currentTime;
			
			//buffer time must be greater than the 1-FPS time
			frameBuffer+=(float)deltaTime/1000000000f;
			
			
			// perform step when buffer time surpasses the 1-FPS time
			if(frameBuffer>= FPS_Duration)
			{
				float calibrated_deltaTime = FPS_Duration*1000000000f;
				// perform step
				this.update((calibrated_deltaTime/1000000000.0f));
				frameBuffer = 0f;
			}
			
		

			
			
			
		}
		
	}



	public synchronized boolean isRunning() {
		return isRunning;
	}



	public synchronized void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}



	public MainContent getMainContent() {
		return mainContent;
	}



	public void setMainContent(MainContent mainContent) {
		this.mainContent = mainContent;
	}


	
}
