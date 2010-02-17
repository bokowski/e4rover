package org.eclipsecon.ebots.core;

import java.util.Map;

public interface ITelemetry extends IGameObject {
	/** @return the telemetry values */
	public Map<String, String> getValues();
}
