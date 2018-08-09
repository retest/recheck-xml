package de.retest.recheck.xml;

import static de.retest.recheck.xml.testutils.ApprovalsUtil.verifyXml;
import static de.retest.recheck.xml.testutils.XmlUtil.toXml;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.util.Set;

import org.junit.Test;

import de.retest.ui.descriptors.RootElement;

public class RecheckXmlAdapterTest {

	private final File xmlDir = new File( "src/test/resources/xml" );

	@Test
	public void read_note_xml() throws Exception {
		final RecheckXmlAdapter adapter = new RecheckXmlAdapter();

		final Set<RootElement> element = adapter.convert( new File( xmlDir, "note.xml" ) );

		assertThat( element ).doesNotContainNull();
		verifyXml( toXml( element ) );
	}

	@Test
	public void read_cd_catalog_xml() throws Exception {
		final RecheckXmlAdapter adapter = new RecheckXmlAdapter();

		final Set<RootElement> element = adapter.convert( new File( xmlDir, "cd_catalog.xml" ) );

		assertThat( element ).doesNotContainNull();
		verifyXml( toXml( element ) );
	}
}
