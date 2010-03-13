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

	/**
	 * Returns the robot's battery state of charge. New batteries give a value
	 * of about 9000. A value below 3000 indicates that the batteries need to be
	 * changed.
	 * 
	 * @return the current battery level of the robot.
	 */
	public int getBatteryLevel();

	/** @see ContestPlatform#setRobotWheelVelocity(int,int, String) */
	public int getLeftWheelVelocity();

	/** @see ContestPlatform#setRobotWheelVelocity(int,int, String) */
	public int getRightWheelVelocity();

	/**
	 * Each time the left wheel rotates forward one turn, this value increments.
	 * Each time it rotates backward, this value decrements. Note that this
	 * value is not in the same units as {@link #getPosX()} and
	 * {@link #getPosY()}.
	 * 
	 * @return the accumulated motor counts of the robot's left wheel
	 */
	public int getLeftWheelOdometry();

	/**
	 * Each time the right wheel rotates forward one turn, this value
	 * increments. Each time it rotates backward, this value decrements. Note
	 * that this value is not in the same units as {@link #getPosX()} and
	 * {@link #getPosY()}.
	 * 
	 * @return the accumulated motor counts of the robot's right wheel
	 */
	public int getRightWheelOdometry();

	/**
	 * Returns the robot's x position in arena coordinates. The origin of the
	 * arena coordinate system is in the center of the arena. When the arena is
	 * viewed from above with the "Adirondack" target at the bottom (this is how
	 * the arena camera is positioned), the positive x axis points right or east
	 * of the center, towards "Humphrey".
	 * 
	 * See http://ebots.s3.amazonaws.com/diagrams.pdf for a detailed diagram of
	 * the arena coordinate system.
	 * 
	 * @return the y coordinate of the robot's position in the arena
	 */
	public float getPosX();

	/**
	 * Returns the robot's y position in arena coordinates. The origin of the
	 * arena coordinate system is in the center of the arena. When the arena is
	 * viewed from above with the "Adirondack" target at the bottom (this is how
	 * the arena camera is positioned), the positive y axis points upwards or
	 * north of the center, towards "Mimi".
	 * 
	 * See http://ebots.s3.amazonaws.com/diagrams.pdf for a detailed diagram of
	 * the arena coordinate system.
	 * 
	 * @return the y coordinate of the robot's position in the arena
	 */
	public float getPosY();

	/**
	 * Returns the robot's heading within the arena coordinate system in
	 * positive degrees. When the arena is viewed from above with the
	 * "Adirondack" target at the bottom (this is how the arena camera is
	 * positioned), 0 degrees is to the right/east of center in the positive X
	 * direction (towards "Humphrey"). 90 degrees is upwards/north of center in
	 * the positive Y direction (towards "Mimi").
	 * 
	 * Heading angles increase counterclockwise when the arena is viewed from
	 * above, like the trigonometric unit circle.
	 * 
	 * See http://ebots.s3.amazonaws.com/diagrams.pdf for a detailed diagram of
	 * the arena coordinate system.
	 * 
	 * @return the robot's heading in the arena, between 0 degrees (inclusive)
	 *         and 360 degrees (exclusive)
	 */
	public float getHeading();

}
