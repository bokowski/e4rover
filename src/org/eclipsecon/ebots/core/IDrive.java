package org.eclipsecon.ebots.core;

public interface IDrive extends IServerObject {
	/** @return the robot's current velocity */
	int getVelocity();
	
	/** @return the robot's current radius 
	 * FIXME: is this relative or absolute? */
	int getRadius();
	
	/** @return the distance
	 * FIXME: from where? */
	int getDistance();

}
