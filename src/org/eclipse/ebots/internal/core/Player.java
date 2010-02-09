package org.eclipse.ebots.internal.core;

import org.eclipsecon.ebots.core.IGame;
import org.eclipsecon.ebots.core.IPlayer;


public class Player implements Comparable<Player>, IPlayer {

	private final String name;
	private int timesPlayed;
	private int bestScore;

	public Player(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public int getHighScore() {
		return bestScore;
	}

	public void setHighScore(int bestScore) {
		this.bestScore = bestScore;
	}

	public int getPlayCount() {
		return timesPlayed;
	}

	public void incrementPlayCount() {
		timesPlayed++;
	}

	public int compareTo(Player o) {
		return o.bestScore - bestScore;
	}

}


