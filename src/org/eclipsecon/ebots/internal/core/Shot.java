package org.eclipsecon.ebots.internal.core;

import java.util.Random;

import org.eclipsecon.ebots.core.IShot;

public class Shot extends ServerObject implements IShot {

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
