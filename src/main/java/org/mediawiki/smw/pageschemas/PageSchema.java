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

  String freemarkerTemplatePath="/templates";
  
  /**
   * @return the freemarkerTemplatePath
   */
  public String getFreemarkerTemplatePath() {
    return freemarkerTemplatePath;
  }

  /**
   * @param freemarkerTemplatePath the freemarkerTemplatePath to set
   */
  public void setFreemarkerTemplatePath(String freemarkerTemplatePath) {
    this.freemarkerTemplatePath = freemarkerTemplatePath;
  }
  
  /**
   * get a template result
   * @param rootMap
   * @param freeMarkerTemplateName
   * @return
   * @throws Exception
   */
  public String processTemplate(Map<String, Object> rootMap,String freeMarkerTemplateName) throws Exception {
    // tell Freemarker to use main class path and therefore find templates in
    // main/resources/templates
    FreeMarkerConfiguration.addTemplateClass(PageSchema.class, getFreemarkerTemplatePath());
    // process the template with the given name
    String result = FreeMarkerConfiguration.doProcessTemplate(
        freeMarkerTemplateName, rootMap); 
    return result;
  }

  /**
   * update a wiki page using the given template
   * 
   * @param wiki
   * @param rootMap
   * @param pageTitle
   * @param freeMarkerTemplateName
   * @throws Exception
   */
  public void updateWithTemplate(MediawikiApi wiki,
      Map<String, Object> rootMap, String pageTitle,
      String freeMarkerTemplateName) throws Exception {
    String summary = getGenerationTimeStamp(wiki);
    updateWithTemplate(wiki,rootMap,pageTitle,freeMarkerTemplateName,summary);
  }

  /**
   * update a wiki page using the given template settings
   * @param wiki
   * @param rootMap
   * @param pageTitle
   * @param summary
   * @param freeMarkerTemplateName
   * @throws Exception
   */
  public void updateWithTemplate(MediawikiApi wiki,
      Map<String, Object> rootMap, String pageTitle,
      String freeMarkerTemplateName,String summary) throws Exception {

    String wikiPage=this.processTemplate(rootMap, freeMarkerTemplateName);
   
    // System.out.println(wikiPage);
    LOGGER.log(Level.INFO, "updating " + pageTitle + " on " + wiki.getSiteurl()
        + wiki.getScriptPath());
    ;
    wiki.edit(pageTitle, wikiPage, summary);
  }

  List<Property> properties = null;

  /**
   * @return the properties
   */
  public List<Property> getProperties() {
    if (properties == null) {
      properties = new ArrayList<Property>();
      for (Template template : getTemplates()) {
        for (Field field : template.getFields()) {
          if (field.getProperty() != null) {
            field.getProperty().setField(field);
            properties.add(field.getProperty());
          }
        }
      }
    }
    return properties;
  }

  /**
   * @param properties
   *          the properties to set
   */
  public void setProperties(List<Property> properties) {
    this.properties = properties;
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

    /**
     * @return the pageSchema
     */
    public PageSchema getPageSchema() {
      return pageSchema;
    }

    /**
     * @param pageSchema
     *          the pageSchema to set
     */
    public void setPageSchema(PageSchema pageSchema) {
      this.pageSchema = pageSchema;
    }

    /**
     * @return the listPageTitle
     */
    public String getListPageTitle() {
      return listPageTitle;
    }

    /**
     * @param listPageTitle
     *          the listPageTitle to set
     */
    public void setListPageTitle(String listPageTitle) {
      this.listPageTitle = listPageTitle;
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
      // FIXME - do we still need that crutch?
      // query expects non null value (pseudo-primary key ...)
      // could be null after the following search if no mandatory field is
      // specified
      for (Template template : pageSchema.templates) {
        for (Field field : template.fields) {
          for (Parameter param : field.formInput.parameters) {
            if ("mandatory".equals(param.name.toLowerCase())) {
              if (linkField == null) {
                linkField = field;
              } // if linkfield
            } // mandatory
          } // for param
        }
      }
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
    String pageTitle = "Category:" + this.category;
    LOGGER.log(Level.INFO, "updating PageSchema for " + this.category
        + " page " + wiki.getSiteurl() + wiki.getScriptPath() + pageTitle);
    String xml = this.asXML();
    xml = xml.replaceAll("\\<\\?xml(.+?)\\?\\>", "").trim();
    if (debug)
      LOGGER.log(Level.INFO, xml);
    // create the listPage
    ConceptPage listPage = new ConceptPage(this);
    Map<String, Object> rootMap = new HashMap<String, Object>();
    rootMap.put("pageSchema", this);
    rootMap.put("linkedSchemas", linkedSchemas);
    String uml = this.processTemplate(rootMap, "Plantuml.ftl");

    // What to display on the Category Wiki page
    rootMap.put("xml", xml);
    rootMap.put("uml", uml);
    rootMap.put("documentation", this.wikiDocumentation);
    rootMap.put("listPage", listPage);
    rootMap.put("sourcedocumentation", this.getSourceDocumentation());
    rootMap.put("generated", getGenerationTimeStamp(wiki));

    updateWithTemplate(wiki, rootMap, pageTitle, "CategoryPage.ftl");

    if (!listPage.isAvailable()) {
      LOGGER.log(Level.WARNING, "no mandatory field specified for Category "
          + this.category);
    } else {
      rootMap.put("template", this.getTemplates().get(0));
      rootMap.put("conceptPage", listPage);
      updateWithTemplate(wiki, rootMap, listPage.conceptPageTitle,
          "ConceptPage.ftl");
      updateWithTemplate(wiki, rootMap, listPage.listPageTitle, "ListPage.ftl");
    }
  }
  
  /**
   * update the property declaration pages 
   * @param wiki - the wiki
   * @throws Exception 
   */
  public void updatePropertyDeclarationPages(MediawikiApi wiki) throws Exception {
    for (Property property:this.getProperties()) {
      Map<String, Object> rootMap = new HashMap<String, Object>();
      rootMap.put("pageSchema", this);
      rootMap.put("property",property);
      String pageTitle="Property:"+property.getName();
      updateWithTemplate(wiki, rootMap, pageTitle, "PropertyPage.ftl");
    }
  }

  /**
   * get a generation time Stamp
   * 
   * @param wiki
   * @return
   */
  private String getGenerationTimeStamp(MediawikiApi wiki) {
    String result = "generated with " + getJSMW_Link() + " at "
        + wiki.getIsoTimeStamp();
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
