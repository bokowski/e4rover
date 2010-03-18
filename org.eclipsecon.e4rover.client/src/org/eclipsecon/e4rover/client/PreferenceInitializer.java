/*******************************************************************************
 * Copyright (c) 2010 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 ******************************************************************************/
package org.eclipsecon.e4rover.client;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.osgi.service.prefs.Preferences;

public class PreferenceInitializer extends AbstractPreferenceInitializer {

	public PreferenceInitializer() {
	}

	// This preference initializer exists so that we don't have to add null
	// checks everywhere the preference value is being used.
	@Override public void initializeDefaultPreferences() {
		Preferences node = new DefaultScope().getNode("org.eclipsecon.e4rover.client");
		node.put("PLAYER_KEY", "not set");
	}

}
