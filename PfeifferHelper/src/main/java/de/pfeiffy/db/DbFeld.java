package de.pfeiffy.db;

public class DbFeld {

	String feld;
	String value;
	int format;

	public DbFeld(String feld, String value, int format) {
		this.feld = feld;
		this.value = value;
		this.format = format;
	}

	public String getFeld() {
		return feld;
	}

	public void setFeld(String feld) {
		this.feld = feld;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int getFormat() {
		return format;
	}

	public void setFormat(int format) {
		this.format = format;
	}
}
