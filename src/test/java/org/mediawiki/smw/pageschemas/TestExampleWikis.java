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
		// add a Form
		Form form = new Form(pageSchema, "ExampleWiki");

		// add a template
		Template template = new Template(form, "ExampleWiki", "standard");

		// add a siteurl field
		Field siteurl = new Field(template, "siteurl", "siteurl");
		siteurl.setFormInput("URL","size=80");
		
		// add a wikiid field
		Field wikiid = new Field(template, "wikid", "wikiId");
		wikiid.setFormInput("Text", "size=40");
		

		// update the schema on the given wiki
		pageSchema.update(this.getWiki());
	}

}
