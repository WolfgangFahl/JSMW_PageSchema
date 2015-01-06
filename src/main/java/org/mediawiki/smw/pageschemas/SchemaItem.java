package org.mediawiki.smw.pageschemas;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;

/**
 * base class for Semantic Page Schema Items
 * @author wf
 *
 */
public class SchemaItem {
  // back link to my pageSchema
	@XmlTransient
	PageSchema pageSchema;
	String name;
	
	/**
	 * get my PageSchema 
	 * @return my pageSchema
	 */
	protected PageSchema getPageSchema() {
		return pageSchema;
	}
	
	/**
	 * set my PageSchema
	 * @param pageSchema - the PageSchema to set for me
	 */
	protected void setPageSchema(PageSchema pageSchema) {
		this.pageSchema=pageSchema;
	}

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
