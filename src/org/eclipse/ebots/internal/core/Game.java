package org.eclipse.ebots.internal.core;

import com.thoughtworks.xstream.XStream;
import java.util.List;
import java.util.Random;
import org.eclipsecon.ebots.core.*;

public class Game implements IGame{

	public static final int GAME_LENGTH_SECONDS = 180;
	public static final int FIRST_REWARD = 1;
	public static final int REWARD_STEP = 1;
	public static final int COUNTDOWN_SECONDS = 3;
	private static XStream xstream;
	static {
		xstream = new XStream();
		xstream.alias("game", Game.class);
		xstream.omitField(Game.class, "gameService");
	}

	private static Game instance;
	
	String playerName;
	int score;
	int countdownSeconds;
	int remainingSeconds;
	Shot lastShot = null;
	Shot nextShot = null;
	int nextReward;
	
	GameServiceImpl gameService;

	/**
	 * Only the server gets to use this constructor. The client uses fromXML(),
	 * below.
	 */
	Game() {/*empty*/}
	
	/* server never calls this */
	public synchronized static Game getInstance() {
		if (instance == null) {
			instance = new Game();
			(new Thread() {
				@Override
				public void run() {
					// update from server, catching all exceptions
				}
			}).start();
		}
		return instance;
	}

	static Game fromXML(String xml) {
		return (Game) xstream.fromXML(xml);
	}
	
	public String getPlayer() {
		return playerName;
	}

	public int getCountdownSeconds() {
		return countdownSeconds;
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

	String toXML() {
		return toString();
	}
	
	@Override
	public String toString() {
		return xstream.toXML(this);
	}

	public static class Shot implements IShot{
		public final GOAL goal;
		public final PUCK puck;

		static final Random rand = new Random();

		public Shot(GOAL goal, PUCK puck) {
			this.goal = goal;
			this.puck = puck;
		}
		
		public Shot() {
			goal = GOAL.values()[rand.nextInt(3)];
			puck = PUCK.values()[rand.nextInt(4)];
		}
		
		@Override
		public boolean equals(Object obj) {
			return (obj instanceof Shot) && (((Shot)obj).goal == goal) && (((Shot)obj).puck == puck);
		}

		public GOAL getGoal() {
			return goal;
		}

		public PUCK getPuck() {
			return puck;
		}

	}

	public byte[] getCameraImage() {
		return null;
	}

	public int getCurrentScore() {
		return score;
	}

	public List<IPlayer> getPlayers() {
		return null;
	}

	public List<IPlayer> getQueue() {
		return null;
	}

	public IRobot getRobot() {
		return null;
	}

	public long getTimeRemaining() {
		return remainingSeconds * 1000;
	}

	public void setComment(String comment) {
	}

}
