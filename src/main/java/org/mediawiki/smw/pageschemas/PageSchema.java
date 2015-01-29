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

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import com.bitplan.mediawiki.japi.MediawikiApi;
import com.bitplan.rest.freemarker.FreeMarkerConfiguration;

/**
 * http://www.mediawiki.org/wiki/Extension:Page_Schemas
 * 
 * @author wf
 */
@XmlRootElement(name = "PageSchema")
@XmlSeeAlso({ SchemaItem.class })
// "wikiDocumentation","umlDocumentation",
@XmlType(propOrder = { "value", "forms", "templates", "sections" })
public class PageSchema extends SchemaItem {
  private static final String VERSION = "0.0.2";

  /**
   * get the link to JSMW
   * 
   * @return
   */
  private static final String getJSMW_Link() {
    String link = "[https://github.com/WolfgangFahl/JSMW_PageSchema JSMW_PageSchema Version "
        + VERSION + "]";
    return link;
  }

  @XmlTransient
  protected String source;
  @XmlTransient
  protected String sourceTitle;
  @XmlTransient
  protected String lang;

  List<Template> templates = new ArrayList<Template>();
  List<Form> forms = new ArrayList<Form>();
  List<Section> sections = new ArrayList<Section>();
  @XmlTransient
  private PageSchemaManager pageSchemaManager;

  /**
   * default constructor to make JAXB happy
   */
  public PageSchema() {

  }

  /**
   * create the PageSchema for the given category
   * 
   * @param category
   */
  public PageSchema(String category) {
    super(category);
    this.category = category;
    this.name = category;
  }

  /**
   * create a pageSchema and add it to the given manager
   * 
   * @param psm
   * @param category
   */
  public PageSchema(PageSchemaManager psm, String category) {
    this(category);
    psm.pageSchemas.put(category, this);
    this.pageSchemaManager = psm;
  }

  /**
   * @return the templates
   */
  @XmlElement(name = "Template")
  public List<Template> getTemplates() {
    return templates;
  }

  /**
   * @param templates
   *          the templates to set
   */
  public void setTemplates(List<Template> templates) {
    this.templates = templates;
  }

  @XmlElement(name = "semanticforms_Form")
  /**
   * @return the forms
   */
  public List<Form> getForms() {
    return forms;
  }

  /**
   * @param forms
   *          the forms to set
   */
  public void setForms(List<Form> forms) {
    this.forms = forms;
  }

  /**
   * @return the sections
   */
  @XmlElement(name = "Section")
  public List<Section> getSections() {
    return sections;
  }

  /**
   * @param sections
   *          the sections to set
   */
  public void setSections(List<Section> sections) {
    this.sections = sections;
  }

  /**
   * create a PageSchema from an XML string
   * 
   * @param xml
   *          - xml representation of Page Schema
   * @return the PageSchema unmarshalled from the given xml
   * @throws JAXBException
   *           if there's something wrong with the xml input
   */
  public static PageSchema fromXML(final String xml) throws JAXBException {
    // unmarshal the xml message to the format to a W3CValidator Java object
    JAXBContext context = JAXBContext.newInstance(PageSchema.class);
    Unmarshaller u = context.createUnmarshaller();
    StringReader xmlReader = new StringReader(xml);
    // this step will convert from xml text to Java Object
    PageSchema result = (PageSchema) u.unmarshal(xmlReader);
    return result;
  }

  /**
   * Utility class for the Concept and ListPage
   * 
   * @author wf
   *
   */
  public class ConceptPage {
    PageSchema pageSchema;
    String listPageTitle;
    String conceptPageTitle;
    Field linkField;
    private String queryfields;
    List<Property> properties = new ArrayList<Property>();

    /**
     * @return the pageSchema
     */
    public PageSchema getPageSchema() {
      return pageSchema;
    }

    /**
     * @param pageSchema the pageSchema to set
     */
    public void setPageSchema(PageSchema pageSchema) {
      this.pageSchema = pageSchema;
    }

