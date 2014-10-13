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


/**
 * http://www.mediawiki.org/wiki/Extension:Page_Schemas
 * 
 * @author wf
 */
@XmlRootElement(name="PageSchema")
public class PageSchema {
	List<Template> templates=new ArrayList<Template>();
	List<Form> forms=new ArrayList<Form>();
	List<Section> sections=new ArrayList<Section>();

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
}
