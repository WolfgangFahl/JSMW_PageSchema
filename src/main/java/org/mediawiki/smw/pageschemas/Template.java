package org.mediawiki.smw.pageschemas;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
/**
 * Template class
 * @author wf
 *
 */
public class Template extends SchemaItem {
	String format;
	List<Field> fields=new ArrayList<Field>();

	
	/**
	 * @return the format
	 */
	@XmlAttribute
	public String getFormat() {
		return format;
	}
	/**
	 * @param format the format to set
	 */
	public void setFormat(String format) {
		this.format = format;
	}
	
	/**
	 * @return the fields
	 */
	@XmlElement(name = "Field")
	public List<Field> getFields() {
		return fields;
	}
	/**
	 * @param fields the fields to set
	 */
	public void setFields(List<Field> fields) {
		this.fields = fields;
	}
	
	
}
