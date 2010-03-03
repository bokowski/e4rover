package org.eclipsecon.ebots.client;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.osgi.service.prefs.Preferences;

public class PreferenceInitializer extends AbstractPreferenceInitializer {

	public PreferenceInitializer() {
	}

	@Override
	public void initializeDefaultPreferences() {
		Preferences node = new DefaultScope().getNode("org.eclipsecon.ebots.client");
		node.put("PLAYER_KEY", "not set");
	}

}
