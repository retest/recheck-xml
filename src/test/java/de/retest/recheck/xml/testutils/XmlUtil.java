package de.retest.recheck.xml.testutils;

import java.io.StringWriter;
import java.util.Collections;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.helpers.DefaultValidationEventHandler;

import org.eclipse.persistence.internal.oxm.record.namespaces.MapNamespacePrefixMapper;
import org.eclipse.persistence.jaxb.JAXBContextFactory;
import org.eclipse.persistence.jaxb.MarshallerProperties;

import com.google.common.collect.ImmutableMap;

import de.retest.xml.StdXmlClassesProvider;

public class XmlUtil {

	public static String toXml( final Object element ) throws Exception {
		final StringWriter writer = new StringWriter();
		final Class<?>[] contextClasses = StdXmlClassesProvider.a();
		final JAXBContext jc = JAXBContextFactory.createContext( contextClasses, Collections.emptyMap() );

		final Marshaller marshaller;
		try {
			marshaller = jc.createMarshaller();
			marshaller.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, true );
			marshaller.setProperty( MarshallerProperties.NAMESPACE_PREFIX_MAPPER, prefix() );
			marshaller.setProperty( MarshallerProperties.INDENT_STRING, "\t" );
			marshaller.setEventHandler( new DefaultValidationEventHandler() );
			marshaller.setProperty( Marshaller.JAXB_FRAGMENT, true );
			marshaller.marshal( element, writer );
			return writer.toString();
		} catch ( final JAXBException e ) {
			throw e;
		}
	}

	private static MapNamespacePrefixMapper prefix() {
		return new MapNamespacePrefixMapper( ImmutableMap.of( "http://www.w3.org/2001/XMLSchema", "xsd",
				"http://www.w3.org/2001/XMLSchema-instance", "xsi" ) );
	}
}
