package de.retest.recheck.xml;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Collections;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.helpers.DefaultValidationEventHandler;

import org.approvaltests.Approvals;
import org.approvaltests.ReporterFactory;
import org.approvaltests.namer.ApprovalNamer;
import org.approvaltests.writers.ApprovalTextWriter;
import org.assertj.core.api.Assertions;
import org.eclipse.persistence.internal.oxm.record.namespaces.MapNamespacePrefixMapper;
import org.eclipse.persistence.jaxb.JAXBContextFactory;
import org.eclipse.persistence.jaxb.MarshallerProperties;
import org.junit.Test;

import com.google.common.collect.ImmutableMap;

import de.retest.ui.descriptors.RootElement;
import de.retest.xml.StdXmlClassesProvider;

public class RecheckXmlAdapterTest {

	private final File xmlDir = new File("src/test/resources/xml");

	@Test
	public void read_note_xml() throws Exception {
		final RecheckXmlAdapter adapter = new RecheckXmlAdapter();
		final RootElement element = adapter.convert(new File(xmlDir, "note.xml")).iterator().next();

		Assertions.assertThat(element).isNotNull();
		verifyXml(toXml(element), "read_note_xml");
	}

	@Test
	public void read_cd_catalog_xml() throws Exception {
		final RecheckXmlAdapter adapter = new RecheckXmlAdapter();
		final RootElement element = adapter.convert(new File(xmlDir, "cd_catalog.xml")).iterator().next();

		Assertions.assertThat(element).isNotNull();
		verifyXml(toXml(element), "read_cd_catalog_xml");
	}

	public void verifyXml(String xml, String filename) {
		Approvals.verify(new ApprovalTextWriter(xml, "xml"), new ApprovalNamer() {
			@Override
			public String getApprovalName() {
				return RecheckXmlAdapterTest.class.getSimpleName() + "." + filename;
			}

			@Override
			public String getSourceFilePath() {
				return "src" + File.separator + "test" + File.separator + "resources" + File.separator + RecheckXmlAdapterTest.class.getPackage().getName().replaceAll("\\.", File.separator) + File.separator;
			}
		}, ReporterFactory.get());
	}

	private String toXml(final RootElement element) throws Exception {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		final Class<?>[] contextClasses = StdXmlClassesProvider.a();
		JAXBContext jc = JAXBContextFactory.createContext(contextClasses, Collections.emptyMap());

		Marshaller marshaller = null;
		try {
			marshaller = jc.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.setProperty(MarshallerProperties.NAMESPACE_PREFIX_MAPPER, new MapNamespacePrefixMapper(ImmutableMap.of("http://www.w3.org/2001/XMLSchema", "xsd", "http://www.w3.org/2001/XMLSchema-instance", "xsi")));
			marshaller.setProperty(MarshallerProperties.INDENT_STRING, "\t");
			marshaller.setEventHandler(new DefaultValidationEventHandler());
			marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
			marshaller.marshal(element, out);
			return new String(out.toByteArray());
		} catch (final JAXBException e) {
			throw new RuntimeException(e);
		}
	}

}
