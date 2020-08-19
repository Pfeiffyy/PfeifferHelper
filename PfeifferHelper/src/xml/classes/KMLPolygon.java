package xml.classes;

import java.util.List;

public class KMLPolygon {
	
	int extrude;
	String altitudeMode;
	List <String> coordinatesPG;
	public int getExtrude() {
		return extrude;
	}
	public void setExtrude(int extrude) {
		this.extrude = extrude;
	}
	public String getAltitudeMode() {
		return altitudeMode;
	}
	public void setAltitudeMode(String altitudeMode) {
		this.altitudeMode = altitudeMode;
	}
	public List<String> getCoordinatesPG() {
		return coordinatesPG;
	}
	public void setCoordinatesPG(List<String> coordinatesPG) {
		this.coordinatesPG = coordinatesPG;
	}
}
