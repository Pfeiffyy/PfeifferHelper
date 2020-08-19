package de.pfeiffy.xml.classes;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.CDATASection;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XmlKmlTool {
	File file;
	DocumentBuilderFactory docFactory;
	DocumentBuilder docBuilder;
	Document doc;
	CDATASection cDa;

	public XmlKmlTool(File file) {

		try {

			this.file = file;

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<String> getItems(String suchTag) {
		List<String> results = new ArrayList<String>();
		try {

			docFactory = DocumentBuilderFactory.newInstance();
			docBuilder = docFactory.newDocumentBuilder();
			doc = docBuilder.parse(file);

			NodeList nList = doc.getElementsByTagName(suchTag);
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				results.add(nNode.getTextContent());
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return results;
	}

	public List<Node> getNodes(String suchTag) {
		List<Node> results = new ArrayList<Node>();
		try {

			docFactory = DocumentBuilderFactory.newInstance();
			docBuilder = docFactory.newDocumentBuilder();
			doc = docBuilder.parse(file);

			NodeList nList = doc.getElementsByTagName(suchTag);
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				results.add(nNode);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return results;
	}

	public List<KmlObjectPlaceMark> getPlaceMarks(String filter) {
		List<KmlObjectPlaceMark> results = new ArrayList<KmlObjectPlaceMark>();
		try {

			docFactory = DocumentBuilderFactory.newInstance();
			docBuilder = docFactory.newDocumentBuilder();
			doc = docBuilder.parse(file);

			NodeList placemarks = doc.getElementsByTagName("Placemark");
			for (int temp = 0; temp < placemarks.getLength(); temp++) {

				Node nNode = placemarks.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					KmlObjectPlaceMark kmlo = new KmlObjectPlaceMark();

					Node nameNode = getChildNodeByname(nNode, "name");
					if (nameNode != null) {
						kmlo.setName(nameNode.getTextContent());
					}

					Node description = getChildNodeByname(nNode, "description");
					if (description != null) {
						kmlo.setDescription(description.getTextContent());
					}

					Node id = getChildNodeByname(nNode, "id");
					if (id != null) {
						kmlo.setId(id.getTextContent());
					}

					Node time = getChildNodeByname(nNode, "time");
					if (time != null) {
						kmlo.setTime(time.getTextContent());
					}

					// KML-Point
					Node point = getChildNodeByname(nNode, "Point");
					if (point != null) {
						Node coordinates = getChildNodeByname(point, "coordinates");
						KMLPoint kp = new KMLPoint();
						kp.setCoordinatesFromString(coordinates.getTextContent());
						kmlo.setPoint(kp);
					}

					// Owntag
					Node owntag = getChildNodeByname(nNode, "OWNTAG");
					if (owntag != null) {
						OwnTag ow = new OwnTag();

						Node bemerkung = getChildNodeByname(point, "Bemerkung");
						if (bemerkung != null) {
							ow.setBemerkung(bemerkung.getTextContent());
						}

						Node vitality = getChildNodeByname(point, "vitality");
						if (vitality != null) {
							ow.setVitality(vitality.getTextContent());
						}

						Node kind = getChildNodeByname(point, "kind");
						if (kind != null) {
							ow.setKind(kind.getTextContent());
						}

						Node qual = getChildNodeByname(point, "qual");
						if (qual != null) {
							ow.setQual(qual.getTextContent());
						}

						Node volume = getChildNodeByname(point, "volume");
						if (volume != null) {
							ow.setVolume(volume.getTextContent());
						}

						Node picsNode = getChildNodeByname(owntag, "Pics");
						if (picsNode != null) {
							XmlPics xmlPics = new XmlPics();
							NodeList picsNodeList = picsNode.getChildNodes();
							for (int x = 0; x < picsNodeList.getLength(); x++) {
								xmlPics.addPicPaths(picsNodeList.item(x).getTextContent());

							}

							ow.setXmlPics(xmlPics);

						}
						kmlo.setOwntag(ow);
					}

					results.add(kmlo);
				}

			}
		} catch (

		Exception e) {
			e.printStackTrace();
		}
		return results;
	}

	private Node getChildNodeByname(Node nNode, String findName) {
		Node ergNode = null;
		NodeList nodeList = nNode.getChildNodes();
		for (int temp1 = 0; temp1 < nodeList.getLength(); temp1++) {
			Node nNode1 = nodeList.item(temp1);

			if (nNode1.getNodeType() == Node.ELEMENT_NODE) {
				if (nNode1.getNodeName().equals(findName)) {
					ergNode = nNode1;
					break;
				}
			}
		}

		return ergNode;
	}

	public int getFreeID() {
		int max = 0;
		try {

			// docFactory = DocumentBuilderFactory.newInstance();
			// docBuilder = docFactory.newDocumentBuilder();
			// doc = docBuilder.parse(file);
			NodeList nList = doc.getElementsByTagName("id");
			ArrayList<Integer> intList = new ArrayList<Integer>();
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				intList.add(Integer.valueOf(nNode.getTextContent()));
			}
			// Comparator<Integer> comparator = Collections.reverseOrder();
			// Sortieren
			max = Collections.max(intList);

		} catch (Exception e) {
			// TODO: handle exception
		}
		return max + 1;
	}

	public String addPlacemark(KmlObjectPlaceMark kmlObjectPlaceMark) {
		String returnString = "Punkt wurde gespeichert";
		try {

			if (!file.exists()) {
				makeEmptyKml();
			}

			docFactory = DocumentBuilderFactory.newInstance();
			docBuilder = docFactory.newDocumentBuilder();
			doc = docBuilder.parse(file);
			Node document = doc.getElementsByTagName("Document").item(0);
			Element placemark = doc.createElement("Placemark");
			Element id = doc.createElement("id");
			Element name = doc.createElement("name");
			Element point = doc.createElement("Point");
			Element coordinates = doc.createElement("coordinates");
			Element description = doc.createElement("description");

			if (description != null) {
				placemark.appendChild(description);

				cDa = doc.createCDATASection(kmlObjectPlaceMark.getDescription());
				description.appendChild(cDa);
			}
			if (kmlObjectPlaceMark.getName() != null) {
				name.setTextContent(kmlObjectPlaceMark.getName());
				placemark.appendChild(name);
			}
			if (kmlObjectPlaceMark.getPoint().getCoordinatesString() != null) {
				coordinates.setTextContent(kmlObjectPlaceMark.getPoint().getCoordinatesString());
				point.appendChild(coordinates);

			} // description.setTextContent(kmlObjectPlaceMark.getDescription());

			id.setTextContent(getFreeID() + "");
			placemark.appendChild(id);

			if (point != null) {
				placemark.appendChild(point);
			}
			addOwnTag(kmlObjectPlaceMark, doc, placemark);

			document.appendChild(placemark);

			// write the content File
			writeBackXML(doc);

		} catch (Exception e) {

			returnString = "Punkt konnte nicht  gespeichert werden";

		}

		return returnString;

	}

	public String updatePlacemark(KmlObjectPlaceMark kmlObjectPlaceMark) {
		String returnString = "Punkt wurde ge�ndert";
		try {

			if (!file.exists()) {
				makeEmptyKml();
			}

			deletePlacemark(kmlObjectPlaceMark);
			addPlacemark(kmlObjectPlaceMark);

		} catch (Exception e) {

			returnString = "Punkt konnte nicht  egespeichert werden";

		}

		return returnString;

	}

	public String deletePlacemark(KmlObjectPlaceMark kmlObjectPlaceMark) {
		String returnString = "Punkt wurde gespeichert";
		try {

			if (!file.exists()) {
				makeEmptyKml();
			}
			docFactory = DocumentBuilderFactory.newInstance();
			docBuilder = docFactory.newDocumentBuilder();
			doc = docBuilder.parse(file);
			delNodeByID(kmlObjectPlaceMark, doc);

			writeBackXML(doc);

		} catch (Exception e) {
			e.printStackTrace();
			returnString = "Punkt konnte nicht  egespeichert werden";

		}

		return returnString;

	}

	public String makeEmptyKml() {
		String returnString = "Punkt wurde angelegt";

		try {

			// // root element
			docFactory = DocumentBuilderFactory.newInstance();
			docBuilder = docFactory.newDocumentBuilder();
			doc = docBuilder.newDocument();
//			Element rootElement = doc.createElement("kml");
//			rootElement.setAttribute("xmlns", "http://www.opengis.net/kml/2.2");
//			Element document = doc.createElement("Document");
//			rootElement.appendChild(document);
//			//
//			doc.appendChild(rootElement);

			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(file);
			transformer.transform(source, result);

			// Output to console for testing
			StreamResult consoleResult = new StreamResult(System.out);
			transformer.transform(source, consoleResult);
		} catch (Exception e) {
			e.printStackTrace();
			returnString = "Punkt konnte nicht angelegt werden";

		}

		return returnString;

	}

	private void writeBackXML(Document doc)
			throws TransformerFactoryConfigurationError, TransformerConfigurationException, TransformerException {
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		System.out.println("-----------Modified File-----------");
		// StreamResult consoleResult = new StreamResult(System.out);
		StreamResult result = new StreamResult(file);
		transformer.transform(source, result);
	}

	private void delNodeByID(KmlObjectPlaceMark kmlObjectPlaceMark, Document doc) {
		NodeList no = doc.getElementsByTagName("id");
		Node document = doc.getElementsByTagName("Document").item(0);
		for (int x = 0; x < no.getLength(); x++) {

			Node current = no.item(x);
			String id = current.getTextContent();

			if (id.equalsIgnoreCase(kmlObjectPlaceMark.getId())) {
				document.removeChild(current.getParentNode());
			}

		}
	}

	private void addOwnTag(KmlObjectPlaceMark kmlObjectPlaceMark, Document doc, Element placemark) {
		if (kmlObjectPlaceMark.getOwntag() != null) {
			Element owntag = doc.createElement("OWNTAG");
			if (kmlObjectPlaceMark.getOwntag().getBemerkung() != null) {
				Element bemerkung = doc.createElement("Bemerkung");
				bemerkung.setTextContent(kmlObjectPlaceMark.getOwntag().getBemerkung());
				owntag.appendChild(bemerkung);
			}

			if (kmlObjectPlaceMark.getOwntag().getKind() != null) {
				Element kind = doc.createElement("Kind");
				kind.setTextContent(kmlObjectPlaceMark.getOwntag().getKind());
				owntag.appendChild(kind);
			}

			if (kmlObjectPlaceMark.getOwntag().getQual() != null) {
				Element qual = doc.createElement("Qual");
				qual.setTextContent(kmlObjectPlaceMark.getOwntag().getQual());
				owntag.appendChild(qual);
			}

			if (kmlObjectPlaceMark.getOwntag().getVitality() != null) {
				Element vital = doc.createElement("Vitality");
				vital.setTextContent(kmlObjectPlaceMark.getOwntag().getVitality());
				owntag.appendChild(vital);
			}

			if (kmlObjectPlaceMark.getOwntag().getVolume() != null) {
				Element volume = doc.createElement("Volume");
				volume.setTextContent(kmlObjectPlaceMark.getOwntag().getVolume());
				owntag.appendChild(volume);
			}

			if (kmlObjectPlaceMark.getOwntag().getXmlPics() != null) {
				Element pics = doc.createElement("Pics");
				for (int x = 0; x < kmlObjectPlaceMark.getOwntag().getXmlPics().getPicPaths().size(); x++) {
					Element pic = doc.createElement("Pic");
					pic.setTextContent(kmlObjectPlaceMark.getOwntag().getXmlPics().getPicPaths().get(x));
					pics.appendChild(pic);

				}
				owntag.appendChild(pics);
			}

			placemark.appendChild(owntag);
		}
	}

	public void makeSimpleXMLDateien(String rootelement, String tag, String[] tagText) {

		try {

			Element dat;
			docFactory = DocumentBuilderFactory.newInstance();
			docBuilder = docFactory.newDocumentBuilder();
			doc = docBuilder.newDocument();
			Element rootElement = doc.createElement(rootelement);

			for (int i = 0; i < tagText.length; i++) {
				dat = doc.createElement(tag);
				dat.setTextContent(tagText[i]);
				rootElement.appendChild(dat);
			}
			//
			doc.appendChild(rootElement);

			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(file);
			transformer.transform(source, result);

			// Output to console for testing
			StreamResult consoleResult = new StreamResult(System.out);
			transformer.transform(source, consoleResult);
		} catch (Exception e) {
			e.printStackTrace();

		}

	}

}