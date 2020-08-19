package xml.classes;

public class OwnTag {
	
	String tagName = "OWNTAG"; 
//    <OWNTAGS>
//    <plant>rose</plant>
//    <high>15.3</high>
//    <vitality>3</vitality>
//	  <pics>fafdhah
//	   <pic>tggjgjh</pic>
//	   <pic>tggjgjh</pic>
//	 </pics>
//   </OWNTAGS>
	

	
	String bemerkung;
	String vitality;
	String kind;
	String qual;
	String volume;
	XmlPics xmlPics;
	public XmlPics getXmlPics() {
		return xmlPics;
	}
	public void setXmlPics(XmlPics xmlPics) {
		this.xmlPics = xmlPics;
	}
	public String getTagName() {
		return tagName;
	}
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	public String getBemerkung() {
		return bemerkung;
	}
	public void setBemerkung(String bemerkung) {
		this.bemerkung = bemerkung;
	}
	public String getVitality() {
		return vitality;
	}
	public void setVitality(String vitality) {
		this.vitality = vitality;
	}
	public String getKind() {
		return kind;
	}
	public void setKind(String kind) {
		this.kind = kind;
	}
	public String getQual() {
		return qual;
	}
	public void setQual(String qual) {
		this.qual = qual;
	}
	public String getVolume() {
		return volume;
	}
	public void setVolume(String volume) {
		this.volume = volume;
	}
	

}
