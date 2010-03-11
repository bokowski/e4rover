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
	@Inject
	Composite parent;
	@Inject
	IStylingEngine stylingEngine;

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

	@PostConstruct
	public void init() {
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
		controls = new Control[] { timestampText, playerText, scoreText,
				secondsLabel, secondsText, targetRockLabel, targetRockText,
				targetInstrumentLabel, targetInstrumentText, rewardLabel,
				rewardText };
		GridLayoutFactory.fillDefaults().numColumns(2).generateLayout(parent);
	}

	@UIEventHandler(IGame.TOPIC)
	void gameUpdated(final IGame game) {
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
				stylingEngine.setClassname(targetRockText, targetRock
						.toString());
				INSTRUMENT targetInstrument = game.getNextGoal()
						.getInstrument();
				targetInstrumentText.setText(targetInstrument.toString());
				stylingEngine.setClassname(targetInstrumentText,
						targetInstrument.toString());
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
