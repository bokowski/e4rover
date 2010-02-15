package org.eclipsecon.ebots.internal.core;

import org.eclipsecon.ebots.core.IGame;
import org.eclipsecon.ebots.core.IGoal;

public class Game extends ServerObject implements IGame{

	public static final int FIRST_REWARD = 1;
	public static final int REWARD_STEP = 1;

	String playerName;
	int score;
	int countdownSeconds;
	int remainingSeconds;
	Goal lastGoal = null;
	Goal nextGoal = null;
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

	public IGoal getLastGoal() {
		return lastGoal;
	}

	public IGoal getNextGoal() {
		return nextGoal;
	}

	public int getNextReward() {
		return nextReward;
	}

	public byte[] getCameraImage() {
		// TODO Needs to actually return an image!
		return null;
	}

	/* Game state changers - called only by server. */

	/**
	 * Called when a player has been identified and it is time to count down to
	 * the start of the game.
	 */
	public void enterCountdownState(String playerName) {
		System.out.println("Entering countdown state...");
		this.playerName = playerName;
		countdownSeconds = IGame.COUNTDOWN_SECONDS;
		gameStartTimeMillis = System.currentTimeMillis() + countdownSeconds*1000;
	}

	/**
	 * Advances the clock that is counting down the seconds before the beginning
	 * of the game. Returns true if more time remains, false otherwise.
	 * 
	 * @return true if more seconds remain before the game will begin, false if
	 *         the game has started or if no game is in progress.
	 */
	public boolean tickCountdownClock() {
		countdownSeconds = Math.round((gameStartTimeMillis - System.currentTimeMillis())/1000);
		if (countdownSeconds < 0) {
			countdownSeconds=0;
			return false;
		}
		return true;
	}

	/**
	 * Called when the game begins, after the pre-game countdown has finished.
	 * Only called by the server.
	 */
	public void enterPlayingState() {
		System.out.println("Entering playing state...");
		countdownSeconds = 0;
		remainingSeconds = GAME_LENGTH_SECONDS;
		lastGoal = null;
		nextGoal = new Goal();
		nextReward = FIRST_REWARD;
		score = 0;
		gameEndTimeMillis = System.currentTimeMillis() + (remainingSeconds * 1000);
	}

	/**
	 * Advances the clock that is counting down the seconds to the conclusion of
	 * the game. Returns true if more time remains, false otherwise.
	 * 
	 * @return true if more seconds remain on the clock, false if time has
	 *         elapsed or no game is in progress
	 */
	public boolean tickPlayClock() {
		remainingSeconds = Math.round((gameEndTimeMillis - System.currentTimeMillis())/1000);
		if (remainingSeconds < 0) {
			remainingSeconds = 0;
			return false;
		}
		return true;
	}

	/**
	 * Called when a goal attempt has been detected by the server. It is compared
	 * against the expected goal and if it matches, the player's score is
	 * increased appropriately.
	 * 
	 * @param goal the goal that has been detected
	 */
	public void handleGoal(IGoal goal) {
		System.out.println("Handling " + goal);
		if (goal.equals(nextGoal)) {
			System.out.println("Goal is good!");
			score += nextReward;
			nextReward++;
		} else {
			System.out.println("Goal is bad. Expected " + nextGoal);
			nextReward = Game.FIRST_REWARD;
		}
		// time for next goal
		lastGoal = nextGoal;
		nextGoal = new Goal();
	}

	/**
	 * Called after the game ends. Resets the game state in preparation for the
	 * next player.
	 */
	public void enterGameOverState() {
		remainingSeconds = 0;
		lastGoal = null;
		nextGoal = null;
		nextReward = -1;
		score = -1;
		playerName = null;
	}
	
}
