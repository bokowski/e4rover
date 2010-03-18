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
import javax.inject.Provider;

import org.eclipse.e4.core.services.StatusReporter;
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

	/* SWT parent composite for this view */
	@Inject Composite parent;

	/*
	 * We are using an inner composite so that the group of buttons is centered
	 * in the view.
	 */
	Composite innerComposite;

	/*
	 * ContestPlatform is a domain-specific interface that is registered as an
	 * OSGi declarative service (see the ContestPlatform.java and
	 * contestplatform.xml files). OSGi services (declarative or not) are
	 * available to e4 parts through dependency injection.
	 */
	@Inject ContestPlatform platform;

	/*
	 * The styling engine allows you to assign CSS class names to widgets. See
	 * references to 'stylingEngine' further below for how it is used.
	 */
	@Inject IStylingEngine stylingEngine;

	/*
	 * The value of the "PLAYER_KEY" preference will be injected here. Any time
	 * the preference value changes, this field's value will be updated. (Behind
	 * the scenes, a preference listener will be managed by the framework.) If
	 * code should run in response to preference changes, use method injection
	 * instead of field injection - an example of this can be found in
	 * PlayersQueueView.java.
	 * 
	 * [Note that the weird 'preference' prefix is not going to be the final
	 * API, we are planning to replace the generic @Named annotation below with
	 * a more specific annotation @Preference("PLAYER_KEY") in the final API.]
	 */
	@Inject @Named("preference-PLAYER_KEY") String playerKey;

	/*
	 * Injecting a javax.inject.Provider instead of the status reporter itself
	 * means that the instance can be accessed lazily through Provider.get().
	 * This was not necessary for this view to work, we just wanted to
	 * demonstrate an exemplary use of Provider - the status reporter instance
	 * will only be needed if we need handle an exception.
	 */
	@Inject Provider<StatusReporter> statusReporter;

	/**
	 * Nickname of the current player.
	 */
	private String playerNick = "not logged in";

	private Label nickLabel;

	/*
	 * Methods annotated with @PostConstruct will be called after all values
	 * have been injected successfully. The equivalent for in 3.x would be
	 * createPartControl().
	 */
	@PostConstruct public void init() {
		parent.setLayout(new GridLayout());
		innerComposite = new Composite(parent, SWT.NONE);
		innerComposite.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true));

		// We're passing CSS class names as the first argument. See e4rover.css
		// for how this is used to put images in the buttons. The three numbers
		// are left wheel velocity, right wheel velocity (between -100 and +100)
		// and the duration in milliseconds for how long to keep the specified
		// velocity.
		createSpacer(2);
		createButton("fastforward", 50, 50, 1000);
		createSpacer(2);

		createSpacer(2);
		createButton("forward", 20, 20, 500);
		createSpacer(2);

		createButton("hardleft", -25, 25, 500);
		createButton("left", -8, 8, 500);

		nickLabel = new Label(innerComposite, SWT.NONE);
		nickLabel.setText(playerNick);
		nickLabel.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false));

		createButton("right", 8, -8, 500);
		createButton("hardright", 25, -25, 500);

		createSpacer(2);
		createButton("backward", -20, -20, 500);
		createSpacer(2);

		createSpacer(2);
		createButton("fastbackward", -50, -50, 1000);
		createSpacer(2);

		setButtonsEnabled(false);
		innerComposite.setLayout(new GridLayout(5, true));
	}

	/**
	 * Creates a number of empty labels to make the grid layout happy. An
	 * alternative would be to make the 'real' widget span multiple columns by
	 * setting their layout data accordingly, but that's much harder to set up
	 * correctly.
	 */
	private void createSpacer(int count) {
		for (int i = 0; i < count; i++) {
			new Label(innerComposite, SWT.NONE);
		}
	}

	/**
	 * Creates a button with the given CSS class that, when pressed, sends a
	 * command to the rover to set the left wheel and right wheel velocity to
	 * the given values for the given duration.
	 * 
	 * @param cssClass
	 *            css class name
	 * @param leftWheel
	 *            left wheel velocity, between -100 and +100
	 * @param rightWheel
	 *            right wheel velocity, between -100 and +100
	 * @param duration
	 *            amount of time to maintain the velocity, in milliseconds
	 * 
	 * @see ContestPlatform#setRobotWheelVelocity(int, int, String)
	 */
	private void createButton(String cssClass, final int leftWheel, final int rightWheel, final int duration) {
		final Button button = new Button(innerComposite, SWT.PUSH);
		stylingEngine.setClassname(button, cssClass);
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				try {
					platform.setRobotWheelVelocity(leftWheel, rightWheel, playerKey);
					Thread.sleep(duration);
					platform.setRobotWheelVelocity(0, 0, playerKey);
				} catch (Exception ex) {
					statusReporter.get().show(StatusReporter.ERROR, "Could not perform the selected rover command", ex);
				}
			}
		});
	}

	/*
	 * The value of the "PLAYER_KEY" preference will be injected here. Any time
	 * the preference value changes, this method will be called again. (Behind
	 * the scenes, a preference listener will be managed by the framework.) You
	 * can use field injection instead of method injection if no code needs to
	 * run in response to preference changes.
	 * 
	 * [Note that the weird 'preference' prefix is not going to be the final
	 * API, we are planning to replace the generic @Named annotation below with
	 * a more specific annotation @Preference("PLAYER_KEY") in the final API.]
	 */
	@Inject void setPlayerKey(@Named("preference-PLAYER_KEY") final String playerKey) {
		try {
			if (nickLabel != null) {
				nickLabel.setText("not logged in");
				// parent.layout(new Control[] { nickLabel }, SWT.DEFER);
			}
			playerNick = platform.fetchPlayerNick(playerKey);
			if (nickLabel != null) {
				nickLabel.setText(playerNick);
			}
		} catch (Exception e) {
			if (playerKey != null && !playerKey.trim().equals("") && !playerKey.trim().equals("not set")) {
				StatusReporter reporter = statusReporter.get();
				reporter.report(reporter.newStatus(StatusReporter.WARNING,
						"Could not retrieve the player nick name for this player key.", e), StatusReporter.LOG,
						playerKey);
			}
		}
	}

	/*
	 * The @UIEventHandlet annotation means that an OSGi event admin listener
	 * will be registered for us, and events of the given topic will cause this
	 * method to be called. @UIEventHandler methods will be called on the UI
	 * thread - if the thread is not important, use @EventHandler. At this time,
	 * we only support payload data that is passed in the OSGi Event object
	 * under the key IEventBroker#DATA. See ContestPlatform.java for the event
	 * producer side.
	 * 
	 * This method will enable or disable the control buttons so that the user
	 * has visual feedback as to when it is their turn.
	 */
	@UIEventHandler(IGame.TOPIC) void gameUpdated(final IGame game) {
		if (game != null && game.getCountdownSeconds() == 0 && game.getPlayerName() != null
				&& game.getPlayerName().equals(playerNick)) {
			setButtonsEnabled(true);
		} else {
			setButtonsEnabled(false);
		}
	}

	/*
	 * We're not only setting the SWT enabled property, but also changing the
	 * css class name of the inner composite, so that we can use grayed out
	 * images for the disabled buttons. See e4rover.css.
	 */
	private void setButtonsEnabled(boolean enabled) {
		stylingEngine.setClassname(innerComposite, enabled ? "enabled" : "disabled");
		Control[] controls = innerComposite.getChildren();
		for (int i = 0; i < controls.length; i++) {
			Control control = controls[i];
			if (control instanceof Button) {
				((Button) control).setEnabled(enabled);
			}
		}
	}
}
