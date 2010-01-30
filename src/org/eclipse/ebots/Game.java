package org.eclipse.ebots;

import java.util.Random;

import com.thoughtworks.xstream.XStream;

public class Game {

	public enum GOAL {R, G, B}
	public enum PUCK {W, X, Y, Z}
	
	public static final int GAME_LENGTH_SECONDS = 180;
	public static final int FIRST_REWARD = 1;
	public static final int REWARD_STEP = 1;
	public static final int COUNTDOWN_SECONDS = 3;
	private static XStream xstream;
	static {
		xstream = new XStream();
		xstream.alias("game", Game.class);
	}

	private static Game instance;
	
	String playerName;
	int score;
	int countdownSeconds;
	int remainingSeconds;
	Shot lastShot = null;
	Shot nextShot = null;
	int nextReward;

	Game() {
		/*empty*/
	}
	
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

	public int getScore() {
		return score;
	}

	public int getCountdownSeconds() {
		return countdownSeconds;
	}

	public int getRemainingSeconds() {
		return remainingSeconds;
	}

	public Shot getLastShot() {
		return lastShot;
	}

	public Shot getNextShot() {
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

	public static class Shot {
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

	}

}
