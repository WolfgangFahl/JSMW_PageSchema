package org.mediawiki.smw.pageschemas;

import static org.junit.Assert.*;

import java.util.List;

import javax.xml.bind.JAXBException;

import org.junit.Test;

/**
 * JUnit test for 
 * @author wf
 *
 */
public class TestXML {
	
	@Test
	public void testPageSchema2XML() throws JAXBException {
		PageSchema schema=new PageSchema();
		// add a Form
		Form form = new Form();
		form.setName("City");
		schema.forms.add(form);
		
		// add a template
		Template template=new Template();
		template.setName("City");
		template.setFormat("standard");
    schema.templates.add(template);
    
    // add a field
    Field field=new Field();
    field.setName("Population");
    field.setLabel("Pop.");
    template.getFields().add(field);
    
    FormInput fi=new FormInput();
    fi.setInputType("text");
    fi.parameters.add(new Parameter("size","20"));
    field.setFormInput(fi);
    
		String xml=schema.asXML();
		System.out.println(xml);
	}

	@Test
	public void testXML2PageSchema() throws JAXBException {
		String xmls[]={"<PageSchema>\n" + 
				"	<semanticforms_Form name=\"City\">\n" + 
				"	<standardInputs/>\n" + 
				"	</semanticforms_Form>\n" + 
				"	<Template name=\"City\" format=\"standard\">\n" + 
				"		<Field name=\"Population\">\n" + 
				"			<Label>Pop.</Label>\n" + 
				"			<semanticmediawiki_Property name=\"Has population\">\n" + 
				"				<Type>Number</Type>\n" + 
				"			</semanticmediawiki_Property>	\n" + 
				"			<semanticforms_FormInput>\n" + 
				"				<InputType>text</InputType>\n" + 
				"				<Parameter name=\"size\">20</Parameter>\n" + 
				"				<Parameter name=\"mandatory\"/>\n" + 
				"			</semanticforms_FormInput>\n" + 
				"		</Field>\n" + 
				"	</Template>\n" + 
				"	<Section name=\"History\" level=\"2\">\n" + 
				"		<semanticforms_PageSection>\n" + 
				"			<Parameter name=\"rows\">10</Parameter>\n" + 
				"			<Parameter name=\"mandatory\"/>\n" + 
				"		</semanticforms_PageSection>\n" + 
				"	</Section>\n" + 
				"</PageSchema>"};
		for (String xml:xmls) {
			PageSchema pageschema = PageSchema.fromXML(xml);
			assertNotNull(pageschema);
			List<Template> templates = pageschema.getTemplates();
			assertEquals(1,templates.size());
			
			List<Form> forms=pageschema.getForms();
			assertEquals(1,forms.size());
      
			Template template=templates.get(0);
			List<Field> fields = template.getFields();
			assertEquals(1,fields.size());
			Field field = fields.get(0);
			assertEquals("Population",field.getName());
			assertEquals("Pop.",field.getLabel());

			assertEquals("Has population",field.getProperty().getName());
			assertEquals("Number",field.getProperty().getType());
			
			FormInput formInput = field.getFormInput();
			assertEquals("text",formInput.getInputType());
			
			List<Parameter> params = formInput.getParameters();
			assertEquals(2,params.size());
			assertEquals("size",params.get(0).getName());
			assertEquals("20",params.get(0).getValue());
			assertEquals("mandatory",params.get(1).getName());
			assertEquals("",params.get(1).getValue());
		}
	}

}