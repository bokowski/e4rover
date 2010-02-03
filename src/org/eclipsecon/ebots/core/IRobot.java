/*******************************************************************************
 * Copyright (c) 2010 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipsecon.ebots.core;

import java.util.Map;

/**
 * This is the main interface for interacting with the robot participating in the game.
 * Any client can ask the robot for its state information, but only the client operated
 * by the active game player can manipulate the robot state (drive it).
 * 
 * With apologies to Asimov and Binder.
 */
public interface IRobot {
	
	/**
	 * Drives the robot according to the provided parameters. Returns whether
	 * the caller actually has permission to drive the robot. A return value of
	 * <code>false</code> indicates that the caller did not have permission because
	 * their player is not currently the active game player.
	 * @param velocity The average velocity at which the robot should move
	 * @param radius The radius of rotation, in degrees
	 * @param distance The distance to travel
	 * @return <code>true</code> if the caller was allowed to drive the robot,
	 * and <code>false</code> otherwise
	 */
	public boolean drive(int velocity, int radius, int distance);
	
	/**
	 * Returns the current telemetry for this robot.
	 * @return the robot's telemetry data
	 */
	public Map<String, String> getTelemetry();

}
