package org.mediawiki.smw.pageschemas;

import javax.xml.bind.annotation.XmlValue;

public class Parameter extends SchemaItem {
	String value;

	/**
	 * default constructor to make JAXB happy
	 */
	public Parameter() {};
	
	/**
	 * name value constructor for a parameter
	 * @param name
	 * @param value
	 */
	public Parameter(String name, String value) {
		this.name=name;
		this.value=value;
	}

	/**
	 * @return the value
	 */
	@XmlValue
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
	
}
