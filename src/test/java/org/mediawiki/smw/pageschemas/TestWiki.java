package org.mediawiki.smw.pageschemas;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
// import java.util.Base64;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.security.auth.login.FailedLoginException;

import org.junit.BeforeClass;
import org.junit.Test;
import org.wikipedia.Mediawiki;
import org.wikipedia.Wiki;
import org.wikipedia.Wiki.User;
import org.wikipedia.XTrustProvider;

/**
 * test Wiki interface
 * 
 * @author wf
 *
 */
public class TestWiki {
	protected static Logger LOGGER = Logger
			.getLogger("org.mediawiki.smw.pageschemas");

	private static String propsPath;
	private static Mediawiki wiki;

	

	@BeforeClass
	public static void readProps() throws Exception {
		propsPath = System.getProperty("user.home") + "/.testWiki/properties.txt";
		File propsFile = new File(propsPath);
		if (propsFile.canRead()) {
		  wiki=Mediawiki.fromProperties(propsFile);
		}
	}

	@Test
	public void testLogin() throws FailedLoginException, IOException {
		if (wiki != null) {
			User user = wiki.getCurrentUser();
			LOGGER.log(Level.INFO,user.getUsername()+" logged into "+wiki.getDomain());
		} else {
			LOGGER.log(Level.WARNING,"test not run due to missing file " + propsPath);
		}
	}

}
