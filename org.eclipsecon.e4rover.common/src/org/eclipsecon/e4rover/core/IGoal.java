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

import org.eclipsecon.e4rover.internal.core.Game;

/**
 * A Goal represents a task that the player must perform with the robot. More
 * specifically, a Goal requires aligning one of the two instruments held by
 * the robot with one of the four targets attached to the perimeter of the
 * arena. When a Goal is successfully completed, a reward is given to the player
 * and a new Goal is generated.
 * 
 * The current Goal can be accessed by retrieving the current Game state via
 * {@link ContestPlatform#getGame()} and calling the {@link Game#getNextGoal()}
 * method.
 * 
 * @see IGame
 */
public interface IGoal extends IGameObject {

	/**
	 * The TARGET enumeration identifies the four target RFID readers available
	 * on the arena perimeter. HUMPHREY is at 0 degrees (on the right side of
	 * the arena when viewed from above), MIMI is at 90 degrees (at the top of
	 * the arena when viewed from above), MAZATZAL is at 180 degrees, and
	 * ADIRONDACK is at 270 degrees.
	 * 
	 * See http://ebots.s3.amazonaws.com/diagrams.pdf for a detailed diagram of
	 * the arena targets.
	 * 
	 */
	public enum TARGET {
		ADIRONDACK, MIMI, HUMPHREY, MAZATZAL
	}

	/**
	 * The INSTRUMENTs are represented as RFID tags on the front and rear of the
	 * robot. Points are scored by aligning an instrument on the robot with a
	 * target on the site perimeter.  The DRILL is on the front of the robot and
	 * the SPECTROMETER is on the rear of the robot.  
	 * 
	 * See http://ebots.s3.amazonaws.com/diagrams.pdf for a detailed diagram of
	 * the robot showing the instrument positions.
	 * 
	 */
	public enum INSTRUMENT {
		SPECTROMETER, DRILL
	}

	/**
	 * @return the target for this goal.
	 */
	public TARGET getTarget();

	/**
	 * @return the instrument for this goal.
	 */
	public INSTRUMENT getInstrument();
}
