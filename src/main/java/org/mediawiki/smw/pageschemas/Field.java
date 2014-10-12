package org.mediawiki.smw.pageschemas;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
/**
 * a Field
 * @author wf
 *
 */
public class Field extends SchemaItem {
	String label;
  Property property;
  FormInput formInput;
  
	/**
	 * @return the label
	 */
	@XmlElement(name="Label")
	public String getLabel() {
		return label;
	}

	/**
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * @return the property
	 */
	@XmlElement(name="semanticmediawiki_Property")
	public Property getProperty() {
		return property;
	}

	/**
	 * @param property the property to set
	 */
	public void setProperty(Property property) {
		this.property = property;
	}

	/**
	 * @return the formInput
	 */
	@XmlElement(name="semanticforms_FormInput")
	public FormInput getFormInput() {
		return formInput;
	}

	/**
	 * @param formInput the formInput to set
	 */
	public void setFormInput(FormInput formInput) {
		this.formInput = formInput;
	}

	
	

}
