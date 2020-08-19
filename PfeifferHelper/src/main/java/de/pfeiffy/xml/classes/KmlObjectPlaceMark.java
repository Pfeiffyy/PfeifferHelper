package de.pfeiffy.xml.classes;

import java.util.ArrayList;
import java.util.List;

public class KmlObjectPlaceMark {

	String tagname = "placemark";
	String name;
	KMLPoint point;
	OwnTag owntag;
	String description;
	String id;
	String time;
	List<KMLPolygon> kmlPolygonList;

	public List<KMLPolygon> getKmlPolygonList() {
		return kmlPolygonList;
	}

	public void addKmlPolygon(KMLPolygon kmlPolygon) {
		if(this.kmlPolygonList==null)
		{
			this.kmlPolygonList = new ArrayList<KMLPolygon>();
		}
		this.kmlPolygonList.add(kmlPolygon);
	}

	public String getTagname() {
		return tagname;
	}

	public void setTagname(String tagname) {
		this.tagname = tagname;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public KMLPoint getPoint() {
		return point;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public void setPoint(KMLPoint point) {
		this.point = point;
	}

	public OwnTag getOwntag() {
		return owntag;
	}

	public void setOwntag(OwnTag owntag) {
		this.owntag = owntag;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		String descript = description;
		if (getOwntag() != null) {
			OwnTag ot = getOwntag();
			descript = description + "/" + ot.getBemerkung() + "/" + ot.getVitality();
		}

		return descript;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
