/*******************************************************************************
 * Copyright (c) 2010 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 ******************************************************************************/
package org.eclipsecon.e4rover.core;

/**
 * Thrown when a user attempts to command the robot when it is not their turn.
 * If you want to play the {@link IGame}, you need to register and get in line
 * to play by calling {@link ContestPlatform#enterPlayerQueue(String)}.
 * 
 * @see ContestPlatform#setRobotWheelVelocity(int, int, String)
 */
public class NotYourTurnException extends Exception {

	private static final long serialVersionUID = 5114543999566297554L;

	public NotYourTurnException(String string) {
		super(string);
	}

}
