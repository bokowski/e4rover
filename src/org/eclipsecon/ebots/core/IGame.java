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
 * Represents a running robot challenge game.
 */
public interface IGame {

	public static final int GAME_LENGTH_SECONDS = 180;
	public static final int COUNTDOWN_SECONDS = 3;

	/**
	 * Returns the name of the current player
	 * @return the current player's name
	 */
	public String getPlayerName();

	/**
	 * Returns the score of the current player, or -1 if nobody is currently playing
	 * the game.
	 * @return The current game score
	 */
	public int getScore();

	/**
	 * There is a brief pause of {@link #COUNTDOWN_SECONDS} seconds between
	 * games. This returns the number of seconds until the next game starts. It
	 * is 0 if a game is currently in progress.
	 * 
	 * @return the number of seconds until the next game starts
	 */
	public int getCountdownSeconds();

	/**
	 * Games last {@link #GAME_LENGTH_SECONDS} seconds. This method returns the
	 * remaining time for the current player to complete their goals.
	 * 
	 * @return The remaining time for the current player
	 */
	public int getRemainingSeconds();
	
	/**
	 * Returns the last shot that was successfully performed in the game, or <code>null</code>
	 * if no shot has ever been taken.
	 */
	public IShot getLastShot();
	
	/**
	 * Returns the next shot that needs to be performed by the active game player in
	 * order to score.
	 * @return the next shot in the game
	 */
	public IShot getNextShot();

	/**
	 * Returns how many points the player will receive if he/she successfully
	 * completes the next shot. This is reset to 1 if the player fails to do so.
	 * 
	 * @return the number of points rewarded for the next shot, if successful.
	 */
	public int getNextReward();
	
	/**
	 * Returns an image captured from the game's arena camera.
	 * @return The bytes of a JPEG image
	 */
	public byte[] getCameraImage();
	
}

