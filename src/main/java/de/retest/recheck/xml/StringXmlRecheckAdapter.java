package de.retest.recheck.xml;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Set;

import javax.xml.stream.XMLStreamException;

import de.retest.recheck.RecheckAdapter;
import de.retest.ui.DefaultValueFinder;
import de.retest.ui.descriptors.RootElement;

public class StringXmlRecheckAdapter implements RecheckAdapter {

	private static final String XML_PREFIX = "<?xml";

	@Override
	public boolean canCheck( final Object toVerify ) {
		return (toVerify instanceof String) && ((String) toVerify).startsWith( XML_PREFIX );
	}

	@Override
	public Set<RootElement> convert( final Object toVerify ) {
		try ( final Reader reader = new StringReader( (String) toVerify ) ) {
			return XmlToRootConverter.toRootElement( reader );
		} catch ( final IOException e ) {
			throw new RuntimeException( "Could not read string: "+toVerify, e );
		} catch ( final XMLStreamException e ) {
			throw new RuntimeException( "Failed to convert to xml:"+ toVerify, e );
		}
	}

	@Override
	public DefaultValueFinder getDefaultValueFinder() {
		return ( comp, attributesKey ) -> null;
	}
}
