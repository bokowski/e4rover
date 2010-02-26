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
public interface IPlayer extends IGameObject, Comparable<IPlayer> {

	/**
	 * PLAYER_KEY uniquely identifies you to the game server and allows you to
	 * command the robot.
	 * 
	 * IMPORTANT: You *must* paste the secret key you received in your
	 * registration confirmation email into the PLAYER_KEY field. If you do not
	 * have a player key, visit the arena in the Magnolia room and register with
	 * the arena attendant.
	 * 
	 * Do NOT share your PLAYER_KEY with anyone!
	 */
	public static final String MY_PLAYER_KEY = "c2c9eff1469b0148bb98de69ad6f6b611abff90c";

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
