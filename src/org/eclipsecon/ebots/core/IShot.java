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
 * A shot represents a task to be performed by the robot. In particular, a shot
 * involves connecting a puck held by the robot with a goal attached to the perimeter
 * of the arena.
 */
public interface IShot {
	/**
	 * The goal enumeration lists the target RFID readers available on the arena
	 * perimeter. The goals are at 0, 120, and 240 degrees around the arena perimeter
	 */
	public enum GOAL {G1, G2,G3}
	
	/**
	 * The pucks represent RFID tags around the perimeter of the robot itself. The
	 * pucks are located at 45, 135, 225, and 315 degrees around the robot. Points
	 * are scored by aligning a puck on the robot with a goal on the arena perimeter.
	 */
	public enum PUCK {A, B,C, D}

	/**
	 * Returns the goal for this shot.
	 * @return the goal
	 */
	public GOAL getGoal();

	/**
	 * Returns the puck for this shot
	 * @return the puck.
	 */
	public PUCK getPuck();
}
