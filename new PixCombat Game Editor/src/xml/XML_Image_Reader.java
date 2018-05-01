package xml;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

import content.LocatedImage;
import content.MainContent;


public class XML_Image_Reader implements ContentHandler {
	// Zwischenspeicher fuer Inhalt von Elementen
	String ElementContent = null;
	
	//All Images
	private Map<String, ArrayList<LocatedImage>> character = new HashMap<String, ArrayList<LocatedImage>>();
	
	//Basic Looping
	private Map<String, Boolean> loopBools = new HashMap<String, Boolean>();
	private Map<String, Integer> loopIndices = new HashMap<String, Integer>();
	
	//Times between Images
	private Map<String, ArrayList<Float>> times = new HashMap<String, ArrayList<Float>>();
	private ArrayList<Float> time = new ArrayList<Float>();
	
	
	private int loopIndex ;
	private boolean loops;
	private float duration;
	private boolean readingSprites = false;
	private boolean readingAnimation = false;
	private boolean readingIMG = false;
	private int key = 0;
	private String animation = "";
	private MainContent mainContent;
	
	
	public XML_Image_Reader(MainContent mainContent)
	{
		this.mainContent = mainContent;
	}
	
	public Map<String, ArrayList<LocatedImage>> getCharacter() {
		return this.character;
	}

	public static void main(String... args) {

		
	}


	public void setDocumentLocator(Locator locator) {
	}

	public void startDocument() throws SAXException {
		mainContent.console.println();
		mainContent.console.println();
		mainContent.console.println("Starte Charakter parsen");
	}

	public void endDocument() throws SAXException {
		mainContent.console.println("Ende Charakter parsen");
		mainContent.console.println();
		mainContent.console.println();
	}

	public void startPrefixMapping(String prefix, String uri)
			throws SAXException {
	}

	public void endPrefixMapping(String prefix) throws SAXException {
	}

	public void startElement(String uri, String localName, String qName,
			Attributes atts) throws SAXException {
		if (localName.equals("sprites") && readingSprites == false) {
			readingSprites = true;
		} else if (readingSprites == true) {
			if (readingAnimation == false) {
				readingAnimation = true;
				//starting with an animation
				
				//pick up the meta-info
				loopIndex = Integer.parseInt(atts.getValue("loopIndex"));
				loops =  getVal(atts.getValue("loops").toString());
									
				time = new ArrayList<Float>();						
				character.put(localName, new ArrayList<LocatedImage>());				
				animation = localName;
				times.put(animation, time);
				this.loopBools.put(animation, loops);
				this.loopIndices.put(animation,loopIndex);	
							
			} else {
				key = Integer.parseInt(atts.getValue("key"));
				duration = Float.parseFloat(atts.getValue("duration"));
				time.add(duration);
				readingIMG = true;
			}
		}
	}

	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if (readingSprites == true) {
			if (readingAnimation == true) {
				if (readingIMG == true) {
					readingIMG = false;
					try {
						character.get(animation).add(key,
								loadImage(ElementContent));
					} catch (IndexOutOfBoundsException e) {
						mainContent.console
								.println("IndexOutOfBoundsException while parsing character... CHECK KEYS!");
					}
					mainContent.console.println("Loaded image key " + key + " from "
							+ ElementContent);
					mainContent.console.println("Duration: " + duration + "ms");
					
				} else {
					readingAnimation = false;
					mainContent.console.println("Loaded animation " + localName
							+ " with " + character.get(localName).size()
							+ " images");
					mainContent.console.println("Loops: " + loops );
					if(loops)
						mainContent.console.println("Loop Index: " + loopIndex );
					
					mainContent.console.println();
					
				}
			} else {
				readingSprites = false;
			}
		}
	}
	
	private boolean getVal(String value) {
		if(value.equals("true"))
			return true;
		else
			return false;		
	}

	public void characters(char[] ch, int start, int length)
			throws SAXException {
		ElementContent = new String(ch, start, length);
	}

	public void ignorableWhitespace(char[] ch, int start, int length)
			throws SAXException {
	}

	public void processingInstruction(String target, String data)
			throws SAXException {
	}

	public void skippedEntity(String name) throws SAXException {
	}

	public LocatedImage loadImage(String url) {
		try {
			LocatedImage img = new LocatedImage(url);
			System.out.println("picture found");
			return img;
		} catch (Exception e) {
			System.out.println("no picture found");
			return null;
		}
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

	

}
