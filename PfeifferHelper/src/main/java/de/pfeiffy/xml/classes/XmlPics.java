package de.pfeiffy.xml.classes;

import java.util.ArrayList;

public class XmlPics {
	ArrayList<String> picPaths = new ArrayList<String>();

	public ArrayList<String> getPicPaths() {
		return picPaths;
	}

	public void addPicPaths(String picPath) {

		this.picPaths.add(picPath);
	}

}
