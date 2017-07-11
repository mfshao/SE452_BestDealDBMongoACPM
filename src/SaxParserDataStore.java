import java.io.IOException;
import java.util.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

////////////////////////////////////////////////////////////

/**************
 * 
 * SAX parser use callback function to notify client object of the XML document
 * structure. You should extend DefaultHandler and override the method when
 * parsing the XML document
 * 
 ***************/

////////////////////////////////////////////////////////////

public class SaxParserDataStore extends DefaultHandler {
	SmartPhone smartphone;
	Laptop laptop;
	Tablet tablet;
	TV tv;
	Accessory accessory;
	static HashMap<String, SmartPhone> smartphones;
	static HashMap<String, Laptop> laptops;
	static HashMap<String, Tablet> tablets;
	static HashMap<String, TV> tvs;
	static HashMap<String, Accessory> accessories;
	String xmlFileName;
	HashMap<String,String> accessoryHashMap;
	String elementValueRead;
	String currentElement = "";

	public SaxParserDataStore() {
	}

	public SaxParserDataStore(String xmlFileName) {
		this.xmlFileName = xmlFileName;
		smartphones = new HashMap<String, SmartPhone>();
		laptops = new HashMap<String, Laptop>();
		tablets = new HashMap<String, Tablet>();
		tvs = new HashMap<String, TV>();
		accessories = new HashMap<String, Accessory> ();
		accessoryHashMap = new HashMap<String, String>();
		parseDocument();
	}

	private void parseDocument() {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		try {
			SAXParser parser = factory.newSAXParser();
			parser.parse(xmlFileName, this);
		} catch (ParserConfigurationException e) {
			System.out.println("ParserConfig error");
		} catch (SAXException e) {
			System.out.println("SAXException : xml not well formed");
		} catch (IOException e) {
			System.out.println("IO error");
		}
	}

	////////////////////////////////////////////////////////////

	/*************
	 * 
	 * There are a number of methods to override in SAX handler when parsing
	 * your XML document :
	 * 
	 * Group 1. startDocument() and endDocument() : Methods that are called at
	 * the start and end of an XML document. Group 2. startElement() and
	 * endElement() : Methods that are called at the start and end of a document
	 * element. Group 3. characters() : Method that is called with the text
	 * content in between the start and end tags of an XML document element.
	 * 
	 * 
	 * There are few other methods that you could use for notification for
	 * different purposes, check the API at the following URL:
	 * 
	 * https://docs.oracle.com/javase/7/docs/api/org/xml/sax/helpers/DefaultHandler.html
	 * 
	 ***************/

	////////////////////////////////////////////////////////////

	// when xml start element is parsed store the id into respective hashmap for
	// smartphone,laptops etc
	@Override
	public void startElement(String str1, String str2, String elementName, Attributes attributes) throws SAXException {

		if (elementName.equalsIgnoreCase("smartphone")) {
			currentElement = "smartphone";
			smartphone = new SmartPhone();
			smartphone.setId(attributes.getValue("id"));
		}
		if (elementName.equalsIgnoreCase("tablet")) {
			currentElement = "tablet";
			tablet = new Tablet();
			tablet.setId(attributes.getValue("id"));
		}
		if (elementName.equalsIgnoreCase("laptop")) {
			currentElement = "laptop";
			laptop = new Laptop();
			laptop.setId(attributes.getValue("id"));
		}
		if (elementName.equalsIgnoreCase("tv")) {
			currentElement = "tv";
			tv = new TV();
			tv.setId(attributes.getValue("id"));
		}
		if (elementName.equals("accessory")) {
			currentElement = "accessory";
			accessory = new Accessory();
			accessory.setId(attributes.getValue("id"));
		}
	}

