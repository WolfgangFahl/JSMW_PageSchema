package org.mediawiki.smw.pageschemas;

import org.junit.Test;

/**
 * see <a href=
 * 'http://mediawiki-japi.bitplan.com/mediawiki-japi/index.php/ExampleWikis'>ExampleWikis</a
 * >
 * 
 * @author wf
 *
 */
public class TestExampleWikis extends BaseSchemaTest {

	@Test
	public void testCreateExampleWikisSchema() throws Exception {
		PageSchemaManager psm=new PageSchemaManager();
		
		PageSchema exampleWikiPageSchema = new PageSchema(psm,"ExampleWiki");
		exampleWikiPageSchema.setUmlDocumentation("ExampleWikis are testCases for mediawiki-japi");
		exampleWikiPageSchema.setWikiDocumentation("see [[ExampleWikis]]");
			// add a template
		Template template = exampleWikiPageSchema.getDefaultTemplate();
		template.addField("siteurl", "siteurl","URL","size=80,mandatory=");
		template.addField("wikid", "wikiId","Text", "size=40,mandatory=");
		template.addField("mwversion", "Version","Text", "size=15");
		template.addField("mwlogo", "Logo","URL", "size=100");
		
		PageSchema examplePagePageSchema = new PageSchema(psm,"ExamplePage");
		examplePagePageSchema.setUmlDocumentation("examplePages are testPages for mediawiki-japi");
		examplePagePageSchema.setWikiDocumentation("see [[Category:ExamplePage]]");
			// add a template
		template = examplePagePageSchema.getDefaultTemplate();
		template.addField("content", "Content","text","size=80");
		template.addField("page", "Page","Page", "size=40");
		template.addLink("wiki", "wiki","ExampleWiki", "size=40",true);
		template.addField("forEdit", "write access","Boolean", "size=15");	
			
		// update the schemas on the given wiki
		psm.update(this.getWiki());
	}

}
