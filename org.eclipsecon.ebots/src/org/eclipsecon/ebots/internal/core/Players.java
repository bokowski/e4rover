package org.eclipsecon.ebots.internal.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.eclipsecon.ebots.core.ContestPlatform;
import org.eclipsecon.ebots.core.IPlayer;
import org.eclipsecon.ebots.core.IPlayers;

public class Players extends ServerObject implements IPlayers {

	final Map<String, IPlayer> playerMap;
	

	/**
	 * For server use only. Clients should call {@link ContestPlatform#getPlayers()}
	 * @param playerMap
	 */
	public Players(Map<String, IPlayer> playerMap) {
		this.playerMap = playerMap;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipsecon.ebots.core.IPlayers#getPlayerMap()
	 */
	public Map<String, IPlayer> getPlayerMap() {
		return Collections.unmodifiableMap(playerMap);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipsecon.ebots.core.IPlayers#getTopPlayers()
	 */
	public List<IPlayer> getTopPlayers(int howMany) {
		if (howMany < 0)
			throw new IllegalArgumentException("Asking for " + howMany + " top players is invalid.");
		ArrayList<IPlayer> players = new ArrayList<IPlayer>(playerMap.values());
		Collections.sort(players);
		if (howMany == 0)
			howMany = players.size();
		howMany = Math.min(players.size(), howMany);
		
		return players.subList(0, howMany);
	}
}
