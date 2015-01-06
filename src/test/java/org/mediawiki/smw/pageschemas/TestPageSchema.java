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
		PageSchemaManager psm=new PageSchemaManager();
		PageSchema pageSchema = new PageSchema(psm,"PageSchema");
		pageSchema.setWikiDocumentation("see https://www.mediawiki.org/wiki/Extension:Page_Schemas");
		pageSchema.setUmlDocumentation("This is the master class of the PageSchema hierarchy. It has 1:n relations to forms, templates and sections");
		
			// add a template
		Template template = pageSchema.getDefaultTemplate();
		// add a field
		template.addField("category","category","Page","size=80");
		
		PageSchema formPageSchema = new PageSchema(psm,"Form");
		Template formTemplate=formPageSchema.getDefaultTemplate();
		formTemplate.addField("createtitle","create Title","Text","size=80");
		formTemplate.addField("edittitle","edit Title","Text","size=80");
		formTemplate.addField("pageschema","Schema","PageSchema-Page","size=80");

		PageSchema formInputPageSchema = new PageSchema(psm,"FormInput");
		formInputPageSchema.setWikiDocumentation("the inputType can be any of [https://semantic-mediawiki.org/wiki/Help:List_of_datatypes Help:List_of_datatypes]");
	  Template formInputTemplate=formInputPageSchema.getDefaultTemplate();
	  formInputTemplate.addField("field", "Form", "Form-Page", "size=80");
	  
		PageSchema fieldPageSchema = new PageSchema(psm,"Field");
		fieldPageSchema.setUmlDocumentation("a container for single piece of data");
		fieldPageSchema.setWikiDocumentation("the name of the field will also be a property name so please make sure you follow the rules for [https://www.mediawiki.org/wiki/Manual:Page_title page titles]");
		Template fieldTemplate=fieldPageSchema.getDefaultTemplate();
		fieldTemplate.addField("name","Name","Text","size=80");
		fieldTemplate.addField("label","Label","Text","size=80");
		fieldTemplate.addField("form","Form","Page","size=80");
	  fieldTemplate.addField("formInput", "FormInput", "FormInput-Page", "size=80");
	  
	  // create Schemas
		psm.update(this.getWiki());
	}
	
}
