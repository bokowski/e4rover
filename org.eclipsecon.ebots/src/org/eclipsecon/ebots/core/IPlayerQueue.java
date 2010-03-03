package org.eclipsecon.ebots.core;

import java.util.List;

/**
 * Represents the list of {@link IPlayer}s waiting to play a {@link IGame}. The
 * {@link #IPlayer} objects in the contained list will be selected in order one
 * at a time to play the {@link IGame}.
 * 
 * To enter the queue, you need to call
 * {@link ContestPlatform#enterPlayerQueue(String)}. Before you do this you need to
 * set your {@link IPlayer#MY_PLAYER_KEY}.
 * 
 */
public interface IPlayerQueue extends IGameObject {
	/**
	 * Provides an unmodifiable list containing the names of all the players
	 * currently waiting to play the game, ordered by precedence.
	 * 
	 * @return an unmodifiable list of player names
	 */
	public List<String> getPlayerQueue();
}
