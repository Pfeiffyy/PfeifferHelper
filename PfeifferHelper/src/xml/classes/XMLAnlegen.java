package xml.classes;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class XMLAnlegen {

	static DocumentBuilderFactory docFactory;
	static DocumentBuilder docBuilder;
	static Document doc;
	static Element dat;
	static String[] dats = { "pois.xml", "landschaft.xml", "adressen.xml", "picknick.xml" };
	static String[] objekts = { "Pflanze", "Platz", "POI", "Adresse" };

//	public static void main(String[] args) {
//		XMLAnlegen.makeXMLDateien("Datei", "dat", dats, "Datei.xml");
//		XMLAnlegen.makeXMLDateien("ObjektArt", "obj", objekts, "ObjektArt.xml");
//
//	}





}
