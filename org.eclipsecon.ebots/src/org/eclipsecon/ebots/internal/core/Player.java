package org.eclipsecon.ebots.internal.core;

import org.eclipsecon.ebots.core.IPlayer;

public class Player extends ServerObject implements IPlayer {

	private final String name;
	private int timesPlayed;
	private int highScore;

	public Player(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public int getHighScore() {
		return highScore;
	}

	public void setHighScore(int highScore) {
		this.highScore = highScore;
	}

	public int getPlayCount() {
		return timesPlayed;
	}

	public void incrementPlayCount() {
		timesPlayed++;
	}
	
	public void setTimesPlayed(int timesPlayed) {
		this.timesPlayed = timesPlayed;
	}

	public int compareTo(IPlayer o) {
		int diff =  o.getHighScore() - highScore;
		if (diff == 0)
			return o.getName().compareTo(getName());
		return diff; 
	}

	public void updateHighScore(int latestScore) {
		if (highScore < latestScore)
			highScore = latestScore;
	}
}


