package org.eclipsecon.ebots.internal.core;

import java.net.URI;
import java.util.List;

import org.eclipse.core.runtime.URIUtil;
import org.eclipsecon.ebots.core.ContestPlatform;
import org.eclipsecon.ebots.core.IGame;
import org.eclipsecon.ebots.core.IPlayer;
import org.eclipsecon.ebots.core.IRobot;
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
	
	public static final String GAME_FILE_NAME = "game.xml";
	public static final URI GAME_FILE_URI = URIUtil.append(ContestPlatform.EROVER_SERVER_URI, GAME_FILE_NAME);

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

	@Override
	protected URI getURI() {
		return GAME_FILE_URI;
	}

}
