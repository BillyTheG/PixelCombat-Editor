package xml;

import java.io.File;
import java.util.ArrayList;
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
import org.w3c.dom.Node;
import org.w3c.dom.Text;

import content.MainContent;
import exceptions.ContentNullException;
import math.BoundingRectangle;

public class XML_Box_Creater {

	private DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
	private DocumentBuilder docBuilder;
	private Element rootElement;
	private Element type;
	private Element boxes;
	private Document doc;
	private MainContent mainContent;

	public XML_Box_Creater(MainContent mainContent) {
		this.mainContent = mainContent;
		init();
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
			boxes = doc.createElement("boxes");
			rootElement.appendChild(boxes);

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		}
	}

	public void createXML_Box(String character, Map<String, ArrayList<ArrayList<BoundingRectangle>>> list_list_Boxes) throws ContentNullException {
		Text typeChild = doc.createTextNode(character.substring(0,1).toUpperCase()+character.substring(1, character.length()));
		type.appendChild(typeChild);

		for (Map.Entry<String, ArrayList<ArrayList<BoundingRectangle>>> picSeq : list_list_Boxes.entrySet()) {
			// Name der Bildsequenz z.B. stand
			String picSeq_key = picSeq.getKey().toString();
			
			if(picSeq_key== null)
				throw new ContentNullException(picSeq_key);
			
			// diesen als Attribut in das Dok einfügen
			Element currentPicSeq = doc.createElement(picSeq_key);
			// Boxes herausnehmen
			ArrayList<ArrayList<BoundingRectangle>> boxes_currentPicSeq = list_list_Boxes.get(picSeq_key);
			// Liste von Boxen in Bild i reinspeichern als Format: <id = 0>

			for (int i = 0; i < boxes_currentPicSeq.size(); i++) {
				ArrayList<BoundingRectangle> currentBoxes_i = boxes_currentPicSeq.get(i);
				Element currentBoxes_i_element = doc.createElement("box_list");
				currentBoxes_i_element.setAttribute("picture", "" + i);
				// Liste von Boxen für ein Bild
				for (int j = 0; j < currentBoxes_i.size(); j++) {
					BoundingRectangle currentBox = currentBoxes_i.get(j);
					Element currentBox_element = doc.createElement("box");
					currentBox_element.setAttribute("id", "" + j);
					currentBox_element.setAttribute("x", "" + (currentBox.getPos().x - MainContent.CENTER.x));
					currentBox_element.setAttribute("y", "" + (currentBox.getPos().y - MainContent.CENTER.y));
					currentBox_element.setAttribute("height", "" + currentBox.getHeight());
					currentBox_element.setAttribute("width", "" + currentBox.getWidth());
					currentBox_element.setAttribute("hurts", "" + currentBox.getHurts());
					currentBoxes_i_element.appendChild(currentBox_element);
				}
				currentPicSeq.appendChild(currentBoxes_i_element);
			}
			boxes.appendChild(currentPicSeq);

		}

		try {
			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("resources/chars_boxes/" + character.substring(0,1).toUpperCase()+character.substring(1, character.length()) +".xml"));

			// Output to console for testing
			// StreamResult result = new StreamResult(System.out);

			transformer.transform(source, result);

			mainContent.console.println("File saved!");

		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		}
		
		//remove All
		type.removeChild(typeChild);
		
		int size = boxes.getChildNodes().getLength();
		
		for(int i = 0; i< size; i++){
			Node element = boxes.getChildNodes().item(i);
			if(element != null)
				boxes.removeChild(element);
		}
		
		
		
	}

	

	public static void main(String argv[]) 
	{

	
	}
		
}