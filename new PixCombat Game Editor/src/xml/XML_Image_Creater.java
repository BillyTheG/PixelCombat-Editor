package xml;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import content.LocatedImage;
import content.MainContent;
import exceptions.ContentNullException;
import exceptions.SizeNotEqualException;

public class XML_Image_Creater {

	private DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
	private DocumentBuilder docBuilder;
	private Element rootElement;
	private Element type;
	private Element sprites;
	private Document doc;
	private MainContent mainContent;

	private Map<String, ArrayList<LocatedImage>> images = new HashMap<String, ArrayList<LocatedImage>>();

	// Basic Looping
	private Map<String, Boolean> loopBools = new HashMap<String, Boolean>();
	private Map<String, Integer> loopIndices = new HashMap<String, Integer>();

	// Times between Images
	private Map<String, ArrayList<Float>> times = new HashMap<String, ArrayList<Float>>();

	public XML_Image_Creater(MainContent mainContent) {
		this.mainContent = mainContent;
		init();
	}

	public void setVariables(Map<String, ArrayList<LocatedImage>> images, Map<String, Boolean> loopBools, Map<String, Integer> loopIndices, Map<String, ArrayList<Float>> times) {
		this.images = images;
		this.loopBools = loopBools;
		this.loopIndices = loopIndices;
		this.times = times;
	}

	private void init() {
		try {
			docBuilder = docFactory.newDocumentBuilder();
			// root elements
			doc = docBuilder.newDocument();
			rootElement = doc.createElement("character");
			doc.appendChild(rootElement);

			// type of Character
			type = doc.createElement("type");
			rootElement.appendChild(type);

			//
			sprites = doc.createElement("sprites");
			rootElement.appendChild(sprites);

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		}
	}

	public void createXML_Image(String character) throws SizeNotEqualException, ContentNullException {
		Text typeChild = doc.createTextNode(character.substring(0, 1).toUpperCase() + character.substring(1, character.length()));
		type.appendChild(typeChild);

		mainContent.console.println("Creating XML Document for " + typeChild.getTextContent());
		// if we want to create an image xml, we have to consider the order of sequences

		for (Map.Entry<String, ArrayList<LocatedImage>> picSeq : images.entrySet()) {
			// name of picture sequence e.g. stand
			String picSeq_key = picSeq.getKey().toString();

			Element currentPicSeq = doc.createElement(picSeq_key);

			// extract attributes and image sequence

			ArrayList<LocatedImage> currentImages = images.get(picSeq_key);
			ArrayList<Float> currentTimes = times.get(picSeq_key);
			Boolean loopBool = loopBools.get(picSeq_key);
			Integer loopIndex = loopIndices.get(picSeq_key);

			if (currentImages == null || currentTimes == null || loopBool == null || loopIndex == null)
				throw new ContentNullException(picSeq_key);

			if (currentImages.size() != currentTimes.size())
				throw new SizeNotEqualException(currentImages.size(), currentTimes.size());

			currentPicSeq.setAttribute("loops", "" + loopBool);
			currentPicSeq.setAttribute("loopIndex", "" + loopIndex);

			// take previously loaded images with attributes and refill them into the xml file

			for (int j = 0; j < currentImages.size(); j++) {
				LocatedImage image = currentImages.get(j);
				if (image == null) {
					mainContent.console.println("The animation: " + currentPicSeq + " has a null element on position: " + j);
					mainContent.console.println("The insertion of XML Elements will be skipped");
					break;
				}
				int duration = ((int) currentTimes.get(j).floatValue());
				String location = (image == null) ? "" : image.getURL();

				Element current_Image = doc.createElement("img");
				current_Image.setAttribute("key", "" + j);
				current_Image.setAttribute("duration", "" + duration);
				current_Image.setAttribute("x", "" + image.getOffsetPos().x);
				current_Image.setAttribute("y", "" + image.getOffsetPos().y);
				current_Image.setTextContent(location.replace("\n", ""));

				currentPicSeq.appendChild(current_Image);
			}
			sprites.appendChild(currentPicSeq);

		}

		mainContent.console.println("XML Document has been created successfully");
		mainContent.console.println("The XML File will be now created and saved");
		try {
			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("resources/chars_images/" + character.substring(0, 1).toUpperCase() + character.substring(1, character.length()) + ".xml"));

			// Output to console for testing
			// StreamResult result = new StreamResult(System.out);

			transformer.transform(source, result);

			mainContent.console.println("File saved!");

		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		}

		// remove All
		type.removeChild(typeChild);

		while (sprites.getFirstChild() != null) {
			sprites.removeChild(sprites.getFirstChild());
		}

	}



	public static void main(String argv[]) {

	}

}