package xml;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import xml.classes.KmlObjectPlaceMark;

public class ModifyXML {
	File file;

	public ModifyXML(File File) {
		this.file = file;
	}

	public String addPlacemark(KmlObjectPlaceMark kmlObjectPlaceMark) {
		String returnString = "Punkt wurd egespeichert";
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(file);
			Node document = doc.getElementsByTagName("Document").item(0);

			Element placemark = doc.createElement("placemark");
			Element name = doc.createElement("name");
			Element point = doc.createElement("Point");
			Element coordinates = doc.createElement("coordinates");
			Element description = doc.createElement("description");

			name.setTextContent(kmlObjectPlaceMark.getName());
			coordinates.setTextContent(kmlObjectPlaceMark.getPoint().getCoordinatesString());
			description.setTextContent(kmlObjectPlaceMark.getDescription());

			point.appendChild(coordinates);
			placemark.appendChild(name);
			placemark.appendChild(point);
			placemark.appendChild(description);
			document.appendChild(placemark);

			// write the content File
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			System.out.println("-----------Modified File-----------");
			// StreamResult consoleResult = new StreamResult(System.out);
			StreamResult result = new StreamResult(file);
			transformer.transform(source, result);

		} catch (Exception e) {

			returnString = "Punkt konnte nicht  egespeichert werden";

		}

		return returnString;

	}

	public static void main(String argv[]) {
		CreateXML.main(argv);

		try {
			File file = new File("out.xml");
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(file);
			Node document = doc.getElementsByTagName("Document").item(0);

			Element placemark = doc.createElement("Placemark");
			Element name = doc.createElement("name");
			Element point = doc.createElement("Point");
			Element coordinates = doc.createElement("coordinates");
			Element description = doc.createElement("description");

			name.setTextContent("Punkt 1");
			coordinates.setTextContent("8,48,5");
			description.setTextContent("Das ist ein Punkt");

			point.appendChild(coordinates);
			placemark.appendChild(name);
			placemark.appendChild(point);
			placemark.appendChild(description);
			document.appendChild(placemark);

			// write the content File
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			System.out.println("-----------Modified File-----------");
			// StreamResult consoleResult = new StreamResult(System.out);
			StreamResult result = new StreamResult(file);
			transformer.transform(source, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}