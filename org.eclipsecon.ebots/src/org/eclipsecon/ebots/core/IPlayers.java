package org.eclipsecon.ebots.core;

import java.util.Map;

public interface IPlayers extends IGameObject {

	/**
	 * Provides an unmodifiable map containing information about all of the
	 * registered players in the game.
	 * 
	 * @return an unmodifiable map of player names to player objects.
	 */
	public Map<String, IPlayer> getPlayerMap();

}