    /**
     * a List Page
     * 
     * @param pageSchema
     */
    public ConceptPage(PageSchema pageSchema) {
      this.pageSchema = pageSchema;
      init();
    }

    /**
     * update a conceptPage using the given wiki, pageTitle and freeMarker Termplate
     * @param wiki
     * @throws Exception 
     */
    public void updateConceptPage(MediawikiApi wiki, String pageTitle,String freeMarkerTemplateName) throws Exception {
      Map<String, Object> rootMap = new HashMap<String, Object>();
      rootMap.put("conceptPage", this);
      // tell Freemarker to use main class path and therefore find templates in
      // main/resources/templates
      FreeMarkerConfiguration.addTemplateClass(PageSchema.class,
          "/templates");
      // make sure the template is found
      String wikiPage = FreeMarkerConfiguration.doProcessTemplate(
          freeMarkerTemplateName, rootMap);
      // System.out.println(wikiPage);
      String summary = getGenerationTimeStamp(wiki);
      LOGGER.log(
          Level.INFO,
          "updating " + pageTitle + " on " + wiki.getSiteurl()
              + wiki.getScriptPath());
      ;
      wiki.edit(pageTitle, wikiPage, summary);
    }

    /**
     * check whether this list Page is available
     * 
     * @return
     */
    public boolean isAvailable() {
      boolean result = linkField != null;
      return result;
    }

    /**
     * initialize me
     */
    public void init() {
      // create list of Concept Pages
      listPageTitle = "List of " + pageSchema.getPluralName();
      conceptPageTitle = "Concept:" + pageSchema.getName();
      linkField = null; // field to link Page to categories -
      // query expects non null value (pseudo-primary key ...)
      // could be null after the following search if no mandatory field is
      // specified
      queryfields = "";
      for (Template template : pageSchema.templates) {
        for (Field field : template.fields) {
          if (field.getProperty() != null) {
            properties.add(field.getProperty());
          }
          for (Parameter param : field.formInput.parameters) {
            if ("mandatory".equals(param.name.toLowerCase())) {
              if (linkField == null) {
                linkField = field;
              } // if linkfield
            } // mandatory
          } // for param
          queryfields += "| ?" + category + " " + field.name + "\n";
        }
      }
    }

    /**
     * get the Text for the listpage
     * 
     * @return
     */
    public String getListPageText() {
      String listPageText = "__NOCACHE__\n" + "{{#ask: [[Category:" + category
          + "]] [[" + category + " " + linkField.getName() + "::+]]\n"
          + queryfields + "}}\n" + "[[:Category:" + category + "]]\n" + "";
      return listPageText;
    }

   }

  /**
   * update Me on the given wiki must be already logged in
   * 
   * @param wiki
   * @throws Exception
   */
  public void update(MediawikiApi wiki, List<PageSchema> linkedSchemas)
      throws Exception {
    if (this.category == null)
      throw new Exception("the category of the schema must be set!");
    LOGGER.log(Level.INFO, "updating PageSchema for " + this.category + " on "
        + wiki.getSiteurl() + wiki.getScriptPath());
    String xml = this.asXML();
    String pageTitle = "Category:" + this.category;
    xml = xml.replaceAll("\\<\\?xml(.+?)\\?\\>", "").trim();
    if (debug)
      LOGGER.log(Level.INFO, xml);
    // create the listPage
    ConceptPage listPage = new ConceptPage(this);
    String listPageContent = "";
    if (listPage.isAvailable()) {
      listPageContent = "* [[" + listPage.listPageTitle + "]]<br>\n";
    }
    String generated = "\n[[Category:PageSchema]]\n"
        + "This Category has been "+getGenerationTimeStamp(wiki)+"<br>\n";
    String documentation = "\n=== Documentation ===\n" + this.wikiDocumentation
        + "<br>\n";
    documentation += "===Concept===\n{{Concept\n" + "|name=" + this.getName()
        + "\n" + "}}\n";
    for (PageSchema linkedSchema : linkedSchemas) {
      documentation += "* see also [[:Category:" + linkedSchema.category
          + "]]\n";
    }
    String uml = "=== UML ===\n" + this.asPlantUml(linkedSchemas);

    String links = "=== Links === \n" + "* [[:Category:" + this.category
        + "]]<br>\n" + "* [[:Template:" + this.category + "]]<br>\n"
        + "* [[:Form:" + this.category + "]]<br>\n" + listPageContent;
    if (listPage.properties.size() > 0) {
      String proplist = "====Properties ====\n";
      for (Property property : listPage.properties) {
        proplist += "* [[Property:" + property.getName() + "]]<br>\n";
      }
      links += "\n" + proplist;
    }

    String sourcedocumentation = this.getSourceDocumentation();
    // What to display on the Wiki page
    String text = xml + "\n" + uml + documentation + links
        + sourcedocumentation + generated;

    String summary = "modified by JSMW_PageSchema at " + wiki.getIsoTimeStamp();
    // wiki.setDebug(true);
    pageSchemaManager.edit(pageTitle, text, summary);

    if (!listPage.isAvailable()) {
      LOGGER.log(Level.WARNING, "no mandatory field specified for Category "
          + this.category);
    } else {
      listPage.updateConceptPage(wiki, listPage.conceptPageTitle, "ConceptPage.ftl");
      pageSchemaManager.edit(listPage.listPageTitle,
          listPage.getListPageText(), summary);
    }
  }

