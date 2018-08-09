package de.retest.recheck.xml.testutils;

import java.io.File;

import org.approvaltests.Approvals;
import org.approvaltests.ReporterFactory;
import org.approvaltests.namer.ApprovalNamer;
import org.approvaltests.writers.ApprovalTextWriter;

public class ApprovalsUtil {

	private static final String BASE = "src" + File.separator + "test" + File.separator;
	private static final String JAVA = BASE + "java";
	private static final String RESOURCES = BASE + "resources";

	public static void verifyXml( final String xml ) {
		final ApprovalNamer namer = Approvals.createApprovalNamer();
		Approvals.verify( new ApprovalTextWriter( xml, "xml" ), new ApprovalNamer() {

			@Override
			public String getApprovalName() {
				return namer.getApprovalName();
			}

			@Override
			public String getSourceFilePath() {
				return namer.getSourceFilePath().replace( JAVA, RESOURCES );
			}
		}, ReporterFactory.get() );
	}
}
