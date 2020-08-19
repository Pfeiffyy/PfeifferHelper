package xml;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import xml.classes.KMLPoint;
import xml.classes.KMLPolygon;
import xml.classes.KmlObjectPlaceMark;
import xml.classes.OwnTag;
import xml.classes.XMLAnlegen;
import xml.classes.XmlKmlTool;
import xml.classes.XmlPics;

import org.w3c.dom.Node;

public class TestXML {

	static File dateiXML = new File("Datei.xml");
	static File objektXML = new File("ObjektArt.xml");
	static File geometriedatei = new File("Goemetrien.txt");

	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		// new File("x1.xml").delete();

		// List<String> list = new ArrayList<String>();
		// XmlKmlTool xmlK = new XmlKmlTool(dateiXML);
		// list = xmlK.getItems("dat");
		//
		// list = new ArrayList<String>();
		// xmlK = new XmlKmlTool(objektXML);
		// list = xmlK.getItems("obj");
		//
		// System.out.println("--------------------");

		// int zz = modXML.getFreeID();

		// String[] dats = { "pois.xml", "landschaft.xml", "adressen.xml",
		// "picknick.xml" };
		// String[] objekts = { "Pflanze", "Platz", "POI", "Adresse" };
		//
		// XmlKmlTool modXML = new XmlKmlTool(new File("Datei.xml"));
		// modXML.makeSimpleXMLDateien("Datei", "dat", dats);
		//
		// XmlKmlTool modXML1 = new XmlKmlTool(new File("ObjektArt.xml"));
		// modXML1.makeSimpleXMLDateien("ObjektArt", "obj", objekts);
		//
		// }
		//
		// }
		XmlKmlTool modXML = new XmlKmlTool(new File("NewPolygon.xml"));
		//
		// List<KmlObjectPlaceMark> klmList = modXML.getPlaceMarks("");
		//
		// for (int i = 0; i < klmList.size(); i++) {
		// KmlObjectPlaceMark km = (KmlObjectPlaceMark) klmList.get(i);
		// if (km != null) {
		// System.out.println(" km.getDescription(): " + km.getDescription());
		// System.out.println(" km.getId(): " + km.getId());
		// System.out.println(" km.getName(): " + km.getName());
		// System.out.println(" km.getTime(): " + km.getTime());
		// }
		// KMLPoint p = km.getPoint();
		// if (p != null) {
		// System.out.println(" p.getCoordinatesString(): " + p.getCoordinatesString());
		// System.out.println(" p.getElevation(): " + p.getElevation());
		// System.out.println(" p.getLatitude(): " + p.getLatitude());
		// System.out.println(" p.getLongitude(): " + p.getLongitude());
		// }
		//
		// OwnTag o = km.getOwntag();
		// if (o != null) {
		// System.out.println(" o.getBemerkung(): " + o.getBemerkung());
		// System.out.println(" o.getKind(): " + o.getKind());
		// System.out.println(" o.getQual(): " + o.getQual());
		// System.out.println(" o.getVitality(): " + o.getVitality());
		// System.out.println(" o.getVolume(): " + o.getVolume());
		//
		// if (o.getXmlPics() != null) {
		// for (int x = 0; x < o.getXmlPics().getPicPaths().size(); x++) {
		// System.out.println(
		// " o.getXmlPics().getPicPaths().get(x): " +
		// o.getXmlPics().getPicPaths().get(x));
		//
		// }
		// }
		// System.out.println("------------------------------------------------------");
		//
		// }
		//
		// }
		// }
		// }
		// }

		KmlObjectPlaceMark kmlObjectPlaceMark = new KmlObjectPlaceMark();
		// PolyGon
		kmlObjectPlaceMark.setName("TestPolyGon 1");

		KMLPolygon kmlPolygon = new KMLPolygon();
		kmlPolygon.setExtrude(1);
		kmlPolygon.setAltitudeMode("relativeToGround");

		FileReader fr = new FileReader(geometriedatei);
		BufferedReader br = new BufferedReader(fr);
		//
		List<String> coordinatesPGList = new ArrayList<String>();

		String zeile = "";
		//
		try {
			while ((zeile = br.readLine()) != null) {
				System.out.println(zeile);
				String[] geos = zeile.substring(zeile.indexOf("((") + 2, zeile.indexOf("))")).split(",");
				System.out.println(" geo: " + geos);
				for (int i = 0; i < geos.length; i++) {
					String[] coords = geos[i].split(" ");
					coordinatesPGList.add(coords[0] + "," + coords[1] + ",0");
				}

			}
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		kmlPolygon.setCoordinatesPG(coordinatesPGList);
		
		kmlObjectPlaceMark.addKmlPolygon(kmlPolygon);

		// coordinatesPGList.add(arg0)

		// kmlObj.setDescription("Erster Versuch mit Objekt");
		// // kmlObj.setId("1111");
		// kmlObj.setName("Punkt 899");
		// // //
		// KMLPoint kmlPoint = new KMLPoint();
		// kmlPoint.setLongitude(11.4);
		// kmlPoint.setLatitude(4.7);
		// kmlPoint.setElevation(320);
		// kmlObj.setPoint(kmlPoint);
		// kmlObj.setDescription("Beeschreibung");
		//
		// XmlPics pics = new XmlPics();
		// pics.addPicPaths("/rree/zu1.png");
		// pics.addPicPaths("/rree/ghh1j.png");
		// pics.addPicPaths("/rree/fh1fgfg.png");
		//
		// OwnTag ot = new OwnTag();
		// ot.setBemerkung("Das ist eine Bemerkung 11199");
		// ot.setKind("Strauch 1");
		// ot.setVitality("10");
		// ot.setXmlPics(pics);
		// kmlObj.setOwntag(ot);
		//
		modXML.addPlacemark(kmlObjectPlaceMark);
	}
}
// //
// //// List<String> items = modXML.getItems("coordinates");
// //// for (int i = 0; i < items.size(); i++) {
// ////
// //// System.out.println(items.get(i));
// ////
// //// }
// //
// // // modXML.updatePlacemark(kmlObj);
// //
// // kmlObj.setId("82");
// // kmlObj.setName("Punkt zwaiunafzich");
// // modXML.updatePlacemark(kmlObj);
// // //
// //
// //// kmlObj.setId("81");
// //// modXML.deletePlacemark(kmlObj);
//
// }
//
// }
