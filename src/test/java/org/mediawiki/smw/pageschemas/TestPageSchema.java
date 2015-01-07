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
		formTemplate.addLink("pageschema","Schema","PageSchema","size=80",true);
		
		PageSchema templatePageSchema = new PageSchema(psm,"Template");
		Template templateTemplate=templatePageSchema.getDefaultTemplate();
		templateTemplate.addField("multiple","multiple","Text","size=80");
		templateTemplate.addField("format","format","Text","size=80");
		templateTemplate.addLink("pageschema","Schema","PageSchema","size=80",true);
		
		templateTemplate.setWikiDocumentation("see https://semantic-mediawiki.org/wiki/Help:Semantic_templates");
		templateTemplate.setUmlDocumentation("I am a template and have a list of fields");

		PageSchema formInputPageSchema = new PageSchema(psm,"FormInput");
	  Template formInputTemplate=formInputPageSchema.getDefaultTemplate();
	  // FIXME add allowed values here 
		formInputTemplate.addField("inputType","inputType","Text","size=80");
	  formInputTemplate.addLink("field", "Field", "Field", "size=80",true);

		formInputPageSchema.setWikiDocumentation("the inputType can be according to <br>see [https://www.mediawiki.org/wiki/Extension:Semantic_Forms/Defining_forms#Allowed_input_types_for_data_types allowed input types] or <br>any of [https://www.mediawiki.org/wiki/Extension:Semantic_Forms_Inputs Semantic Form Inputs]");
		formInputPageSchema.setUmlDocumentation("I am a userinterface control to add input for a template field");
		
		PageSchema propertyPageSchema = new PageSchema(psm,"Property");
	  Template propertyTemplate=propertyPageSchema.getDefaultTemplate();
	  // FIXME add allowed values here and make it a drop down box
		propertyTemplate.addField("type","type","Text","size=80");
	  propertyTemplate.addLink("formInput", "formInput", "FormInput", "size=80",true);

		propertyPageSchema.setWikiDocumentation("the type can be any of [https://semantic-mediawiki.org/wiki/Help:List_of_datatypes Help:List_of_datatypes]");
	  
		PageSchema fieldPageSchema = new PageSchema(psm,"Field");
		Template fieldTemplate=fieldPageSchema.getDefaultTemplate();
		fieldTemplate.addField("name","Name","Text","size=80");
		fieldTemplate.addField("label","Label","Text","size=80");
		fieldTemplate.addLink("form","Form","Form","size=80",true);
	  fieldTemplate.addLink("template", "Template", "Template", "size=80",true);
	  fieldTemplate.addLink("formInput", "FormInput", "FormInput", "size=80",true);

		fieldPageSchema.setUmlDocumentation("a container for a single piece of data");
		fieldPageSchema.setWikiDocumentation("the name of the field will also be used to derive a property name so please make sure you follow the rules for [https://www.mediawiki.org/wiki/Manual:Page_title page titles]");

	  // create Schemas
		psm.update(this.getWiki());
	}
	
}
