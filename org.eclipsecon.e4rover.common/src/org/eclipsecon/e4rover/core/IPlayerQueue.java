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

import java.util.List;

/**
 * Represents the list of {@link IPlayer}s waiting to play a {@link IGame}. The
 * {@link #IPlayer} objects in the contained list will be selected in order one
 * at a time to play the {@link IGame}.
 * 
 * To enter the queue, you need to call
 * {@link ContestPlatform#enterPlayerQueue(String)}.
 * 
 */
public interface IPlayerQueue extends IGameObject {
	
	public static final String TOPIC = "org/eclipsecon/e4rover/queue";

	/**
	 * Provides an unmodifiable list containing the names of all the players
	 * currently waiting to play the game, ordered by precedence.
	 * 
	 * @return an unmodifiable list of player names
	 */
	public List<String> getPlayerQueue();
}
