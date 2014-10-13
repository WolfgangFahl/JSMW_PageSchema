package org.mediawiki.smw.pageschemas;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
/**
 * a Section
 * @author wf
 *
 */
public class Section extends SchemaItem {
   int level;
   PageSection pageSection;

	/**
	 * @return the level
	 */
  @XmlAttribute
	public int getLevel() {
		return level;
	}

	/**
	 * @param level the level to set
	 */
	public void setLevel(int level) {
		this.level = level;
	}

	/**
	 * @return the pageSection
	 */
	@XmlElement(name="semanticforms_PageSection")
	public PageSection getPageSection() {
		return pageSection;
	}

	/**
	 * @param pageSection the pageSection to set
	 */
	public void setPageSection(PageSection pageSection) {
		this.pageSection = pageSection;
	}
	
   
}
