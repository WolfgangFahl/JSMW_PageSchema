package org.mediawiki.smw.pageschemas;

import javax.xml.bind.annotation.XmlElement;

public class Property extends SchemaItem {
	String type;

	/**
	 * @return the type
	 */
	@XmlElement(name="Type")
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	
	

}
