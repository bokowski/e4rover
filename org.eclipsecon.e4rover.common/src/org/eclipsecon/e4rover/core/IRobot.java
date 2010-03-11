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
package org.eclipsecon.e4rover.core;

/**
 * This is the interface for accessing the current state of the robot. Note that
 * anyone can monitor the robot even if they are not controlling the robot.
 * 
 * Retrieve the up-to-date version of this object from
 * {@link ContestPlatform#getRobot()} or by registering as an
 * {@link IUpdateListener} on {@link ContestPlatform}.
 * 
 * With apologies to Asimov and Binder.
 * 
 * @see ContestPlatform
 */
public interface IRobot extends IGameObject {

	public static final String TOPIC = "org/eclipsecon/e4rover/robot";

	public int getBatteryLevel();

	/** @see ContestPlatform#setRobotWheelVelocity(int,int, String) */
	public int getLeftWheelVelocity();

	/** @see ContestPlatform#setRobotWheelVelocity(int,int, String) */
	public int getRightWheelVelocity();

	/**
	 * @return the distance travelled by the robot's left wheel
	 */
	public int getLeftWheelOdometry();

	/**
	 * @return the distance travelled by the robot's right wheel
	 */
	public int getRightWheelOdometry();
}
