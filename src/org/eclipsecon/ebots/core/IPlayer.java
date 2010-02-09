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
 * Represents a player participating in a robot challenge {@link IGame}.
 */
public interface IPlayer {

	/**
	 * The highest score this player has achieved in the current game.
	 * @return The player's high score.
	 */
	public int getHighScore();
	
	/**
	 * Returns the name of this player
	 * @return the player name
	 */
	public String getName();
	
	/**
	 * Returns the number of times this player has participated in the game
	 * @return The player's play count
	 */
	public int getPlayCount();
	
	/**
	 * Returns the amount of time until this player will reach the top of the player
	 * queue and have another chance to play the game. Returns -1 if this player
	 * is not currently in the queue.
	 * @return The wait time in milliseconds, or -1 if this player is not waiting
	 */
	// TBD: Not sure how whether we'll represent this here.
//	public long getTimeToWait();
	

}
