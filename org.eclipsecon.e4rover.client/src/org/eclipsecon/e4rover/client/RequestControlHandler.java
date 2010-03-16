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

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Provider;

import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.IPreferenceChangeListener;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.PreferenceChangeEvent;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.e4.core.services.StatusReporter;
import org.eclipse.e4.core.services.annotations.PostConstruct;
import org.eclipse.e4.core.services.annotations.PreDestroy;
import org.eclipsecon.e4rover.core.ContestPlatform;

/**
 * A command handler that initiates a request for this player
 * to take control of the robot.
 */
public class RequestControlHandler {
	@Inject ContestPlatform platform;
	@Inject Provider<StatusReporter> statusReporter;

	/*
	 * Unfortunately we cannot inject preferences into handler instances at this
	 * moment. For now we hook into the preferences framework; see the end of
	 * this class for details.
	 */
	// FIXME: @Inject @Named("preference-PLAYER_KEY")
	String playerKey;

	public boolean canExecute() {
		if (playerKey == null || playerKey.trim().length() == 0) {
			statusReporter.get().show(StatusReporter.ERROR, "Unable to register: you must provide your player key",
					null);
			return false;
		}
		// we could check to see if we're already in the queue
		return true;
	}
	
	public void execute() {
		try {
			platform.enterPlayerQueue(playerKey);
		} catch (IOException e) {
			statusReporter.get().show(StatusReporter.ERROR, "An error occurred when trying to register for control", e);
		}
	}

	/*
	 * Old school preferences management
	 */
	IEclipsePreferences node;
	IPreferenceChangeListener listener = new IPreferenceChangeListener() {
		public void preferenceChange(PreferenceChangeEvent event) {
			if (event.getKey().equals("PLAYER_KEY")) {
				playerKey = (String) event.getNewValue();
			}
		}
	};

	@PostConstruct public void init() {
		// FrameworkUtil.getBundle(this).getSymbolicName()
		node = new InstanceScope().getNode("org.eclipsecon.e4rover.client");
		playerKey = (String) node.get("PLAYER_KEY", null);
		node.addPreferenceChangeListener(listener);
	}

	@PreDestroy public void dispose() {
		node.removePreferenceChangeListener(listener);
	}
}
