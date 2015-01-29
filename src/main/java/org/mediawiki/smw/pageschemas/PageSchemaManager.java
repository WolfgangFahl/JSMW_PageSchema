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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.bitplan.mediawiki.japi.MediawikiApi;

/**
 * rudimentary manager for schemas
 * 
 * @author wf
 *
 */
public class PageSchemaManager {
  protected static Logger LOGGER = Logger
      .getLogger("org.mediawiki.smw.pageschemas");
  protected Map<String, PageSchema> pageSchemas = new HashMap<String, PageSchema>();
  protected MediawikiApi wiki;

  /**
   * @return the wiki
   * @throws Exception 
   */
  public MediawikiApi getWiki() throws Exception {
    return wiki;
  }

  /**
   * @param wiki the wiki to set
   */
  public void setWiki(MediawikiApi wiki) {
    this.wiki = wiki;
  }

  /**
   * show a list of my pageSchemas for debugging
   * 
   * @return
   */
  public void showDebugList() {
    for (PageSchema ps : pageSchemas.values()) {
      System.out.println(ps.getName());
      System.out.println("uml: \n" + ps.getUmlDocumentation());
      System.out.println("wiki: \n" + ps.getWikiDocumentation());
      System.out.println();
    }
  }

  /**
   * lookup the schema with the given category
   * 
   * @param category
   * @return
   */
  public PageSchema lookup(String category) {
    PageSchema result = pageSchemas.get(category);
    if (result == null)
      LOGGER.log(Level.SEVERE, "Couldn't find PageSchema for category '"
          + category + "'");
    return result;
  }

  /**
   * update me on the given wiki - with the given PageSchemaManager - must
   * already be logged in to work
   * 
   * @param wiki
   * @param psm
   * @throws Exception
   */
  public void update(MediawikiApi wiki) throws Exception {
    this.wiki = wiki;
    for (PageSchema pageSchema : pageSchemas.values()) {
      List<PageSchema> allLinkedSchemas = new ArrayList<PageSchema>();
      for (Template template : pageSchema.getTemplates()) {
        List<PageSchema> linkedSchemas = template.getLinkedPageSchemas(this);
        allLinkedSchemas.addAll(linkedSchemas);
      }
      pageSchema.update(wiki, allLinkedSchemas);
    }
    LOGGER.log(Level.INFO, "Done");
  }

  /**
   * edit hook
   * 
   * @param pageTitle
   * @param text
   * @param summary
   * @throws Exception
   */
  public void edit(String pageTitle, String text, String summary)
      throws Exception {
    wiki.edit(pageTitle, text, summary);
  }

}
