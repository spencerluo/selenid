package utils;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.openqa.selenium.By;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XmlUtils {

	public static Document dom;

	public static void init() throws ParserConfigurationException, Exception, IOException {
		DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		dom = db.parse(new File("config\\element.xml"));
	}

	public static Node getNode(String name, String type, String value) {
		NodeList pages = dom.getElementsByTagName(name);
		for (int i = 0; i < pages.getLength(); i++) {
			if (pages.item(i).getAttributes().getNamedItem(type).getNodeValue().equals(value)) {
				return pages.item(i);
			}
		}
		return null;
	}
	
	
}
