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

import javax.xml.bind.annotation.XmlElement;

/**
 * a Form in a PageSchema
 * @author wf
 *
 */
public class Form extends SchemaItem {
	
	String createTitle;
	String editTitle;
	
	/**
	 * default constructor to make JAXB happy
	 */
	public Form() {
	}

	/**
	 * create a form with the given title and add it to the given schema
	 * @param pageSchema
	 * @param title
	 */
	public Form(PageSchema pageSchema, String title) {
		super(title);
		setPageSchema(pageSchema);
		getPageSchema().forms.add(this);
	}
	
	

	/**
	 * @return the createTitle
	 */
	@XmlElement(name="CreateTitle")
	public String getCreateTitle() {
		return createTitle;
	}
	/**
	 * @param createTitle the createTitle to set
	 */
	public void setCreateTitle(String createTitle) {
		this.createTitle = createTitle;
	}
	/**
	 * @return the editTitle
	 */
	@XmlElement(name="EditTitle")
	public String getEditTitle() {
		return editTitle;
	}
	/**
	 * @param editTitle the editTitle to set
	 */
	public void setEditTitle(String editTitle) {
		this.editTitle = editTitle;
	}

}
