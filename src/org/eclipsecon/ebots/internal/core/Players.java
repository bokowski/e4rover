package org.eclipsecon.ebots.internal.core;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipsecon.ebots.core.IPlayers;

public class Players extends ServerObject implements IPlayers {

	Map<String, Player> playerMap = new HashMap<String, Player>();
	List<String> playerQueue = new LinkedList<String>();
	
	/* (non-Javadoc)
	 * @see org.eclipsecon.ebots.internal.core.IPlayers#getPlayerMap()
	 */
	public Map<String, Player> getPlayerMap() {
		return Collections.unmodifiableMap(playerMap);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipsecon.ebots.internal.core.IPlayers#getPlayerQueue()
	 */
	public List<String> getPlayerQueue() {
		return Collections.unmodifiableList(playerQueue);
	}
	
}
