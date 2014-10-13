package org.wikipedia;

/**
 * Copyright (C) 2013 BITPlan GmbH
 *
 * Pater-Delp-Str. 1
 * D-47877 Willich-Schiefbahn
 *
 * http://www.bitplan.com
 * 
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.security.auth.login.FailedLoginException;

import org.apache.commons.io.FileUtils;

/**
 * Mediawiki API access
 * 
 * @author wf
 * 
 */
public class Mediawiki extends org.wikipedia.Wiki {
	protected static Logger LOGGER = Logger
			.getLogger("com.bitplan.wikisync.mediawiki");
	/**
	 * 
	 */
	private static final long serialVersionUID = -6514233707434381106L;

	/**
	 * get a time stamp
	 * 
	 * @return
	 */
	public static String timeStamp(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String result = formatter.format(date);
		return result;
	}

	// Create a trust manager that does not validate certificate chains
	TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}

		public void checkClientTrusted(X509Certificate[] certs, String authType) {
		}

		public void checkServerTrusted(X509Certificate[] certs, String authType) {
		}
	} };

	/**
	 * decode the given string
	 * 
	 * @param str
	 * @return
	 */
	public static String base64Decode(String str) {
		// Decode data on other side, by processing encoded data
		// jdk 8
		// String result=new String(Base64.getDecoder().decode(str));
		String result = new String(
				javax.xml.bind.DatatypeConverter.parseBase64Binary(str));
		return result;
	}

	/**
	 * encode the given string
	 * 
	 * @param str
	 * @return
	 */
	public static String base64Encode(String str) {
		// encode data on your side using BASE64
		// jdk8
		// String result=Base64.getEncoder().encodeToString(str.getBytes());
		String result = javax.xml.bind.DatatypeConverter.printBase64Binary(str
				.getBytes());
		return result;
	}
	
	/**
	 * get a Mediawiki instance from the given properties
	 * @param propsFile
	 * @return
	 * @throws Exception
	 */
	public static Mediawiki fromProperties(File propsFile) throws Exception {
		Properties props=new Properties();
		props.load(new FileInputStream(propsFile));
		String prot=props.getProperty("prot");
		String domain = props.getProperty("domain");
		String scriptpath = props.getProperty("scriptpath");
		String username = props.getProperty("username");
		String password = props.getProperty("password");
		password =base64Decode(password.trim()).replace("\r", "").replace("\n", "");
		// LOGGER.log(Level.INFO, domain + " " + username + " '" + password+"'");

		// http://stackoverflow.com/questions/7615645/ssl-handshake-alert-unrecognized-name-error-since-upgrade-to-java-1-7-0
		System.setProperty("jsse.enableSNIExtension", "false");
		// http://stackoverflow.com/questions/3093112/certificateexception-no-name-matching-ssl-someurl-de-found
		// FIXME JDK 1.8 needed
		// HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> true);
		// https://code.google.com/p/misc-utils/wiki/JavaHttpsUrl
		XTrustProvider.install();
		Mediawiki wiki=new Mediawiki(prot,domain,scriptpath);
		wiki.login(username, password);
		return wiki;
	}

	/**
	 * create a Wiki
	 * 
	 * @param server
	 * @param scriptPath
	 */
	public Mediawiki(String prot, String server, String scriptPath) {
		super(prot, server, scriptPath);
		zipped = false;
		this.setMaxLag(0);
		this.setThrottle(0);
	}

	/**
	 * open a connection to the given URL
	 * 
	 * @param url
	 * @return
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 */
	protected URLConnection openConnection(String url)
			throws MalformedURLException, IOException {
		// Install the all-trusting trust manager
		SSLContext sc;
		try {
			sc = SSLContext.getInstance("TLS");
			sc.init(null, trustAllCerts, new SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
			HostnameVerifier hv = new IgnoreHostName();
			HttpsURLConnection.setDefaultHostnameVerifier(hv);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		URLConnection result = new URL(url).openConnection();
		return result;
	}

	/**
	 * import a page from the given xml file
	 * 
	 * @param xmlFile
	 * @return
	 * @throws IOException
	 */
	public String doimport(File xmlFile) throws IOException {
		String xml = FileUtils.readFileToString(xmlFile);
		LOGGER.log(Level.FINEST, xml);
		String[] parts = xml.split("<title>");
		if (parts.length < 2)
			throw new IllegalArgumentException(
					"doimport: xml does not contain any page title");
		parts = parts[1].split("</title>");
		if (parts.length < 1)
			throw new IllegalArgumentException("doimport: xml title tag broken");
		String title = parts[0];
		// Mediawiki API description for import function:
		// http://www.mediawiki.org/wiki/API:Import#XML_file
		/**
		 * res = HTTPClient.post(@api_url, { :action => 'import', :xml =>
		 * File.open("dump.xml"), :token => token, :format => 'xml'}, @headers)
		 */
		HashMap info = getPageInfo(title);
		// FIXME - credential check is missing
		String wpImportToken = (String) info.get("token");
		String caller = "import";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("action", "import");
		params.put("xml", xmlFile);
		params.put("token", wpImportToken);
		params.put("format", "xml");
		String result = super.multipartPost(apiUrl + "action=import", params,
				caller);
		return result;

	}

	/**
	 * import the given xml export string
	 * 
	 * @param xml
	 * @throws IOException
	 */
	public String doimport(String xml) throws IOException {
		File xmlFile = new File("/tmp/xmlimport");
		FileUtils.writeStringToFile(xmlFile, xml);
		String result = doimport(xmlFile);
		return result;
	}
}
