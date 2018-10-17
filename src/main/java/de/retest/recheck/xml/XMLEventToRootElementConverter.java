package de.retest.recheck.xml;

import static de.retest.ui.descriptors.RetestIdProviderUtil.getRetestId;

import javax.xml.stream.events.XMLEvent;

import de.retest.ui.descriptors.IdentifyingAttributes;
import de.retest.ui.descriptors.RootElement;

public class XMLEventToRootElementConverter extends XMLEventToElementConverter {

	public XMLEventToRootElementConverter( final XMLEvent event ) {
		super( null, 0, event );
	}

	@Override
	public RootElement createElement() {
		final IdentifyingAttributes identifyingAttributes = new IdentifyingAttributes( this.identifyingAttributes );
		final RootElement result = new RootElement( getRetestId( identifyingAttributes ),identifyingAttributes, attributes.immutable(), null,
				containedComponents, null, 1, null );
		return result;
	}

}
