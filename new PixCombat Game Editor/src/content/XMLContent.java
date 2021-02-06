package content;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import math.BoundingRectangle;
import xml.XML_Box_Reader;
import xml.XML_Image_Reader;

public class XMLContent {

	private MainContent mainContent;
	private XMLReader xml_Reader;
	private XML_Image_Reader xml_Image_Reader;
	private XML_Box_Reader xml_Box_Reader;
	
	public XMLContent(MainContent mainContent) throws SAXException 
	{
		this.mainContent = mainContent;
		init();
	}
	
	
	

	private void init() {
		try {
			SAXParserFactory parserFactory = SAXParserFactory.newInstance();
			SAXParser parser = parserFactory.newSAXParser();
			xml_Reader = parser.getXMLReader();
			xml_Image_Reader = new XML_Image_Reader(mainContent);
			xml_Box_Reader = new XML_Box_Reader();
		} catch (SAXException | ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}




	public Map<String, ArrayList<LocatedImage>> loadCharacter(File file) throws Exception {

		Map<String, ArrayList<LocatedImage>> player = new HashMap<String, ArrayList<LocatedImage>>();

		try 
		{
			InputStream stream = new FileInputStream(file.getPath());
			InputSource source = new InputSource(stream);
			init();
			
			xml_Reader.setContentHandler(xml_Image_Reader);
			xml_Reader.parse(source);
			player = xml_Image_Reader.getCharacter();

		} catch (Exception e) {
			throw e;
		}

		return player;
	}
	
	public Map<String,  ArrayList<ArrayList<BoundingRectangle>>> loadBox(File file) {

		Map<String,  ArrayList<ArrayList<BoundingRectangle>>> boxes = new HashMap<String, ArrayList<ArrayList<BoundingRectangle>>>();
		

		try {
			InputStream stream = new FileInputStream(file.getPath());
			InputSource source = new InputSource(stream);

			
			xml_Reader.setContentHandler(xml_Box_Reader);
			xml_Reader.parse(source);

			boxes = xml_Box_Reader.getBoxes();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return boxes;
	}




	public XML_Image_Reader getXml_Image_Reader() {
		return xml_Image_Reader;
	}




	public void setXml_Image_Reader(XML_Image_Reader xml_Image_Reader) {
		this.xml_Image_Reader = xml_Image_Reader;
	}




	public XML_Box_Reader getXml_Box_Reader() {
		return xml_Box_Reader;
	}




	public void setXml_Box_Reader(XML_Box_Reader xml_Box_Reader) {
		this.xml_Box_Reader = xml_Box_Reader;
	}
	
	
	
}
