package de.retest.recheck.xml;

import java.io.File;

import de.retest.recheck.Recheck;
import de.retest.recheck.RecheckAdapter;
import de.retest.recheck.RecheckImpl;

public class RecheckXml implements Recheck {

	private final Recheck delegate;

	public RecheckXml() {
		this.delegate = new RecheckImpl();
	}

	public void checkXml(File xmlFile) {
		delegate.startTest(xmlFile.getName());
		delegate.check(xmlFile, "");
		delegate.capTest();
	}

	@Override
	public void check(Object toVerify, String stepName) throws IllegalStateException {
		delegate.check(toVerify, stepName);
	}

	@Override
	public void check(Object toVerify, RecheckAdapter adapter, String stepName) throws IllegalArgumentException {
		delegate.check(toVerify, adapter, stepName);
	}

	@Override
	public void startTest() {
		delegate.startTest();
	}

	@Override
	public void startTest(String testName) {
		delegate.startTest(testName);
	}

	@Override
	public void capTest() throws AssertionError {
		delegate.capTest();
	}

	@Override
	public void cap() {
		delegate.cap();
	}

}
