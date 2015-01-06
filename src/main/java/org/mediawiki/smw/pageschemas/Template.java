/**
 * Copyright (C) 2014-2015 BITPlan GmbH
 *
 * Pater-Delp-Str. 1
 * D-47877 Willich-Schiefbahn
 *
 * http://www.bitplan.com
 * 
 * This source is part of
 * https://github.com/WolfgangFahl/JSMW_PageSchema
 * and the license for JSMW_PageSchema applies
 * 
 */
package org.mediawiki.smw.pageschemas;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
/**
 * Template class
 * @author wf
 *
 */
public class Template extends SchemaItem {
	@XmlTransient Form form;
	String multiple;
	String format;
	List<Field> fields=new ArrayList<Field>();

	/**
	 * Default constructor to make Jaxb happy
	 */
	public Template() {
	}
	
	/**
	 * create me with the given name and format and add me to the given form's schema
	 * @param form
	 * @param name
	 * @param format
	 */
	public Template(Form form, String name, String format) {
		this.form=form;
		setPageSchema(form.getPageSchema());
		setName(name);
		setFormat(format);
		getPageSchema().templates.add(this);
	}

	/**
	 * @return the multiple
	 */
	@XmlAttribute
	public String getMultiple() {
		return multiple;
	}
	
	/**
	 * @param multiple the multiple to set
	 */
	public void setMultiple(String multiple) {
		this.multiple = multiple;
	}
	
	
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

	/**
	 * add a field to this template
	 * @param name
	 * @param label
	 * @param inputType
	 * @param paramList
	 * @return
	 */
	public Field addField(String name, String label, String inputType,
		String paramList) {
 	  // add a field
		Field field = new Field(this,"category","category");
		FormInput formInput = new FormInput(field,"Page","size=80");
		return field;
	}

	
}
