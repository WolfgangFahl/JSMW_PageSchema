package org.mediawiki.smw.pageschemas;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * a Field
 * 
 * @author wf
 *
 */
public class Field extends SchemaItem {
	@XmlTransient
	Template template;

	String label;
	Property property;
	FormInput formInput;

	/**
	 * default constructor to make JAXB happy
	 */
	public Field() {
	}

	/**
	 * create a new Field with the given name and label for the given template and
	 * add it to the given template
	 * 
	 * @param template
	 * @param name
	 * @param label
	 */
	public Field(Template template, String name, String label) {
		super(name);
		this.template = template;
		this.setPageSchema(template.getPageSchema());
		setLabel(label);
		template.getFields().add(this);
	}

	/**
	 * @return the label
	 */
	@XmlElement(name = "Label")
	public String getLabel() {
	  if (name!=null && label==null)
	    return name;
		return label;
	}

	/**
	 * @param label
	 *          the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * @return the property
	 */
	@XmlElement(name = "semanticmediawiki_Property")
	public Property getProperty() {
		return property;
	}

	/**
	 * @param property
	 *          the property to set
	 */
	public void setProperty(Property property) {
		this.property = property;
	}

	/**
	 * @return the formInput
	 */
	@XmlElement(name = "semanticforms_FormInput")
	public FormInput getFormInput() {
		return formInput;
	}

	/**
	 * @param formInput
	 *          the formInput to set
	 */
	public void setFormInput(FormInput formInput) {
		this.formInput = formInput;
	}

	/**
	 * set the given Form input
	 * 
	 * @param inputType
	 * @param paramlist
	 */
	public FormInput setFormInput(String inputType, String paramlist) {
		FormInput formInput = new FormInput(this, inputType, paramlist);
		return formInput;
	}

}
