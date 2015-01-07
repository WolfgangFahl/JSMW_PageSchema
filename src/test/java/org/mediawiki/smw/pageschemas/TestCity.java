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
	  cityPageSchema.setWikiDocumentation("see [https://www.mediawiki.org/wiki/Extension:Page_Schemas#Sample%20XML%20Structure Semantic Page Schemas sample xml structure]\n"+
	   	  "<br>Systematic information on cities (called settlments there) is available on wikipedia\n"+
	  	  "Example for [https://en.wikipedia.org/w/index.php?title=New_York_City New York]: <pre>"+
	  	  "{{Infobox settlement\n" + 
	  	  "| name                    = New York City\n" + 
	  	  "| official_name           = City of New York\n" + 
	  	  "| settlement_type         = [[City (New York)|City]]\n" + 
	  	  "| image_skyline           = NYC Montage 2014 4 - Jleon.jpg\n" + 
	  	  "| image_flag              = Flag of New York City.svg\n" + 
	  	  "..."+
	  	  "| population_total        = 8,405,837\n"+
	  	  "| website                 = [http://www.nyc.gov/ New York City]\n" + 
	  	  "}}</pre>");
	  cityPageSchema.setUmlDocumentation("I represent a City like Berlin, New York or Tokyo");
	  Template cityTemplate = cityPageSchema.getDefaultTemplate();
	  cityTemplate.addField("name","local Name","Text","size=80");
	  cityTemplate.addField("webpage","website","URL","size=120");
	  cityTemplate.addField("Population", "Pop.","Number","size=20,mandatory=");
	  cityTemplate.addField("wikipedia_url", "wikipedia url","URL","size=100,mandatory=");
	  cityTemplate.addLink("Country", "Country","Country","size=50",true);
	  cityTemplate.addLink("Mayor", "Mayor","Mayor","size=80",false);
	  
	  PageSchema mayorPageSchema=new PageSchema(psm,"Mayor");
	  mayorPageSchema.setWikiDocumentation("");
	  mayorPageSchema.setUmlDocumentation("I represent a Mayor like Willi Brandt");
	  Template mayorTemplate = mayorPageSchema.getDefaultTemplate();
	  mayorTemplate.addField("Name", "Name", "Text", "size=80");
	  mayorTemplate.addLink("mayorof", "Mayor of", "City", "size=80",true);
	  
	  PageSchema countryPageSchema=new PageSchema(psm,"Country");
	  countryPageSchema.setUmlDocumentation("I represent a Country like Germany, United States or Japan");
	  // FIXME generate "back"-link to City
	  String wikiDocumentation="systematic information on Countries is available on Wikipedia\nsee also [[:Category:City]]\n";
	  countryPageSchema.setWikiDocumentation(wikiDocumentation);
	  Template countryTemplate=countryPageSchema.getDefaultTemplate();
	  countryTemplate.addField("name","Name", "Text", "size=2");
	  countryTemplate.addField("iso3166_2","Country Code", "Text", "size=2");
	  // update the schemas on the given wiki
		psm.update(this.getWiki());
	}
	
}