  /**
   * get a generation time Stamp
   * @param wiki
   * @return
   */
  private String getGenerationTimeStamp(MediawikiApi wiki) {
    String result="generated with " + getJSMW_Link() + " at "
        + wiki.getIsoTimeStamp();
    return result;
  }

  /**
   * get the copyright
   * 
   * @return the copyright
   */
  public String getCopyright() {
    String result = "Copyright (c) 2015 BITPlan GmbH\n"
        + "[[http://www.bitplan.com]]";
    return result;
  }

  /**
   * return me as an uml diagram
   */
  public String asPlantUml(List<PageSchema> linkedSchemas) {
    String content = getUmlTitle(this.category);
    String note = "";
    content += getUmlNote(category + "DiagramNote", getCopyright() + note);
    String classContent = "";
    for (Template template : this.getTemplates()) {
      classContent += template.getUmlContent();
    }
    content += getUmlClass(this.category, classContent);
    for (PageSchema linkedSchema : linkedSchemas) {
      content += category + " -- " + linkedSchema.category + "\n";
    }
    String result = super.asPlantUml(content);
    return result;
  }

  public String getSpot() {
    // << (S,#FF7700) Singleton >>
    String spot = " <<Category>>";
    return spot;
  }

  /**
   * return me as an UML Class
   * 
   * @param className
   * @param classContent
   * @return
   */
  @XmlTransient
  protected String getUmlClass(String className, String classContent) {
    // FIXME top of "+className
    // http://plantuml.sourceforge.net/classes.html

    String classNote = getUmlNote(className + "Note ", this.umlDocumentation);
    classNote += className + "Note .. " + className + "\n";
    String result = classNote + "Class " + className + getSpot() + " {\n"
        + classContent + "\n" + "}\n";
    return result;
  }

  /**
   * get the default Template
   * 
   * @return
   */
  public Template getDefaultTemplate() {
    // add a Form
    Form form = new Form(this, this.category);

    // add a template
    Template template = new Template(form, this.category, "standard");
    return template;
  }

  /**
   * set the source documentation
   * 
   * @param source
   * @param lang
   * @param title
   */
  public void setSource(String source, String lang, String title) {
    this.source = source;
    this.lang = lang;
    this.sourceTitle = title;
  }

  /**
   * get the source documentation
   * 
   * @return the source documentation
   */
  @XmlTransient
  public String getSourceDocumentation() {
    String result = "";
    String footer = "This example source code works with " + getJSMW_Link()
        + "<br>\n";
    if (source != null && !source.trim().equals("")) {
      result = sourceTitle + "\n<br><source lang='" + lang + "'>" + source
          + "</source>\n" + footer;
    }
    return result;
  }

}
