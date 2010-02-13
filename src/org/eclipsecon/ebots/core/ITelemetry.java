package org.eclipsecon.ebots.core;

import java.util.Map;

public interface ITelemetry extends IServerObject {
	/** @return the telemetry values */
	public Map<String, String> getValues();
}
