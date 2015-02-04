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

import java.util.Map;

import com.bitplan.mediawiki.japi.MediawikiApi;

/**
 * interface for Wiki updates
 * @author wf
 *
 */
public interface WikiPageGenerator {
  /**
   * update a page with the given pageTitle using the given wiki, and the
   * freemarker setting in the given rootMap with the given templateName
   * @param wiki
   * @param rootMap
   * @param pageTitle
   * @param freeMarkerTemplateName
   * @throws Exception
   */
  public void updateWithTemplate(MediawikiApi wiki,
      Map<String, Object> rootMap, String pageTitle,
      String freeMarkerTemplateName) throws Exception;
}
