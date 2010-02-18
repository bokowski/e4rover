package org.eclipsecon.ebots.internal.core;

import java.util.HashMap;
import java.util.Map;

import org.eclipsecon.ebots.core.ITelemetry;

public class Telemetry extends ServerObject implements ITelemetry {
	private Map<String, String> t = new HashMap<String, String>();

	public Map<String, String> getValues() {
		return t;
	}

}
