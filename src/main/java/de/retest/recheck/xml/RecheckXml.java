package de.retest.recheck.xml;

import java.io.File;

import de.retest.recheck.Recheck;
import de.retest.recheck.RecheckAdapter;
import de.retest.recheck.RecheckImpl;

public class RecheckXml implements Recheck {

	private final Recheck delegate;

	public RecheckXml() {
		delegate = new RecheckImpl();
	}

	public void checkXml( final File xmlFile ) {
		delegate.startTest( xmlFile.getName() );
		delegate.check( xmlFile, "check" );
		delegate.capTest();
	}

	@Override
	public void check( final Object toVerify, final String stepName ) throws IllegalStateException {
		delegate.check( toVerify, stepName );
	}

	@Override
	public void check( final Object toVerify, final RecheckAdapter adapter, final String stepName )
			throws IllegalArgumentException {
		delegate.check( toVerify, adapter, stepName );
	}

	@Override
	public void startTest() {
		delegate.startTest();
	}

	@Override
	public void startTest( final String testName ) {
		delegate.startTest( testName );
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
