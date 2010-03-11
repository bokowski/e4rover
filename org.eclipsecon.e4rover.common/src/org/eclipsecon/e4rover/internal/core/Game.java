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
