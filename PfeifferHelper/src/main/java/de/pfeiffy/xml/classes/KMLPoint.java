package de.pfeiffy.xml.classes;

public class KMLPoint {

	String tagname = "Point";
	double latitude;
	double longitude;
	double elevation;

	String coordinatesString;

	public String getTagname() {
		return tagname;
	}

	public void setTagname(String tagname) {
		this.tagname = tagname;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getElevation() {
		return elevation;
	}

	public void setElevation(double elevation) {
		this.elevation = elevation;
	}

	public String getCoordinatesString() {

		coordinatesString = getLongitude() + "," + getLatitude() + "," + getElevation();
		return coordinatesString;
	}

	public void setCoordinatesFromString(String coordinatesString) {
		String[] coordWerte = coordinatesString.split(",");
		setLongitude(Double.parseDouble(coordWerte[0]));
		setLatitude(Double.parseDouble(coordWerte[1]));
		setElevation(Double.parseDouble(coordWerte[2]));

		this.coordinatesString = coordinatesString;
	}

}
