package de.retest.recheck.xml;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.retest.ui.Path;
import de.retest.ui.PathElement;
import de.retest.ui.descriptors.Attribute;
import de.retest.ui.descriptors.Element;
import de.retest.ui.descriptors.IdentifyingAttributes;
import de.retest.ui.descriptors.MutableAttributes;
import de.retest.ui.descriptors.PathAttribute;
import de.retest.ui.descriptors.StringAttribute;

public class XMLEventToElementConverter {

	private static final Logger logger = LoggerFactory.getLogger( XMLEventToRootElementConverter.class );

	protected final Collection<Attribute> identifyingAttributes;
	protected final MutableAttributes attributes;
	protected final List<Element> containedComponents = new ArrayList<>();
	protected final Path path;

	public XMLEventToElementConverter( final Path parentPath, final int elementCounter, final XMLEvent event ) {
		final QName qName = event.asStartElement().getName();
		final String name = qName.getLocalPart();
		logger.debug( "Creating element for '{}'.", name );
		path = Path.path( parentPath, new PathElement( name, elementCounter ) );
		identifyingAttributes = extractIdentAttributes( qName );
		attributes = extractAttributes( event );
	}

	public Element convert( final XMLEventReader eventReader ) throws XMLStreamException {
		final Counter counter = new Counter();
		while ( eventReader.hasNext() ) {
			final XMLEvent event = eventReader.nextEvent();
			if ( event.isStartElement() ) {
				final QName qName = event.asStartElement().getName();
				final XMLEventToElementConverter elementConverter =
						new XMLEventToElementConverter( path, counter.getCount( qName ), event );
				containedComponents.add( elementConverter.convert( eventReader ) );
			}
			if ( event.isCharacters() && !event.asCharacters().getData().trim().isEmpty() ) {
				attributes.put( "content", event.asCharacters().getData() );
			}
			if ( event.isEndElement() ) {
				return createElement();
			}
		}
		throw new RuntimeException( "Not well-formed XML? No end-tag to root element?" );
	}

	protected Element createElement() {
		final IdentifyingAttributes identifyingAttributes = new IdentifyingAttributes( this.identifyingAttributes );
		final Element result = new Element( identifyingAttributes, attributes.immutable(), containedComponents );
		return result;
	}

	private Collection<Attribute> extractIdentAttributes( final QName qName ) {
		final Collection<Attribute> identifyingAttributes = new ArrayList<>();
		identifyingAttributes.add( new PathAttribute( path ) );
		if ( (qName.getPrefix() != null) && !qName.getPrefix().isEmpty() ) {
			identifyingAttributes.add( new StringAttribute( "prefix", qName.getPrefix() ) );
		}
		if ( (qName.getNamespaceURI() != null) && !qName.getNamespaceURI().isEmpty() ) {
			identifyingAttributes.add( new StringAttribute( "namespace-URI", qName.getNamespaceURI() ) );
		}
		return identifyingAttributes;
	}

	private MutableAttributes extractAttributes( final XMLEvent event ) {
		final MutableAttributes result = new MutableAttributes();
		final Iterator attributes = event.asStartElement().getAttributes();
		while ( attributes.hasNext() ) {
			final javax.xml.stream.events.Attribute attribute = (javax.xml.stream.events.Attribute) attributes.next();
			result.put( attribute.getName().toString(), attribute.getValue() );
		}
		return result;
	}
}
