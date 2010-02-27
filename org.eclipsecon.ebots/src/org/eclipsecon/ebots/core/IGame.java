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
 * Represents the game currently in progress. All players can access the current
 * game state by calling {@link ContestPlatform#getGame()} or by registering as
 * an {@link IUpdateListener} on {@link ContestPlatform} to receive periodic
 * updates.
 * 
 * At a minimum, the current game player needs to monitor the game state in
 * order to know what {@link Goal} they are trying to achieve and how much time
 * remains in the game (via {@link #getRemainingSeconds()}).
 * 
 * @see ContestPlatform
 */
public interface IGame extends IGameObject {

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
	 * is 0 if the game has already started or if no game is in progress.
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
	 * Returns the last goal that was attempted in the game, or <code>null</code>
	 * if no game is in progress.
	 */
	public IGoal getLastGoal();
	
	/**
	 * Returns the next goal that needs to be performed by the active game player in
	 * order to score, or <code>null</code> if no game is in progress.
	 * @return the next goal in the game
	 */
	public IGoal getNextGoal();

	/**
	 * Returns how many points the player will receive if he/she successfully
	 * completes the next goal. This is reset to 1 if the player fails to do so.
	 * Returns -1 if the game is not currently being played.
	 * 
	 * @return the number of points rewarded for the next goal, if successful.
	 */
	public int getNextReward();
	
}

