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
import java.util.logging.Level;

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
		super(name);
		this.form=form;
		setPageSchema(form.getPageSchema());
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

	class Link {
		String roleName;
		String category;
		Field field;
		boolean mandatory;
	}
	
	/**
	 * add a Link 
	 * @param roleName
	 * @param label
	 * @param paramList
	 * @param mandatory
	 * @return
	 */
	public Link addLink(String roleName,String label,String category,String paramList,boolean mandatory) {
		Link link=new Link();
		link.mandatory=mandatory;
		link.roleName=roleName;
		link.category=category;
		String delim="";
		if (!"".equals(paramList.trim()))  {
			delim=",";
		}
		paramList+=delim+"autocomplete on category="+category;
		delim=",";
		if (mandatory) {
			paramList+=delim+"mandatory=";
		}
		Field field = this.addField(roleName, label, category+"-Page", "combobox", paramList);
		link.field=field;
		return link;
	}
	
	/**
	 * add the given field
	 * @param name
	 * @param label
	 * @param type
	 * @param paramList
	 * @return
	 */
	public Field addField(String name, String label,  String type,
			String paramList) {
		String inputType=""; // and empty input type means: use default;
		Field result=this.addField(name, label, type,inputType,paramList);
		return result;
	}
	
	/**
	 * add a field to this template
	 * @param name
	 * @param label
	 * @param type
	 * @param inputType
	 * @param paramList
	 * @return
	 */
	public Field addField(String name, String label, String type, String inputType,
		String paramList) {
 	  // add a field
		Field field = new Field(this,name,label);
		// FIXME workaround for typed pages combine in a better way with link ...
		if (type.contains("-Page")) {
			String[] parts = type.split("-");
			if (parts.length!=2) {
				LOGGER.log(Level.SEVERE,"invalid type "+type);
			}	else {
				field.category=parts[0];
				type="Page";
			}
		}
		FormInput formInput = new FormInput(field,inputType,paramList);
		// FIXME Template name could be different then type name
		// fix this.name here ...
	  Property property=new Property(this.name+"_"+name,type);
	  field.setProperty(property);

		LOGGER.log(Level.FINE,"created FormInput"+formInput.name);
		return field;
	}
	
	/**
	 * get a list of PageSchemas that are linked via the corresponding "Page" Category;
	 * @return
	 */
	public List<PageSchema> getLinkedPageSchemas(PageSchemaManager pm) {
		List<PageSchema> result=new ArrayList<PageSchema>();
		for (Field field:this.getFields()) {
			if (field.category!=null) {
				PageSchema linkedPageSchema=pm.lookup(field.category);
				if (linkedPageSchema!=null)
					result.add(linkedPageSchema);
			}
		}
		return result;
	}

	
}
