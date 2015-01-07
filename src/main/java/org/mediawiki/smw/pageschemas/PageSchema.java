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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import com.bitplan.mediawiki.japi.MediawikiApi;

/**
 * http://www.mediawiki.org/wiki/Extension:Page_Schemas
 * 
 * @author wf
 */
@XmlRootElement(name = "PageSchema")
@XmlSeeAlso({SchemaItem.class})
// "wikiDocumentation","umlDocumentation",
@XmlType(propOrder = { "value","forms", "templates", "sections" })
public class PageSchema extends SchemaItem {
	private static final String VERSION = "0.0.2";
	
	/**
	 * get the link to JSMW
	 * @return
	 */
	private static final String getJSMW_Link() {
	  String link="[https://github.com/WolfgangFahl/JSMW_PageSchema JSMW_PageSchema Version "+VERSION+"]";
	  return link;
	}

	List<Template> templates = new ArrayList<Template>();
	List<Form> forms = new ArrayList<Form>();
	List<Section> sections = new ArrayList<Section>();

	/**
	 * default constructor to make JAXB happy
	 */
	public PageSchema() {

	}

	/**
	 * create the PageSchema for the given category
	 * 
	 * @param category
	 */
	public PageSchema(String category) {
		this.category = category;
	}

	/**
	 * create a pageSchema and add it to the given manager
	 * 
	 * @param psm
	 * @param category
	 */
	public PageSchema(PageSchemaManager psm, String category) {
		this(category);
		psm.pageSchemas.put(category, this);
	}

	/**
	 * @return the templates
	 */
	@XmlElement(name = "Template")
	public List<Template> getTemplates() {
		return templates;
	}

	/**
	 * @param templates
	 *          the templates to set
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
	 * @param forms
	 *          the forms to set
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
	 * @param sections
	 *          the sections to set
	 */
	public void setSections(List<Section> sections) {
		this.sections = sections;
	}

	/**
	 * create a PageSchema from an XML string
	 * 
	 * @param xml
	 *          - xml representation of Page Schema
	 * @return the PageSchema unmarshalled from the given xml
	 * @throws JAXBException
	 *           if there's something wrong with the xml input
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
	 * update Me on the given wiki must be already logged in
	 * 
	 * @param wiki
	 * @throws Exception
	 */
	public void update(MediawikiApi wiki,List<PageSchema> linkedSchemas ) throws Exception {
		if (this.category == null)
			throw new Exception("the category of the schema must be set!");
		LOGGER.log(Level.INFO, "updating PageSchema for " + this.category + " on "
				+ wiki.getSiteurl());
		String xml = this.asXML();
		String pageTitle = "Category:" + this.category;
		xml = xml.replaceAll("\\<\\?xml(.+?)\\?\\>", "").trim();
		if (debug)
			LOGGER.log(Level.INFO, xml);

		String content = "\n[[Category:PageSchema]]\n"
				+ "This Category has been generated with "+getJSMW_Link()
				+ " at " + wiki.getIsoTimeStamp() + "<br>\n"
				+ "The following results are based on it: \n"
		    + "* [[:Category:" + this.category + "]]<br>\n"
		    + "* [[:Template:" + this.category + "]]<br>\n"
				+ "* [[:Form:" + this.category + "]]<br>\n" + "";

		String text = xml + content + this.wikiDocumentation + "<br>\n";
		for (PageSchema linkedSchema:linkedSchemas) {
			text+="* see also [[:Category:"+linkedSchema.category+"]]\n";
		}
		text+="=== UML diagram for "+this.category+" PageSchema===\n"+this.asPlantUml(linkedSchemas);

		String summary = "modified by JSMW_PageSchema at " + wiki.getIsoTimeStamp();
		// wiki.setDebug(true);
		wiki.edit(pageTitle, text, summary);
	}

	/**
	 * get the copyright
	 * 
	 * @return the copyright
	 */
	public String getCopyright() {
		String result = "Copyright (c) 2015 BITPlan GmbH\n"
				+ "[[http://www.bitplan.com]]";
		return result;
	}

	/**
	 * return me as an uml diagram
	 */
	public String asPlantUml(List<PageSchema> linkedSchemas) {
		String content = getUmlTitle(this.category);
		String note = "";
		content += getUmlNote(category + "DiagramNote", getCopyright() + note);
		String classContent = "";
		for (Template template : this.getTemplates()) {
			classContent += template.getUmlContent();
		}
		content += getUmlClass(this.category, classContent);
		for (PageSchema linkedSchema:linkedSchemas) {
			content+=category+" -- "+linkedSchema.category+"\n";
		}
		content += "hide " + getSpot() + " circle\n";
		String result = super.asPlantUml(content);
		return result;
	}

	public String getSpot() {
		// << (S,#FF7700) Singleton >>
		String spot = " <<Category>>";
		return spot;
	}

	/**
	 * return me as an UML Class
	 * 
	 * @param className
	 * @param classContent
	 * @return
	 */
	@XmlTransient
	protected String getUmlClass(String className, String classContent) {
		// FIXME top of "+className
		// http://plantuml.sourceforge.net/classes.html

		String classNote = getUmlNote(className + "Note ", this.umlDocumentation);
		classNote += className + "Note .." + className + "\n";
		String result = classNote + "Class " + className + getSpot() + " {\n"
				+ classContent + "\n" + "}\n";
		return result;
	}

	/**
	 * get the default Template
	 * 
	 * @return
	 */
	public Template getDefaultTemplate() {
		// add a Form
		Form form = new Form(this, this.category);

		// add a template
		Template template = new Template(form, this.category, "standard");
		return template;
	}

	/**
	 * set the wikiDocumentation appending some example source in the given language
	 * @param wikiDocumentation
	 * @param exampleSource
	 * @param lang
	 * @param title 
	 */
	public void setWikiDocumentation(String wikiDocumentation,
			String exampleSource, String lang, String title) {
	  String header="This example source code works with "+getJSMW_Link()+"\n";
		this.setWikiDocumentation(wikiDocumentation+header+"\n"+title+"\n<br><source lang='"+lang+"'>"+exampleSource+"</source>");
	}

}
