package de.retest.recheck.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

import javax.xml.stream.XMLStreamException;

import de.retest.recheck.RecheckAdapter;
import de.retest.ui.DefaultValueFinder;
import de.retest.ui.descriptors.RootElement;

public class FileXmlRecheckAdapter implements RecheckAdapter {

	private static final String XML_EXTENSION = ".xml";

	@Override
	public boolean canCheck(final Object toVerify) {
		return (toVerify instanceof File) && ((File) toVerify).getName().endsWith(XML_EXTENSION);
	}

	@Override
	public Set<RootElement> convert(final Object toVerify) {
		final File file = (File) toVerify;
		try (final InputStream in = new FileInputStream(file)) {
			return XmlToRootConverter.toRootElement(in);
		} catch (final IOException e) {
			throw new RuntimeException("Could not read file to xml '" + file.getPath() + "'.", e);
		} catch (final XMLStreamException e) {
			throw new RuntimeException("Failed to convert xml of file '" + file.getPath() + "'.", e);
		}
	}

	@Override
	public DefaultValueFinder getDefaultValueFinder() {
		return ( comp, attributesKey ) -> null;
	}

}
