package org.eclipse.ebots.internal.core;


public class Player implements Comparable<Player>{

	private final String name;
	private int timesPlayed;
	private int bestScore;

	public Player(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public int getBestScore() {
		return bestScore;
	}

	public void setBestScore(int bestScore) {
		this.bestScore = bestScore;
	}

	public int getTimesPlayed() {
		return timesPlayed;
	}

	public void incrementTimesPlayed() {
		timesPlayed++;
	}

	public int compareTo(Player o) {
		return o.bestScore - bestScore;
	}
	
}


