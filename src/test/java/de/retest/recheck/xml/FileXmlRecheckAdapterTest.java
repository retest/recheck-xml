package de.retest.recheck.xml;

import static de.retest.recheck.xml.testutils.ApprovalsUtil.verifyXml;
import static de.retest.recheck.xml.testutils.XmlUtil.toXml;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.util.Set;

import org.junit.Test;

import de.retest.ui.descriptors.RootElement;

public class FileXmlRecheckAdapterTest {

	private final File xmlDir = new File( "src/test/resources/xml" );

	@Test
	public void read_note_xml() throws Exception {
		final FileXmlRecheckAdapter cut = new FileXmlRecheckAdapter();
		final File toVerify = toXmlFile( "note.xml" );

		assertThat( cut.canCheck( toVerify ) ).isTrue();

		final Set<RootElement> convert = cut.convert( toVerify );

		assertThat( convert ).hasSize( 1 );
		assertThat( convert ).doesNotContainNull();
		verifyXml( toXml( convert ) );
	}

	@Test
	public void read_cd_catalog_xml() throws Exception {
		final FileXmlRecheckAdapter cut = new FileXmlRecheckAdapter();
		final File toVerify = toXmlFile( "cd_catalog.xml" );

		assertThat( cut.canCheck( toVerify ) ).isTrue();

		final Set<RootElement> convert = cut.convert( toVerify );

		assertThat( convert ).hasSize( 1 );
		assertThat( convert ).doesNotContainNull();
		verifyXml( toXml( convert ) );
	}

	private File toXmlFile( final String s ) {
		return new File( xmlDir, s );
	}
}
