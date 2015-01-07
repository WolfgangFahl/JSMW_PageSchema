package org.mediawiki.smw.pageschemas;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * a parameter
 * @author wf
 *
 */
@XmlRootElement
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
		super(name);
		this.field=field;
		setPageSchema(field.getPageSchema());
		this.value=value;
	}

	
}
