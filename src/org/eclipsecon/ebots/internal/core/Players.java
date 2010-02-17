package org.eclipsecon.ebots.internal.core;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.eclipsecon.ebots.core.IPlayer;
import org.eclipsecon.ebots.core.IPlayers;

public class Players extends ServerObject implements IPlayers {

	Map<String, IPlayer> playerMap = new HashMap<String, IPlayer>();
	
	/* (non-Javadoc)
	 * @see org.eclipsecon.ebots.internal.core.IPlayers#getPlayerMap()
	 */
	public Map<String, IPlayer> getPlayerMap() {
		return Collections.unmodifiableMap(playerMap);
	}
	
}
