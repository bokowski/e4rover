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
 * Contains publicly-accessible information for each player participating in the
 * contest. Up-to-date Player objects can be retrieved via
 * {@link ContestPlatform#getPlayers()}.
 * 
 * @see IPlayers
 */
public interface IPlayer extends IGameObject, Comparable<IPlayer> {

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
	
}
