package de.retest.recheck.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Set;

import javax.xml.stream.XMLStreamException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.retest.recheck.RecheckAdapter;
import de.retest.ui.DefaultValueFinder;
import de.retest.ui.descriptors.RootElement;

public class FileXmlRecheckAdapter implements RecheckAdapter {

	private static final Logger logger = LoggerFactory.getLogger( FileXmlRecheckAdapter.class );

	private static final String XML_EXTENSION = ".xml";

	@Override
	public boolean canCheck( final Object toVerify ) {
		return toVerify instanceof File && ((File) toVerify).getName().endsWith( XML_EXTENSION );
	}

	@Override
	public Set<RootElement> convert( final Object toVerify ) {
		final File file = (File) toVerify;
		try ( final InputStream in = new FileInputStream( file ) ) {
			return XmlToRootConverter.toRootElement( in );
		} catch ( final IOException e ) {
			logger.error( "Could not read file to xml '{}'.", file, e );
		} catch ( final XMLStreamException e ) {
			logger.error( "Failed to convert xml of file '{}'.", file, e );
		}
		return Collections.emptySet();
	}

	@Override
	public DefaultValueFinder getDefaultValueFinder() {
		return ( comp, attributesKey ) -> null;
	}

}
