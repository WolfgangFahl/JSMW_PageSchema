package org.mediawiki.smw.pageschemas;

import javax.xml.bind.annotation.XmlAttribute;

/**
 * base class for Semantic Page Schema Items
 * @author wf
 *
 */
public class SchemaItem {
	String name;

	/**
	 * @return the name
	 */
	@XmlAttribute
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
}
