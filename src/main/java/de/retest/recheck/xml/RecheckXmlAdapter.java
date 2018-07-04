package de.retest.recheck.xml;

import java.io.InputStream;
import java.io.Serializable;
import java.util.Collections;
import java.util.Set;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

import com.google.common.base.Charsets;

import de.retest.recheck.RecheckAdapter;
import de.retest.ui.DefaultValueFinder;
import de.retest.ui.descriptors.IdentifyingAttributes;
import de.retest.ui.descriptors.RootElement;

public class RecheckXmlAdapter implements RecheckAdapter {

	private final XmlInputHandler inputHandler = new XmlInputHandler();

	@Override
	public boolean canCheck( final Object toVerify ) {
		return inputHandler.canCheck( toVerify );
	}

	@Override
	public Set<RootElement> convert( final Object toVerify ) {
		final InputStream inputStream = inputHandler.convertToInputStream( toVerify );
		final XMLInputFactory inputFactory = XMLInputFactory.newInstance();
		XMLEventReader eventReader = null;
		try {
			eventReader = inputFactory.createXMLEventReader( inputStream, Charsets.UTF_8.name() );
			while ( eventReader.hasNext() ) {
				final XMLEvent event = eventReader.nextEvent();
				if ( event.isStartElement() ) {
					final XMLEventToRootElementConverter converter =
							new XMLEventToRootElementConverter( event );
					return Collections.singleton( (RootElement) converter.convert( eventReader ) );
				}
			}
			return Collections.emptySet();
		} catch ( final Exception exc ) {
			throw new RuntimeException( exc );
		} finally {
			if ( eventReader != null ) {
				try {
					eventReader.close();
				} catch ( final XMLStreamException e ) {
					// ignore
				}
			}
		}
	}

	@Override
	public DefaultValueFinder getDefaultValueFinder() {
		return new DefaultValueFinder() {
			@Override
			public Serializable getDefaultValue( final IdentifyingAttributes comp, final String attributesKey ) {
				return null;
			}
		};
	}

}
