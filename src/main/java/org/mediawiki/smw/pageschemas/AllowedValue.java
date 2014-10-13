package org.mediawiki.smw.pageschemas;

import javax.xml.bind.annotation.XmlValue;

/**
 * an AllowedValue
 * @author wf
 *
 */
public class AllowedValue {
	String value;
	
	/**
	 * default constructor to make JAXB happy
	 */
	public AllowedValue() {
		
	}
	
	/**
	 * construct an allowed Value
	 * @param value - the value to be held
	 */
	public AllowedValue(String value) {
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
