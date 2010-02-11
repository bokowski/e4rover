package org.eclipsecon.ebots.core;

import java.util.List;
import java.util.Map;

import org.eclipsecon.ebots.internal.core.Player;

public interface IPlayers {

	/**
	 * Provides an unmodifiable map containing information about all of the
	 * registered players in the game.
	 * 
	 * @return an unmodifiable map of player names to player objects.
	 */
	public Map<String, Player> getPlayerMap();

	/**
	 * Provides an unmodifiable list containing the names of all the players
	 * currently waiting to play the game, ordered by precedence.
	 * 
	 * @return an unmodifiable list of player names
	 */
	public List<String> getPlayerQueue();

}