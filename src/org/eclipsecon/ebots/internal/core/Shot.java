package org.eclipsecon.ebots.internal.core;

import java.util.Random;

import org.eclipsecon.ebots.core.IShot;

public class Shot implements IShot {

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

	@Override
	public int hashCode() {
		return (goal.toString() + puck.toString()).hashCode();
	}
	
	public GOAL getGoal() {
		return goal;
	}

	public PUCK getPuck() {
		return puck;
	}

	@Override
	public String toString() {
		return "Shot: (puck= " + puck + ") (goal= " + goal + ")";
	}
	
	
}
