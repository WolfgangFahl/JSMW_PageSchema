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
 * test the bootstrapping of PageSchema
 * @author wf
 *
 */
public class TestPageSchema extends BaseSchemaTest {
	@Test
	public void testCreatePageSchema_for_PageSchema() throws Exception {
		PageSchema pageSchema = new PageSchema("PageSchema");
		pageSchema.setWikiDocumentation("see https://www.mediawiki.org/wiki/Extension:Page_Schemas");
		pageSchema.setUmlDocumentation("This is the master class of the PageSchema hierarchy. It has 1:n relations to forms, templates and sections");
		
		// add a Form
		Form form = new Form(pageSchema,"PageSchema");
	
		// add a template
		Template template = new Template(form,"PageSchema","standard");

		// add a field
		Field field = new Field(template,"category","category");
		FormInput formInput = new FormInput(field,"Page","size=80");
		
		// update the schema on the given wiki
		pageSchema.update(this.getWiki());

		PageSchema formPageSchema = new PageSchema("Form");
		Template formTemplate=formPageSchema.getDefaultTemplate();
		formTemplate.addField("createtitle","create Title","Text","size=80");
		formTemplate.addField("edittitle","edit Title","Text","size=80");
		formTemplate.addField("pageschema","Schema","Page","size=80");
		formPageSchema.update(this.getWiki());

		PageSchema formInputPageSchema = new PageSchema("FormInput");
		formInputPageSchema.setWikiDocumentation("the inputType can be any of [https://semantic-mediawiki.org/wiki/Help:List_of_datatypes Help:List_of_datatypes]");
	  Template formInputTemplate=formInputPageSchema.getDefaultTemplate();
	  formInputTemplate.addField("field", "Form", "Page", "size=80");
	  formInputPageSchema.update(this.getWiki());
	  
		PageSchema fieldPageSchema = new PageSchema("Field");
		fieldPageSchema.setUmlDocumentation("a container for single piece of data");
		fieldPageSchema.setWikiDocumentation("the name of the field will also be a property name so please make sure you follow the rules for [https://www.mediawiki.org/wiki/Manual:Page_title page titles]");
		Template fieldTemplate=fieldPageSchema.getDefaultTemplate();
		fieldTemplate.addField("name","Name","Text","size=80");
		fieldTemplate.addField("label","Label","Text","size=80");
		fieldTemplate.addField("form","Form","Page","size=80");
	  formTemplate.addField("formInput", "FormInput", "Page", "size=80");
		fieldPageSchema.update(this.getWiki());
	}
	
}
