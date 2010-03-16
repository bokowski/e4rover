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

import org.eclipse.e4.core.services.annotations.PostConstruct;
import org.eclipse.e4.core.services.annotations.UIEventHandler;
import org.eclipse.e4.ui.services.IStylingEngine;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipsecon.e4rover.core.IGame;
import org.eclipsecon.e4rover.core.IGoal.INSTRUMENT;
import org.eclipsecon.e4rover.core.IGoal.TARGET;

public class GameView {
	/* SWT parent composite for this view */
	@Inject Composite parent;

	/*
	 * The styling engine allows you to assign CSS class names to widgets. See
	 * references to 'stylingEngine' further below for how it is used.
	 */
	@Inject IStylingEngine stylingEngine;

	/*
	 * Most of the code in this view is concerned with creating and setting the
	 * values of simple SWT widgets.
	 */
	private Text timestampText;
	private Text playerText;
	private Label secondsLabel;
	private Text secondsText;
	private Text scoreText;
	private Text targetRockText;
	private Text targetInstrumentText;
	private Text rewardText;
	private Label targetRockLabel;
	private Label targetInstrumentLabel;
	private Label rewardLabel;
	private Control[] controls;

	/*
	 * Methods annotated with @PostConstruct will be called after all values
	 * have been injected successfully. The equivalent for in 3.x would be
	 * createPartControl().
	 */
	@PostConstruct public void init() {
		new Label(parent, SWT.NONE).setText("Timestamp:");
		timestampText = new Text(parent, SWT.READ_ONLY);
		new Label(parent, SWT.NONE).setText("Player:");
		playerText = new Text(parent, SWT.READ_ONLY);
		new Label(parent, SWT.NONE).setText("Score:");
		scoreText = new Text(parent, SWT.READ_ONLY);
		secondsLabel = new Label(parent, SWT.NONE);
		secondsLabel.setText("Remaining seconds:");
		secondsText = new Text(parent, SWT.READ_ONLY);
		targetRockLabel = new Label(parent, SWT.NONE);
		targetRockLabel.setText("Target rock:");
		targetRockText = new Text(parent, SWT.READ_ONLY);
		targetInstrumentLabel = new Label(parent, SWT.NONE);
		targetInstrumentLabel.setText("Target instrument:");
		targetInstrumentText = new Text(parent, SWT.READ_ONLY);
		rewardLabel = new Label(parent, SWT.NONE);
		rewardLabel.setText("Reward:");
		rewardText = new Text(parent, SWT.READ_ONLY);
		// for layout purposes, remember all controls
		controls = new Control[] { timestampText, playerText, scoreText, secondsLabel, secondsText, targetRockLabel,
				targetRockText, targetInstrumentLabel, targetInstrumentText, rewardLabel, rewardText };
		GridLayoutFactory.fillDefaults().numColumns(2).generateLayout(parent);
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
	 * This method will update the SWT widgets with up to date information.
	 */
	@UIEventHandler(IGame.TOPIC) void gameUpdated(final IGame game) {
		if (parent != null && !parent.isDisposed()) {
			timestampText.setText("" + game.getTimestamp());
			String playerName = game.getPlayerName();
			playerText.setText(playerName == null ? "(nobody)" : playerName);
			scoreText.setText("" + game.getScore());
			if (game.getCountdownSeconds() > 0 || game.getNextGoal() == null) {
				secondsLabel.setText("Countdown:");
				secondsText.setText("" + game.getCountdownSeconds());
				setDetailWidgetVisibility(false);
			} else {
				secondsLabel.setText("Time remaining:");
				secondsText.setText("" + game.getRemainingSeconds());
				TARGET targetRock = game.getNextGoal().getTarget();
				targetRockText.setText(targetRock.toString());
				// we are using CSS class names so that we can set the
				// text field's background color using CSS. See the e4rover.css
				// file. This makes it easier to figure out for the user where
				// they are supposed to go with the robot.
				stylingEngine.setClassname(targetRockText, targetRock.toString());
				INSTRUMENT targetInstrument = game.getNextGoal().getInstrument();
				targetInstrumentText.setText(targetInstrument.toString());
				stylingEngine.setClassname(targetInstrumentText, targetInstrument.toString());
				rewardText.setText("" + game.getNextReward());
				setDetailWidgetVisibility(true);
			}
			parent.layout(controls, SWT.DEFER);
		}
	}

	private void setDetailWidgetVisibility(boolean visible) {
		targetRockLabel.setVisible(visible);
		targetRockText.setVisible(visible);
		targetInstrumentLabel.setVisible(visible);
		targetInstrumentText.setVisible(visible);
		rewardLabel.setVisible(visible);
		rewardText.setVisible(visible);
	};

}
