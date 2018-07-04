package de.retest.recheck.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class XmlInputHandler {

	public boolean canCheck( final Object toVerify ) {
		if ( isXmlFile( toVerify ) ) {
			return true;
		}
		if ( isXmlString( toVerify ) ) {
			return true;
		}
		return false;
	}

	public InputStream convertToInputStream( final Object toVerify ) {
		if ( isXmlFile( toVerify ) ) {
			try {
				return new FileInputStream( (File) toVerify );
			} catch ( final FileNotFoundException e ) {
				throw new RuntimeException( e );
			}
		}
		throw new IllegalArgumentException( "Conversion not implemented for inputs of " + toVerify.getClass() );
	}

	private boolean isXmlFile( final Object toVerify ) {
		if ( toVerify instanceof File ) {
			final File file = (File) toVerify;
			if ( file.getName().endsWith( ".xml" ) ) {
				return true;
			}
		}
		return false;
	}

	private boolean isXmlString( final Object toVerify ) {
		if ( toVerify instanceof String ) {
			final String string = (String) toVerify;
			if ( string.startsWith( "<?xml" ) ) {
				return true;
			}
		}
		return false;
	}
}
