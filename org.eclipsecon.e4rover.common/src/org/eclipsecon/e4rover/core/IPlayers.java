package org.eclipsecon.e4rover.core;

import java.util.List;
import java.util.Map;

/**
 * Contains all of the {@link IPlayer}s that have registered for the contest.
 * You can get an up-to-date version of this object via
 * {@link ContestPlatform#getPlayers()}.
 */
public interface IPlayers extends IGameObject {

	public static final String TOPIC = "org/eclipsecon/e4rover/players";

	/**
	 * Provides an unmodifiable map containing information about all of the
	 * registered players in the contest.
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