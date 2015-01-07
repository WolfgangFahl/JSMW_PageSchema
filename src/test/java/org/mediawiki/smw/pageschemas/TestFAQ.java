package org.mediawiki.smw.pageschemas;

import org.junit.Test;

/**
 * test handling the FAQ section
 * 
 * @author wf
 *
 */
public class TestFAQ extends BaseSchemaTest {
  /**
   * test the FAQ schema
   * 
   * @throws Exception
   */
  @Test
  public void testFAQ() throws Exception {
    PageSchemaManager psm = new PageSchemaManager();
    PageSchema faqPageSchema = new PageSchema(psm, "FAQ");

    Template faqTemplate = faqPageSchema.getDefaultTemplate();
    faqTemplate.addField("question", "Question", "Text", "size=5000,mandatory=");
    faqTemplate.addField("askedOn", "asked on", "Date", "size=12");
    faqTemplate.addField("askedBy", "asked by", "Page", "ComboBox", "size=80");
    faqTemplate.addField("answer", "Answer", "Text", "size=5000");

    // FIXME add comment option
    String wikiDocumentation = "The Frequently asked Questions on this site are based on the PageSchema shown below<br>\n"; 
    String exampleSource = "    PageSchemaManager psm = new PageSchemaManager();\n" + 
        "    PageSchema faqPageSchema = new PageSchema(psm, \"FAQ\");\n" + 
        "\n" + 
        "    Template faqTemplate = faqPageSchema.getDefaultTemplate();\n" + 
        "    faqTemplate.addField(\"question\", \"Question\", \"Text\", \"size=5000\");\n" + 
        "    faqTemplate.addField(\"askedOn\", \"asked on\", \"Date\", \"size=12\");\n" + 
        "    faqTemplate.addField(\"askedBy\", \"asked by\", \"Page\", \"ComboBox\", \"size=80\");\n" + 
        "    faqTemplate.addField(\"answer\", \"Answer\", \"Text\", \"size=5000\");\n" + 
        "\n" + 
        "    // FIXME add comment option\n" + 
        "    String wikiDocumentation = \"The Frequently asked Questions on this site are based on the PageSchema shown below<br>\\n\"; \n" + 
        "    String exampleSource = \"\"; // paste source code here\n" + 
        "    faqPageSchema\n" + 
        "        .setWikiDocumentation(wikiDocumentation, exampleSource, \"java\",\"=== Java Source code to generate FAQ Category ===\");\n" + 
        "    faqPageSchema\n" + 
        "        .setUmlDocumentation(\"I represent a Frequently asked question\");\n" + 
        "\n" + 
        "    psm.update(this.getWiki());"; // paste source code here
    faqPageSchema
        .setWikiDocumentation(wikiDocumentation, exampleSource, "java","=== Java Source code to generate FAQ Category ===");
    faqPageSchema
        .setUmlDocumentation("I represent a Frequently asked question");

    psm.update(this.getWiki());
  }
}