	// when xml end element is parsed store the data into respective hashmap for
	// smartphone,laptops etc respectively
	@Override
	public void endElement(String str1, String str2, String element) throws SAXException {

		if (element.equals("smartphone")) {
			smartphones.put(smartphone.getId(), smartphone);
			return;
		}
		if (element.equals("tablet")) {
			tablets.put(tablet.getId(), tablet);
			return;
		}
		if (element.equals("laptop")) {
			laptops.put(laptop.getId(), laptop);
			return;
		}
		if (element.equals("tv")) {
			tvs.put(tv.getId(), tv);
			return;
		}	
		if (element.equals("accessory")) {
			accessories.put(accessory.getId(),accessory);       
			return; 
        }
		if (element.equals("item")) 
		{
			accessoryHashMap.put(elementValueRead,elementValueRead);
		}
      	if (element.equalsIgnoreCase("accessories")){
      		if(currentElement.equals("smartphone")) {
      			smartphone.setAccessories(accessoryHashMap);
    			accessoryHashMap=new HashMap<String,String>();
    			return;
      		} else if (currentElement.equals("tablet")) {
      			tablet.setAccessories(accessoryHashMap);
    			accessoryHashMap=new HashMap<String,String>();
    			return;
      		} else if (currentElement.equals("tv")) {
      			tv.setAccessories(accessoryHashMap);
    			accessoryHashMap=new HashMap<String,String>();
    			return;
      		} else if (currentElement.equals("laptop")) {
      			laptop.setAccessories(accessoryHashMap);
    			accessoryHashMap=new HashMap<String,String>();
    			return;
      		}
      	}
		
		if (element.equalsIgnoreCase("image")) {
			if (currentElement.equals("smartphone"))
				smartphone.setImage(elementValueRead);
			if (currentElement.equals("laptop"))
				laptop.setImage(elementValueRead);
			if (currentElement.equals("tablet"))
				tablet.setImage(elementValueRead);
			if (currentElement.equals("tv"))
				tv.setImage(elementValueRead);
			if (currentElement.equals("accessory"))
				accessory.setImage(elementValueRead);
			return;
		}

		if (element.equalsIgnoreCase("discount")) {
			if (currentElement.equals("smartphone"))
				smartphone.setDiscount(Double.parseDouble(elementValueRead));
			if (currentElement.equals("laptop"))
				laptop.setDiscount(Double.parseDouble(elementValueRead));
			if (currentElement.equals("tablet"))
				tablet.setDiscount(Double.parseDouble(elementValueRead));
			if (currentElement.equals("tv"))
				tv.setDiscount(Double.parseDouble(elementValueRead));
			if (currentElement.equals("accessory"))
				accessory.setDiscount(Double.parseDouble(elementValueRead));
			return;
		}

		if (element.equalsIgnoreCase("condition")) {
			if (currentElement.equals("smartphone"))
				smartphone.setCondition(elementValueRead);
			if (currentElement.equals("laptop"))
				laptop.setCondition(elementValueRead);
			if (currentElement.equals("tablet"))
				tablet.setCondition(elementValueRead);
			if (currentElement.equals("tv"))
				tv.setCondition(elementValueRead);
			if (currentElement.equals("accessory"))
				accessory.setCondition(elementValueRead);
			return;
		}

		if (element.equalsIgnoreCase("manufacturer")) {
			if (currentElement.equals("smartphone"))
				smartphone.setManufacturer(elementValueRead);
			if (currentElement.equals("laptop"))
				laptop.setManufacturer(elementValueRead);
			if (currentElement.equals("tablet"))
				tablet.setManufacturer(elementValueRead);
			if (currentElement.equals("tv"))
				tv.setManufacturer(elementValueRead);
			if (currentElement.equals("accessory"))
				accessory.setManufacturer(elementValueRead);
			return;
		}

		if (element.equalsIgnoreCase("name")) {
			if (currentElement.equals("smartphone"))
				smartphone.setName(elementValueRead);
			if (currentElement.equals("laptop"))
				laptop.setName(elementValueRead);
			if (currentElement.equals("tablet"))
				tablet.setName(elementValueRead);
			if (currentElement.equals("tv"))
				tv.setName(elementValueRead);
			if (currentElement.equals("accessory"))
				accessory.setName(elementValueRead);
			return;
		}

		if (element.equalsIgnoreCase("price")) {
			if (currentElement.equals("smartphone"))
				smartphone.setPrice(Double.parseDouble(elementValueRead));
			if (currentElement.equals("laptop"))
				laptop.setPrice(Double.parseDouble(elementValueRead));
			if (currentElement.equals("tablet"))
				tablet.setPrice(Double.parseDouble(elementValueRead));
			if (currentElement.equals("tv"))
				tv.setPrice(Double.parseDouble(elementValueRead));
			if (currentElement.equals("accessory"))
				accessory.setPrice(Double.parseDouble(elementValueRead));
			return;
		}
	}

	// get each element in xml tag
	@Override
	public void characters(char[] content, int begin, int end) throws SAXException {
		elementValueRead = new String(content, begin, end);
	}

	/////////////////////////////////////////
	// Kick-Start SAX in main //
	////////////////////////////////////////

	// call the constructor to parse the xml and get product details
	public static void addHashmap() {
		new SaxParserDataStore(Properties.TOMCAT_HOME + Properties.WEBAPP_PATH + Properties.PROJECT_PATH
				+ Properties.PRODUCT_CATALOG_PATH);
	}
	
	public static ArrayList<Product> getAllProducts() {
		ArrayList<Product> productList = new ArrayList<>();
		productList.addAll(smartphones.values());
		productList.addAll(laptops.values());
		productList.addAll(tablets.values());
		productList.addAll(tvs.values());
		productList.addAll(accessories.values());
		return productList;
	}
	
	public static HashMap<String, Product> getAllProductsAsHashMap() {
		HashMap<String, Product> productMap = new HashMap<String, Product>();
		productMap.putAll(smartphones);
		productMap.putAll(laptops);
		productMap.putAll(tablets);
		productMap.putAll(tvs);
		productMap.putAll(accessories);
		return productMap;
	}
}
