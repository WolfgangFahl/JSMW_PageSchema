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
		PageSchemaManager psm=new PageSchemaManager();
	  PageSchema cityPageSchema=new PageSchema(psm,"City");
	  cityPageSchema.setWikiDocumentation("see [https://www.mediawiki.org/wiki/Extension:Page_Schemas#Sample%20XML%20Structure Semantic Page Schemas sample xml structure]");
	  cityPageSchema.setUmlDocumentation("I represent a City like Berlin, New York or Tokyo");
	  Template cityTemplate = cityPageSchema.getDefaultTemplate();
	  cityTemplate.addField("name","local Name","Text","size=80");
	  Field pop=cityTemplate.addField("Population", "Pop.","Number","size=20,mandatory=");
	  cityTemplate.addField("Country", "Country","Country-Page","size=50,mandatory=");
	  cityTemplate.addField("Mayor", "Mayor","Mayor-Page","size=80");
	  
	  PageSchema mayorPageSchema=new PageSchema(psm,"Mayor");
	  mayorPageSchema.setWikiDocumentation("");
	  mayorPageSchema.setUmlDocumentation("I represent a Mayor like Willi Brandt");
	  Template mayorTemplate = mayorPageSchema.getDefaultTemplate();
	  mayorTemplate.addField("Name", "Name", "Text", "size=80");
	  mayorTemplate.addField("mayorof", "Mayor of", "City-Page", "size=80");
	  
	  PageSchema countryPageSchema=new PageSchema(psm,"Country");
	  countryPageSchema.setUmlDocumentation("I represent a Country like Germany, United States or Japan");
	  countryPageSchema.setWikiDocumentation("see also [[:Category:City]]");
	  Template countryTemplate=countryPageSchema.getDefaultTemplate();
	  countryTemplate.addField("name","Name", "Text", "size=2");
	  countryTemplate.addField("iso3166_2","Country Code", "Text", "size=2");
	  // update the schemas on the given wiki
		psm.update(this.getWiki());
	}
	
}
