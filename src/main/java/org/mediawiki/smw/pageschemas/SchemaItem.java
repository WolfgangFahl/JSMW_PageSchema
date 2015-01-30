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

import java.io.StringWriter;
import java.util.logging.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlValue;

/**
 * base class for Semantic Page Schema Items
 * 
 * @author wf
 *
 */
@XmlRootElement
@XmlTransient
// http://blog.bdoughan.com/2011/06/using-jaxbs-xmlaccessortype-to.html
@XmlAccessorType(XmlAccessType.NONE)
public abstract class SchemaItem {
	protected boolean debug = false;
	protected static Logger LOGGER = Logger
			.getLogger("org.mediawiki.smw.pageschemas");

	// back link to my pageSchema
	@XmlTransient
	PageSchema pageSchema;

	String pluralName;
	String name;
	String value;
	
	// FIXME - ask Yaron for this extra field
	@XmlAttribute
	String category;

	// FIXME - ask Yaron to add these
	String wikiDocumentation = "";
	String umlDocumentation = "";

	/**
   * @return the category
   */
  public String getCategory() {
    return category;
  }

  /**
   * @param category the category to set
   */
  public void setCategory(String category) {
    this.category = category;
  }

  /**
	 * default constructor to make java and jaxb happy
	 */
	public SchemaItem() {
		
	}
	
	/**
	 * construct me with the given name
	 * @param name
	 */
	public SchemaItem(String name) {
		this.name=name;
		this.pluralName=name+"s";
	}

	/**
	 * @return the wikiDocumentation
	 */
	//@XmlElement
	public String getWikiDocumentation() {
		return wikiDocumentation;
	}

	/**
	 * @param wikiDocumentation
	 *          the wikiDocumentation to set
	 */
	public void setWikiDocumentation(String wikiDocumentation) {
		this.wikiDocumentation = wikiDocumentation;
	}

	/**
	 * @return the umlDocumentation
	 */
	//@XmlElement
	public String getUmlDocumentation() {
		return umlDocumentation;
	}

	/**
	 * @param umlDocumentation
	 *          the umlDocumentation to set
	 */
	public void setUmlDocumentation(String umlDocumentation) {
		this.umlDocumentation = umlDocumentation;
	}

	/**
	 * get my PageSchema
	 * 
	 * @return my pageSchema
	 */
	@XmlTransient
	protected PageSchema getPageSchema() {
		return pageSchema;
	}

	/**
	 * set my PageSchema
	 * 
	 * @param pageSchema
	 *          - the PageSchema to set for me
	 */
	protected void setPageSchema(PageSchema pageSchema) {
		this.pageSchema = pageSchema;
	}

	/**
	 * @return the pluralName
	 */
	@XmlAttribute
	public String getPluralName() {
		return pluralName;
	}

	/**
	 * @param pluralName the pluralName to set
	 */
	public void setPluralName(String pluralName) {
		this.pluralName = pluralName;
	}

	/**
	 * @return the name
	 */
	@XmlAttribute
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *          the name to set
	 */
	public void setName(String name) {
		this.name = name;
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

	/**
	 * get an XML representation of this Schema
	 * 
	 * @return xml string
	 * @throws JAXBException
	 */
	public String asXML() throws JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance(this.getClass());
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		// output pretty printed
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		StringWriter sw = new StringWriter();
		jaxbMarshaller.marshal(this, sw);
		String result = sw.toString();
		return result;
	}

	/**
	 * return me as Wiki content
	 * 
	 * @return
	 * @throws JAXBException
	 */
	public String asWikiContent() throws JAXBException {
		String xml = this.asXML();
		return xml;
	}

	/**
	 * get an UML title
	 * 
	 * @param title
	 * @return
	 */
	protected String getUmlTitle(String title) {
		String result = "\ntitle " + title + "\n";
		return result;
	}

	/**
	 * get an UML note with the given noteTitle and noteContent
	 * 
	 * @param noteTitle
	 * @param noteContent
	 * @return the UmlNote
	 */
	public String getUmlNote(String noteTitle, String noteContent) {
		String result = "note as " + noteTitle + "\n" + noteContent + "\n"
				+ "end note\n";
		return result;
	}

	/**
	 * get my local uml content
	 * 
	 * @return
	 */
	public// abstract
	String getUmlContent() {
		return "";
	}

}
