package de.pfeiffy.help;

import java.util.HashSet;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLHelper {

	public static List<String> duplikateLoeschen(List<String> liste) {
		HashSet<String> hashSet = new HashSet<String>(liste);
		liste.clear();
		liste.addAll(hashSet);
		return liste;
	}

	public static Node getFirstNodeByName(Node nnode, String nodeName) {
		Node node = null;
		NodeList nList = nnode.getChildNodes();
		for (int x = 0; x < nList.getLength(); x++) {

			if (nList.item(x).getNodeName().equalsIgnoreCase(nodeName)) {
				node = nList.item(x);
				break;
			}

		}
		return node;

	}

	public static Node getFirstNodeByName(Document doc, String nodeName) {
		Node node = null;
		NodeList nList = doc.getChildNodes();
		for (int x = 0; x < nList.getLength(); x++) {

			if (nList.item(x).getNodeName().equalsIgnoreCase(nodeName)) {
				node = nList.item(x);
				break;
			}

		}
		return node;

	}

	
	
	public static Node getFirstNodeByNameRecursiv(final Node nnode,final  String nodeName) {

		Node node = null;
		NodeList nList = nnode.getChildNodes();

		for (int x = 0; (node == null) && x < nList.getLength(); x++) {
			Node checkNode=nList.item(x);
			System.out.println(checkNode.getNodeName());

			if (checkNode.getNodeName().equalsIgnoreCase(nodeName)) {
				node = checkNode;
			} else {
				node = getFirstNodeByNameRecursiv(checkNode, nodeName);
			}
		}
		return node;

	}
	public static Node getFirstNodeByNameRecursiv(Document doc,final  String nodeName) {

		Node node = null;
		NodeList nList = doc.getChildNodes();

		for (int x = 0; (node == null) && x < nList.getLength(); x++) {
			Node checkNode=nList.item(x);
			System.out.println(checkNode.getNodeName());

			if (checkNode.getNodeName().equalsIgnoreCase(nodeName)) {
				node = checkNode;
			} else {
				node = getFirstNodeByNameRecursiv(checkNode, nodeName);
			}
		}
		return node;

	}

}
