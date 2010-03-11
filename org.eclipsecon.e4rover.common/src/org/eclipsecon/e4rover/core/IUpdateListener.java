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

import java.util.EventListener;

/**
 * Listener interface that allows users to receive updates when ContestPlatform
 * retrieves an updated version of one of the contest singleton classes.
 * 
 * Users should implement this interface and then register via
 * {@link ContestPlatform#addUpdateListener(IUpdateListener)}.
 * 
 * @see ContestPlatform
 */
public interface IUpdateListener extends EventListener {

	/**
	 * Called when the ContestPlatform has retrieved an updated copy of the Game object.  
	 * @param game the newly updated copy of the game object
	 */
	public void gameUpdated(IGame game);
	
	/**
	 * Called when the ContestPlatform has retrieved an updated copy of the Robot object.  
	 * @param robot the newly updated copy of the robot object
	 */
	public void robotUpdated(IRobot robot);
	
	/**
	 * Called when the ContestPlatform has retrieved an updated copy of the Players object.  
	 * @param players the newly updated copy of the players object
	 */
	public void playersUpdated(IPlayers players);

	/**
	 * Called when the ContestPlatform has retrieved an updated copy of the PlayerQueue object.  
	 * @param players the newly updated copy of the players object
	 */
	public void playerQueueUpdated(IPlayerQueue queue);

	/**
	 * Called when the ContestPlatform has retrieved an updated view from the ArenaCam.
	 * @param img the updated view
	 */
	public void arenaCamViewUpdated(IArenaCamImage img);

}
