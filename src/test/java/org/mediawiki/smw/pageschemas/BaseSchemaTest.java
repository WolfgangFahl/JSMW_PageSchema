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

import java.util.logging.Logger;

import com.bitplan.mediawiki.japi.Mediawiki;
import com.bitplan.mediawiki.japi.MediawikiApi;
import com.bitplan.mediawiki.japi.api.Login;
import com.bitplan.mediawiki.japi.user.WikiUser;

/**
 * base class for schema test
 * @author wf
 *
 */
public class BaseSchemaTest {

	protected static Logger LOGGER = Logger
			.getLogger("org.mediawiki.smw.pageschemas");
	/**
	 * get the wiki to talk to
	 * @return
	 * @throws Exception
	 */
	public MediawikiApi getWiki() throws Exception {
		Mediawiki wiki=new Mediawiki("http://mediawiki-japi.bitplan.com","/mediawiki-japi/");
		WikiUser wuser=WikiUser.getUser("mediawiki-japi",wiki.getSiteurl());
    Login login=wiki.login(wuser.getUsername(),wuser.getPassword());
    return wiki;
	}
}
