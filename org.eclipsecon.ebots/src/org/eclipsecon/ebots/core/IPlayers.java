package org.eclipsecon.ebots.core;

import java.util.List;
import java.util.Map;

public interface IPlayers extends IGameObject {

	/**
	 * Provides an unmodifiable map containing information about all of the
	 * registered players in the game.
	 * 
	 * @return an unmodifiable map of player names to player objects.
	 */
	public Map<String, IPlayer> getPlayerMap();

	/**
	 * Get the player list sorted by high score (descending). This method is
	 * useful for making a scoreboard.
	 * 
	 * @param howMany
	 *            the number of players to get. A value of 0 returns all players
	 * @return a list of players sorted by their score with howMany players in
	 *         it, or all if 0 is passed
	 */
	public List<IPlayer> getTopPlayers(int howMany);

}