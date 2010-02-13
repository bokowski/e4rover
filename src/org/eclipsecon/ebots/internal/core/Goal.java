package org.eclipsecon.ebots.internal.core;

import org.eclipsecon.ebots.core.IGoal;

public class Goal extends ServerObject implements IGoal {
	private String last;
	private String next;
	private int nextReward;
	
	public String getLast() {
		return last;
	}

	public String getNext() {
		return next;
	}

	public int getNextReward() {
		return nextReward;
	}

	public void setLast(String last) {
		this.last = last;
	}

	public void setNext(String next) {
		this.next = next;
	}

	public void setNextReward(int nextReward) {
		this.nextReward = nextReward;
	}

}
