/*******************************************************************************
 * Copyright (c) 2010 Brian de Alwis and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Brian de Alwis - initial API and implementation
 ******************************************************************************/
package org.eclipsecon.e4rover;

import java.io.IOException;

import org.eclipsecon.e4rover.core.ContestPlatform;
import org.eclipsecon.e4rover.core.IGameObject;
import org.eclipsecon.e4rover.core.NotYourTurnException;

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
	 * @param playerKey TODO
	 * @throws IOException if there was an error in fulfilling the request
	 * @throws NotYourTurnException if this is not the current player
	 * @see ContestPlatform#setRobotWheelVelocity(int, int, String)
	 */
	public abstract void setWheelVelocity(int leftWheel, int rightWheel, String playerKey)
			throws IOException, NotYourTurnException;

	/**
	 * Indicate that this player would like to participate 
	 * @return the position of the player in the queue
	 * @throws IOException
	 */
	public abstract int enterPlayerQueue(String playerKey) throws IOException;

}