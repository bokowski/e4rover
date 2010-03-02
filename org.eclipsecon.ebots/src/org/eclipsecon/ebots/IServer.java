package org.eclipsecon.ebots;

import java.io.IOException;

import org.eclipsecon.ebots.core.ContestPlatform;
import org.eclipsecon.ebots.core.IGameObject;
import org.eclipsecon.ebots.core.NotYourTurnException;

public interface IServer {

	/**
	 * Return a server object representing some aspect of the game state
	 * @param desiredClass the class of the object you want to retrieve
	 * @return a deserialized object of desiredClass
	 * @throws IOException if a problem occurs while fetching the object
	 */
	public abstract <T extends IGameObject> T getLatest(Class<T> desiredClass)
			throws IOException;

	/**
	 * Set the robot's velocities on a per-wheel basis
	 * @param leftWheel velocity in cm/s
	 * @param rightWheel velocity in cm/s
	 * @throws IOException if there was an error in fulfilling the request
	 * @throws NotYourTurnException if this is not the current player
	 * @see ContestPlatform#setRobotWheelVelocity(int, int)
	 */
	public abstract void setWheelVelocity(int leftWheel, int rightWheel)
			throws IOException, NotYourTurnException;

	/**
	 * Indicate that this player would like to participate 
	 * @return the position of the player in the queue
	 * @throws IOException
	 */
	public abstract int enterPlayerQueue() throws IOException;

}