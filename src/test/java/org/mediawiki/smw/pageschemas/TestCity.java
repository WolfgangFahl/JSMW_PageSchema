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

import org.junit.Test;

/**
 * Test Cities example
 * @author wf
 *
 */
public class TestCity extends BaseSchemaTest {
	@Test
	public void testCitiesPageSchema() throws Exception {
	  PageSchema cityPageSchema=new PageSchema("City");
	  cityPageSchema.setWikiDocumentation("see [https://www.mediawiki.org/wiki/Extension:Page_Schemas#Sample%20XML%20Structure Semantic Page Schemas sample xml structure]");
	  cityPageSchema.setUmlDocumentation("I represent a City like Berlin, New York or Tokyo");
	  Template cityTemplate = cityPageSchema.getDefaultTemplate();
	  cityTemplate.addField("Population", "Pop.","Number","size=20,mandatory=");
		// update the schema on the given wiki
		cityPageSchema.update(this.getWiki());
	}
	
}
