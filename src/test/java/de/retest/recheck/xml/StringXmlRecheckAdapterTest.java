package de.retest.recheck.xml;

import static de.retest.recheck.xml.testutils.ApprovalsUtil.verifyXml;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import de.retest.recheck.xml.testutils.XmlUtil;
import de.retest.ui.descriptors.RootElement;

public class StringXmlRecheckAdapterTest {

	private final File xmlDir = new File( "src/test/resources/xml" );

	@Test
	public void read_note_xml() throws Exception {
		final StringXmlRecheckAdapter cut = new StringXmlRecheckAdapter();
		final String toVerify = toXmlString( "note.xml" );

		assertThat( cut.canCheck( toVerify ) ).isTrue();

		final Set<RootElement> convert = cut.convert( toVerify );

		assertThat( convert ).hasSize( 1 );
		assertThat( convert ).doesNotContainNull();
		verifyXml( XmlUtil.toXml( convert ) );
	}

	@Test
	public void read_cd_catalog_xml() throws Exception {
		final StringXmlRecheckAdapter cut = new StringXmlRecheckAdapter();
		final String toVerify = toXmlString( "cd_catalog.xml" );

		assertThat( cut.canCheck( toVerify ) ).isTrue();

		final Set<RootElement> convert = cut.convert( toVerify );

		assertThat( convert ).hasSize( 1 );
		assertThat( convert ).doesNotContainNull();
		verifyXml( XmlUtil.toXml( convert ) );
	}

	private String toXmlString( final String s ) throws IOException {
		final File file = new File( xmlDir, s );
		try ( final Reader in = new FileReader( file ) ) {
			return IOUtils.toString( in );
		}
	}
}
