package de.retest.recheck.xml;

import java.io.InputStream;
import java.io.Reader;
import java.util.Collections;
import java.util.Set;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

import com.google.common.base.Charsets;

import de.retest.ui.descriptors.RootElement;

public class XmlToRootConverter {

	private static final XMLInputFactory inputFactory = XMLInputFactory.newInstance();

	public static Set<RootElement> toRootElement( final InputStream in ) throws XMLStreamException {
		return convert( inputFactory.createXMLEventReader( in, Charsets.UTF_8.name() ) );
	}

	public static Set<RootElement> toRootElement( final Reader in ) throws XMLStreamException {
		return convert( inputFactory.createXMLEventReader( in ) );
	}

	private static Set<RootElement> convert( final XMLEventReader eventReader ) throws XMLStreamException {
		if ( eventReader == null ) {
			return Collections.emptySet();
		}
		try {
			while ( eventReader.hasNext() ) {
				final XMLEvent event = eventReader.nextEvent();
				if ( event.isStartElement() ) {
					final XMLEventToRootElementConverter converter = new XMLEventToRootElementConverter( event );
					return Collections.singleton( (RootElement) converter.convert( eventReader ) );
				}
			}
			throw new RuntimeException("Couldn't identify start element in XML stream.");
		} finally {
			closeQuietly( eventReader );
		}
	}

	private static void closeQuietly( final XMLEventReader eventReader ) {
		try {
			eventReader.close();
		} catch ( final XMLStreamException e ) {
			// ignore
		}
	}

}
