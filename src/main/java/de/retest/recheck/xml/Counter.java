package de.retest.recheck.xml;

import java.util.HashMap;
import java.util.Map;

public class Counter {

	private final Map<Object, Integer> counts = new HashMap<Object, Integer>();

	public int getCount( final Object value ) {
		Integer result = counts.get( value );
		if ( result == null ) {
			result = 0;
		}
		counts.put( value, result + 1 );
		return result;
	}

}
