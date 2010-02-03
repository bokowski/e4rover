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

import java.util.List;

/**
 * Represents a running robot challenge game. Clients can interact with the game
 * to both participate, or just to "watch" a game being played by others.
 */
public interface IGame {

	/**
	 * Returns an image captured from the game's arena camera.
	 * @return The bytes of a JPEG image
	 */
	public byte[] getCameraImage();
	
	/**
	 * Returns the score of the current player, or -1 if nobody is currently playing
	 * the game.
	 * @return The current game score
	 */
	public int getCurrentScore();
	
	/**
	 * Returns all players who have participated in this game.
	 * @return The game's players
	 */
	public List<IPlayer> getPlayers();
	
	/**
	 * Returns the ordered list of players who are currently waiting to play this game.
	 * @return The queue of waiting players
	 */
	public List<IPlayer> getQueue();
	
	/**
	 * Returns the game robot.
	 * @return the game robot
	 */
	public IRobot getRobot();
	
	/**
	 * Returns the remaining time, in milliseconds, for the current player to complete
	 * their goals.
	 * @return The remaining time for the current player, in milliseconds
	 */
	public long getTimeRemaining();
	
	/**
	 * Send a short comment to the game. The comment may be displayed on the game's 
	 * score board or similar display. Comments must be less than 140 characters in length.
	 * @param comment The comment to send to the game.
	 */
	public void setComment(String comment);

	/**
	 * Returns the next shot that needs to be performed by the active game player in
	 * order to score.
	 * @return the next shot in the game
	 */
	public IShot getNextShot();

	/**
	 * Returns the last shot that was successfully performed in the game, or <code>null</code>
	 * if no shot has ever been taken.
	 */
	public IShot getLastShot();

}
