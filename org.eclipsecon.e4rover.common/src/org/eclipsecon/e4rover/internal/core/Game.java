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
package org.eclipsecon.e4rover.internal.core;

import org.eclipsecon.e4rover.core.IGame;
import org.eclipsecon.e4rover.core.IGoal;

public class Game extends ServerObject implements IGame {

	public static final int FIRST_REWARD = 1;
	public static final int REWARD_STEP = 1;

	protected String playerName;
	protected int score;
	protected int countdownSeconds;
	protected int remainingSeconds;
	protected Goal lastGoal = null;
	protected Goal nextGoal = null;
	protected int nextReward;

	public String getPlayerName() {
		return playerName;
	}

	public int getScore() {
		return score;
	}

	public int getCountdownSeconds() {
		return countdownSeconds;
	}

	public int getRemainingSeconds() {
		return remainingSeconds;
	}

	public IGoal getLastGoal() {
		return lastGoal;
	}

	public IGoal getNextGoal() {
		return nextGoal;
	}

	public int getNextReward() {
		return nextReward;
	}
}
