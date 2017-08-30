package utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XmlUtils {

	public static Document dom;

	public static void init(String filePath) {
		DocumentBuilder db;
		try {
			db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			dom = db.parse(new File(filePath));
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static ArrayList<Node> getNodes(String name, String type, String value) {
		NodeList nodes = dom.getElementsByTagName(name);
		ArrayList<Node> list = new ArrayList<Node>();
		for (int i = 0; i < nodes.getLength(); i++) {
			if (nodes.item(i).getAttributes().getNamedItem(type).getNodeValue().equals(value)) {
				list.add(nodes.item(i));
			}
		}
		return list;
	}
	
	public static ArrayList<Node> getChildNodes(Node parentNode, String name, String type, String value){
		NodeList nodes = parentNode.getChildNodes();
		ArrayList<Node> list = new ArrayList<Node>();
		for (int i = 0; i < nodes.getLength(); i++) {
			Node node = nodes.item(i);
			if (node.getNodeName().equals(name)&&
				node.getAttributes().getNamedItem(type).getNodeValue().equals(value)) {
				list.add(node);
			}
		}
		return list;
		
	}
	
	
	public static void main(String[] args) {
		init("config\\element.xml");
		Node mainPage = getNodes("page", "name", "mainPage").get(0);
		Node rule = getChildNodes(mainPage, "element", "name", "rule").get(0);
		System.out.println(rule.getAttributes().getNamedItem("value").getNodeValue());
	}
}
