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
	  Field pop=cityTemplate.addField("Population", "Pop.","Number","size=20,mandatory=");
	  cityTemplate.addField("Country", "Country","Page","size=50,mandatory=");
	  cityTemplate.addField("Mayor", "Mayor","Page","size=80");
	  cityPageSchema.update(this.getWiki());
	  
	  PageSchema countryPageSchema=new PageSchema("Country");
	  countryPageSchema.setUmlDocumentation("I represent a Country like Germany, United States or Japan");
	  countryPageSchema.setWikiDocumentation("see also [[:Category:City]]");
	  Template countryTemplate=countryPageSchema.getDefaultTemplate();
	  countryTemplate.addField("name","Name", "Text", "size=2");
	  countryTemplate.addField("iso3166_2","Country Code", "Text", "size=2");
		// update the schema on the given wiki
		countryPageSchema.update(this.getWiki());
	}
	
}
