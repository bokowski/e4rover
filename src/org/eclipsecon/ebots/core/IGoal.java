package org.eclipsecon.ebots.core;

public interface IGoal extends IServerObject {
	/** @return the last goal accomplished */
	public String getLast();
	
	/** @return the next goal to be attempted */
	public String getNext();
	
	/** @return the reward for achieving the next goal */
	public int getNextReward();
}
