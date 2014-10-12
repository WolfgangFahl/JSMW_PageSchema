package org.mediawiki.smw.pageschemas;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

public class FormInput extends SchemaItem {
	String inputtype;
  List<Parameter> parameters=new ArrayList<Parameter>();

	/**
	 * @return the type
	 */
	@XmlElement(name="InputType")
	public String getInputType() {
		return inputtype;
	}

	/**
	 * @param type the type to set
	 */
	public void setInputType(String inputtype) {
		this.inputtype = inputtype;
	}
	
	/**
	 * @return the parameters
	 */
	@XmlElement(name="Parameter")
	public List<Parameter> getParameters() {
		return parameters;
	}

	/**
	 * @param parameters the parameters to set
	 */
	public void setParameters(List<Parameter> parameters) {
		this.parameters = parameters;
	}
	

}
