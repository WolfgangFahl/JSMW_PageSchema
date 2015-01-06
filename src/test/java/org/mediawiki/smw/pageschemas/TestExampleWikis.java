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
		PageSchema pageSchema = new PageSchema("ExampleWiki");
		pageSchema.setUmlDocumentation("ExampleWikis are testCases for mediawiki-japi");
		pageSchema.setWikiDocumentation("see [[ExampleWikis]]");
			// add a template
		Template template = pageSchema.getDefaultTemplate();
		template.addField("siteurl", "siteurl","URL","size=80");
		template.addField("wikid", "wikiId","Text", "size=40");
		template.addField("mwversion", "Version","Text", "size=15");
		template.addField("mwlogo", "Logo","URL", "size=100");
		
		// update the schema on the given wiki
		pageSchema.update(this.getWiki());
	}

}
