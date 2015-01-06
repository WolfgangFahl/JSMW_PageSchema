package org.mediawiki.smw.pageschemas;

import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlValue;

/**
 * a parameter
 * @author wf
 *
 */
public class Parameter extends SchemaItem {
	@XmlTransient Field field;
	
	

	/**
	 * default constructor to make JAXB happy
	 */
	public Parameter() {};
	
	/**
	 * name value constructor for a parameter
	 * @param field 
	 * @param name
	 * @param value
	 */
	public Parameter(Field field, String name, String value) {
		this.field=field;
		setPageSchema(field.getPageSchema());
		this.name=name;
		this.value=value;
	}

	
}
