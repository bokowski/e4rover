package org.eclipsecon.ebots.internal.core;

import org.eclipsecon.ebots.core.IGame;
import org.eclipsecon.ebots.core.IShot;

public class Game extends ServerObject implements IGame{

	public static final int FIRST_REWARD = 1;
	public static final int REWARD_STEP = 1;

	String playerName;
	int score;
	int countdownSeconds;
	int remainingSeconds;
	Shot lastShot = null;
	Shot nextShot = null;
	int nextReward;
	
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

	public IShot getLastShot() {
		return lastShot;
	}

	public IShot getNextShot() {
		return nextShot;
	}

	public int getNextReward() {
		return nextReward;
	}

	public byte[] getCameraImage() {
		return null;
	}

}
