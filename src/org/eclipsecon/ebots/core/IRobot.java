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
public interface IRobot extends IServerObject {
	
	public boolean commandWheelPower(int left, int right);
	
	public int getBatteryLevel();

	public int getLeftPower();

	public int getRightPower();

	public int getLeftOdom();

	public int getRightOdom();
}
