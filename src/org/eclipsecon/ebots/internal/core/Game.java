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
	private long gameStartTimeMillis;
	private long gameEndTimeMillis;
	
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
		// TODO Needs to actually return an image!
		return null;
	}

	/* Game state changers - called only by server */

	public void enterCountdownState(String playerName) {
		System.out.println("Entering countdown state...");
		this.playerName = playerName;
		countdownSeconds = IGame.COUNTDOWN_SECONDS;
		gameStartTimeMillis = System.currentTimeMillis() + countdownSeconds*1000;
	}
	
	public boolean tickCountdownClock() {
		countdownSeconds = Math.round((gameStartTimeMillis - System.currentTimeMillis())/1000);
		if (countdownSeconds < 0) {
			countdownSeconds=0;
			return false;
		}
		return true;
	}
	
	public void enterPlayingState() {
		System.out.println("Entering playing state...");
		countdownSeconds = 0;
		remainingSeconds = GAME_LENGTH_SECONDS;
		lastShot = null;
		nextShot = new Shot();
		nextReward = FIRST_REWARD;
		score = 0;
		gameEndTimeMillis = System.currentTimeMillis() + (remainingSeconds * 1000);
	}

	public boolean tickPlayClock() {
		remainingSeconds = Math.round((gameEndTimeMillis - System.currentTimeMillis())/1000);
		if (remainingSeconds < 0) {
			remainingSeconds = 0;
			return false;
		}
		return true;
	}
	
	public void handleShot(IShot shot) {
		System.out.println("Handling " + shot);
		if (shot.equals(nextShot)) {
			System.out.println("Shot is good!");
			score += nextReward;
			nextReward++;
		} else {
			System.out.println("Shot is bad. Expected " + nextShot);
			nextReward = Game.FIRST_REWARD;
		}
		// time for next shot
		lastShot = nextShot;
		nextShot = new Shot();
	}
	
	public void enterGameOverState() {
		remainingSeconds = 0;
		lastShot = null;
		nextShot = null;
		nextReward = 0;
	}
	
}
