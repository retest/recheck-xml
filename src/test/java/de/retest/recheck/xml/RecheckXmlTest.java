package de.retest.recheck.xml;

import java.io.File;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class RecheckXmlTest {

	private final File xmlDir = new File( "src/test/resources/xml" );

	private static RecheckXml re;

	@BeforeClass
	public static void setup() {
		// Use the default implementation.
		re = new RecheckXml();
	}

	@Test
	public void check_note() {
		re.checkXml( new File( xmlDir, "note.xml" ) );
	}

	@Test
	public void check_cd_catalog() {
		re.checkXml( new File( xmlDir, "cd_catalog.xml" ) );
	}

	@AfterClass
	public static void teardown() {
		re.cap();
	}
}
