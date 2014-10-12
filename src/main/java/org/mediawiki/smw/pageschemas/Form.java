package org.mediawiki.smw.pageschemas;

import javax.xml.bind.annotation.XmlRootElement;
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
