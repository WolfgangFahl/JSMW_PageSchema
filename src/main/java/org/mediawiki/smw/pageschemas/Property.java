package org.mediawiki.smw.pageschemas;

import javax.xml.bind.annotation.XmlElement;

/**
 * a property
 * @author wf
 *
 */
public class Property extends SchemaItem {
	String type;

	/**
	 * default constructor
	 */
	public Property() {};
	
	/**
	 * construct a property for the given namen ane type
	 * @param name
	 * @param type
	 */
	public Property(String name,String type) {
		this.name=name;
		this.type=type;
	}
	
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
