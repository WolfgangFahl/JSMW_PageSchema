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
import org.wikipedia.Wiki;
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

	static Properties props;
	private static String propsPath;

	/**
	 * decode the given string
	 * @param str
	 * @return
	 */
	public String decode(String str) {
	  //Decode data on other side, by processing encoded data
		// jdk 8
		// String result=new String(Base64.getDecoder().decode(str));
		String result=new String(javax.xml.bind.DatatypeConverter.parseBase64Binary(str));
		return result;
	}

	/**
	 * encode the given string
	 * @param str
	 * @return
	 */
	public String encode(String str) {
	  //encode data on your side using BASE64
		// jdk8
		// String result=Base64.getEncoder().encodeToString(str.getBytes());
		String result=javax.xml.bind.DatatypeConverter.printBase64Binary(str.getBytes());
		return result;
	}

	@BeforeClass
	public static void readProps() {
		propsPath = System.getProperty("user.home") + "/.testWiki/properties.txt";
		File propsFile = new File(propsPath);
		if (propsFile.canRead()) {
			props = new Properties();
			try {
				props.load(new FileInputStream(propsFile));
			} catch (FileNotFoundException e) {
			} catch (IOException e) {
			}
		}
	}

	@Test
	public void testLogin() throws FailedLoginException, IOException {
		if (props != null) {
			String prot=props.getProperty("prot");
			String domain = props.getProperty("domain");
			String scriptpath = props.getProperty("scriptpath");
			String username = props.getProperty("username");
			String password = props.getProperty("password");
			password =decode(password.trim()).replace("\r", "").replace("\n", "");
			LOGGER.log(Level.INFO, domain + " " + username + " '" + password+"'");

			// http://stackoverflow.com/questions/7615645/ssl-handshake-alert-unrecognized-name-error-since-upgrade-to-java-1-7-0
			System.setProperty("jsse.enableSNIExtension", "false");
			// http://stackoverflow.com/questions/3093112/certificateexception-no-name-matching-ssl-someurl-de-found
			// FIXME JDK 1.8 needed
			// HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> true);
			// https://code.google.com/p/misc-utils/wiki/JavaHttpsUrl
			XTrustProvider.install();
			Wiki wiki = new Wiki(prot,domain, scriptpath);
			wiki.login(username, password);
		} else {
			System.err.println("test not run due to missing file " + propsPath);
		}
	}

}
