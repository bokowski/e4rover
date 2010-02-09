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
	 * their player is not currently the active game player.  Calling this method with
	 * 0,0 will stop the robot.
	 * @param left velocity for the left wheel, from -100 to 100
	 * @param right velocity for the right wheel, from -100 to 100
	 * @return <code>true</code> if the caller was allowed to drive the robot,
	 * and <code>false</code> otherwise
	 */
	public boolean commandWheelPower(int left, int right);
	
	public int getBatteryLevel();

	public int getLeftWheelPower();

	public int getRightWheelPower();

	public int getLeftTachoCount();

	public int getRightTachoCount();
}
