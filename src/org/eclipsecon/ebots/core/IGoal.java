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
 * A goal represents a task to be performed by the robot. In particular, a goal
 * involves aligning an instrument held by the robot with a target attached to
 * the perimeter of the site.
 */
public interface IGoal extends IGameObject {
	/**
	 * The TARGET enumeration lists the target RFID readers available on the site
	 * perimeter. The goals are at 0, 120, and 240 degrees around the arena perimeter
	 */
	public enum TARGET {G1, G2,G3}

	/**
	 * The INSTRUMENTs are represented as RFID tags around the perimeter of the
	 * robot at 45, 135, 225, and 315 degree. Points are scored by aligning an
	 * instrument on the robot with a target on the site perimeter.
	 */
	public enum INSTRUMENT {MICROSCOPE, SPECTROMETER, DRILL, BRUSH}

	/**
	 * Returns the target for this goal.
	 * @return the target
	 */
	public TARGET getTarget();

	/**
	 * Returns the instrument for this goal
	 * @return the instrument.
	 */
	public INSTRUMENT getInstrument();
}
