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

import javax.xml.bind.annotation.XmlElement;

public class FormInput extends SchemaItem {

	String inputtype;
	// FIXME use and add test case
	String description;
	String descriptionToolTipMode;

	List<Parameter> parameters = new ArrayList<Parameter>();

	/**
	 * default constructor to make JAXB happy
	 */
	public FormInput() {
	}

	/**
	 * create a new Form Input for the given field
	 * 
	 * @param field
	 * @param inputType
	 * @param paramlist
	 */
	public FormInput(Field field, String inputType, String paramlist) {
		this.setPageSchema(field.getPageSchema());
		setInputType(inputType);
		field.setFormInput(this);
		String[] params = paramlist.split(",");
		for (String paramElement : params) {
			String[] paramElementParts = paramElement.split("=");
			if (paramElementParts.length < 1) {
				LOGGER.log(Level.WARNING, "invalid parameter " + paramElement);
			} else {
				String name = paramElementParts[0];
				String value = "";
				if (paramElementParts.length >= 2) {
					value = paramElementParts[1];
				}
				Parameter parameter = new Parameter(field, name, value);
				parameters.add(parameter);
			}
		}
	}

	/**
	 * @return the type
	 */
	@XmlElement(name = "InputType")
	public String getInputType() {
		return inputtype;
	}

	/**
	 * @param inputtype
	 *          the type to set
	 */
	public void setInputType(String inputtype) {
		this.inputtype = inputtype;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *          the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the parameters
	 */
	@XmlElement(name = "Parameter")
	public List<Parameter> getParameters() {
		return parameters;
	}

	/**
	 * @param parameters
	 *          the parameters to set
	 */
	public void setParameters(List<Parameter> parameters) {
		this.parameters = parameters;
	}

}
