/**
 * Copyright (C) 2015 BITPlan GmbH
 *
 * Pater-Delp-Str. 1
 * D-47877 Willich-Schiefbahn
 *
 * http://www.bitplan.com
 * 
 */
package org.mediawiki.smw.pageschemas;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Testsuite for Mediawiki-Japi
 * @author wf
 *
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({ TestWiki.class, TestXML.class,
		TestPageSchema.class, TestExampleWikis.class })
public class TestSuite {

}
