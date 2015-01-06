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

import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.bitplan.mediawiki.japi.MediawikiApi;


/**
 * http://www.mediawiki.org/wiki/Extension:Page_Schemas
 * 
 * @author wf
 */
@XmlRootElement(name="PageSchema")
@XmlType(propOrder = { "forms", "templates", "sections" })
public class PageSchema {
	// FIXME - ask Yaron for this extra field 
	String category;
	
	List<Template> templates=new ArrayList<Template>();
	List<Form> forms=new ArrayList<Form>();
	List<Section> sections=new ArrayList<Section>();

	public PageSchema() {
		
	}
	
	/**
	 * create the PageSchema for the given category
	 * @param category
	 */
	public PageSchema(String category) {
		this.category=category;
	}

	/**
	 * @return the templates
	 */
	@XmlElement(name = "Template")
	public List<Template> getTemplates() {
		return templates;
	}

	/**
	 * @param templates the templates to set
	 */
	public void setTemplates(List<Template> templates) {
		this.templates = templates;
	}

	@XmlElement(name = "semanticforms_Form")
	/**
	 * @return the forms
	 */
	public List<Form> getForms() {
		return forms;
	}

	/**
	 * @param forms the forms to set
	 */
	public void setForms(List<Form> forms) {
		this.forms = forms;
	}

	/**
	 * @return the sections
	 */
	@XmlElement(name = "Section")
	public List<Section> getSections() {
		return sections;
	}

	/**
	 * @param sections the sections to set
	 */
	public void setSections(List<Section> sections) {
		this.sections = sections;
	}

	/**
	 * create a PageSchema from an XML string
	 * 
	 * @param xml - xml representation of Page Schema
	 * @return the PageSchema unmarshalled from the given xml
	 * @throws JAXBException  if there's something wrong with the xml input
	 */
	public static PageSchema fromXML(final String xml) throws JAXBException {
		// unmarshal the xml message to the format to a W3CValidator Java object
		JAXBContext context = JAXBContext.newInstance(PageSchema.class);
		Unmarshaller u = context.createUnmarshaller();
		StringReader xmlReader = new StringReader(xml);
		// this step will convert from xml text to Java Object
		PageSchema result = (PageSchema) u.unmarshal(xmlReader);
		return result;
	}

	/**
	 * get an XML representation of this Schema
	 * @return xml string
	 * @throws JAXBException 
	 */
	public String asXML() throws JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance(PageSchema.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		// output pretty printed
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		StringWriter sw = new StringWriter();
		jaxbMarshaller.marshal(this, sw);
		String result=sw.toString();
		return result;
	}

	/**
	 * update Me on the given wiki
	 * must be already logged in 
	 * @param wiki
	 * @throws Exception 
	 */
	public void update(MediawikiApi wiki) throws Exception {
		if (this.category==null)
			throw new Exception("the category of the schema must be set!");
		String xml=this.asXML();
		String pageTitle="Category:"+this.category;
		xml=xml.replaceAll("\\<\\?xml(.+?)\\?\\>", "").trim();
		String text=xml+"\n[[Category:PageSchema]]";
		String summary="modified by JSMW_PageSchema at "+wiki.getIsoTimeStamp();
		wiki.edit(pageTitle, text, summary);
	}

	/**
	 * get the default Template
	 * @return
	 */
	public Template getDefaultTemplate() {
		// add a Form
		Form form = new Form(this,this.category);
	
		// add a template
		Template template = new Template(form,this.category,"standard");
		return template;
	}
}
