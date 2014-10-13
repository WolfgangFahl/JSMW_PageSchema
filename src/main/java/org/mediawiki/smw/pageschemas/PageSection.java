package org.mediawiki.smw.pageschemas;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

/**
 * a page Section
 * 
 * @author wf
 *
 */
public class PageSection {
	List<Parameter> parameters = new ArrayList<Parameter>();

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
