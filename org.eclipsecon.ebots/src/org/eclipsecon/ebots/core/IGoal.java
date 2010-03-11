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

import org.eclipsecon.ebots.internal.core.Game;

/**
 * A Goal represents a task that the player must perform with the robot. More
 * specifically, a Goal requires aligning one of the four instruments held by
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
	 * The TARGET enumeration identifies the target RFID readers available on
	 * the arena perimeter. The goals are at 0, 90, 180, and 270 degrees.
	 */
	public enum TARGET {
		ADIRONDACK, MIMI, HUMPHREY, MAZATZAL
	}

	/**
	 * The INSTRUMENTs are represented as RFID tags around the perimeter of the
	 * robot at 45, 135, 225, and 315 degrees. Points are scored by aligning an
	 * instrument on the robot with a target on the site perimeter.
	 */
	public enum INSTRUMENT {
		MICROSCOPE, SPECTROMETER, DRILL, BRUSH
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
