package org.mediawiki.smw.pageschemas;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

/**
 * a property
 * @author wf
 *
 */
public class Property extends SchemaItem {
	String type;
	String linkedForm;

	List<AllowedValue> allowedValues=new ArrayList<AllowedValue>();

	/**
	 * default constructor
	 */
	public Property() {};
	
	/**
	 * construct a property for the given namen ane type
	 * @param name
	 * @param type
	 */
	public Property(String name,String type) {
		this.name=name;
		this.type=type;
	}
	
	/**
	 * @return the type
	 */
	@XmlElement(name="Type")
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	
	/**
	 * @return the linkedForm
	 */
	public String getLinkedForm() {
		return linkedForm;
	}

	/**
	 * @param linkedForm the linkedForm to set
	 */
	public void setLinkedForm(String linkedForm) {
		this.linkedForm = linkedForm;
	}

	/**
	 * @return the allowedValues
	 */
	@XmlElement(name="AllowedValue")
	public List<AllowedValue> getAllowedValues() {
		return allowedValues;
	}

	/**
	 * @param allowedValues the allowedValues to set
	 */
	public void setAllowedValues(List<AllowedValue> allowedValues) {
		this.allowedValues = allowedValues;
	}
	
	

}
