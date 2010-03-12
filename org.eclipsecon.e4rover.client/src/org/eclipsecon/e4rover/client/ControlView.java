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

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.e4.core.services.annotations.PostConstruct;
import org.eclipse.e4.core.services.annotations.UIEventHandler;
import org.eclipse.e4.ui.services.IStylingEngine;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipsecon.e4rover.core.ContestPlatform;
import org.eclipsecon.e4rover.core.IGame;

public class ControlView {

	@Inject Composite outerParent;

	Composite parent;
	@Inject ContestPlatform platform;
	@Inject IStylingEngine stylingEngine;
	@Inject @Named("preference-PLAYER_KEY") String playerKey;

	@PostConstruct public void init() {
		outerParent.setLayout(new GridLayout());
		parent = new Composite(outerParent, SWT.NONE);
		parent.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true));

		createSpacer(2);
		createButton("fastforward", 50, 50, 1000);
		createSpacer(2);

		createSpacer(2);
		createButton("forward", 20, 20, 500);
		createSpacer(2);

		createButton("hardleft", 20, -20, 500);
		createButton("left", 5, -5, 500);
		createSpacer(1);
		createButton("right", -5, 5, 500);
		createButton("hardright", -20, 20, 500);

		createSpacer(2);
		createButton("backward", -20, -20, 500);
		createSpacer(2);

		createSpacer(2);
		createButton("fastbackward", -50, -50, 1000);
		createSpacer(2);

		setButtonsEnabled(false);
		parent.setLayout(new GridLayout(5, true));
		// GridLayoutFactory.fillDefaults().numColumns(5).generateLayout(parent);
	}

	private void createSpacer(int count) {
		for (int i = 0; i < count; i++) {
			new Label(parent, SWT.NONE);
		}
	}

	private void createButton(String label, final int leftMotor, final int rightMotor, final int duration) {
		final Button button = new Button(parent, SWT.PUSH);
		// button.setText(label);
		stylingEngine.setClassname(button, label);
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				try {
					platform.setRobotWheelVelocity(leftMotor, rightMotor, playerKey);
					Thread.sleep(duration);
					platform.setRobotWheelVelocity(0, 0, playerKey);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
	}

	@Inject @Named("preference-PLAYER_NICK") String playerNick;

	@UIEventHandler(IGame.TOPIC) void gameUpdated(final IGame game) {
		if (game != null && game.getCountdownSeconds() == 0 && game.getPlayerName() != null
				&& game.getPlayerName().equals(playerNick)) {
			setButtonsEnabled(true);
		} else {
			setButtonsEnabled(false);
		}
	}

	private void setButtonsEnabled(boolean enabled) {
		stylingEngine.setClassname(parent, enabled ? "enabled" : "disabled");
		Control[] controls = parent.getChildren();
		for (int i = 0; i < controls.length; i++) {
			Control control = controls[i];
			if (control instanceof Button) {
				((Button) control).setEnabled(enabled);
			}
		}
	}
}
