package org.eclipsecon.ebots.internal.core;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.eclipsecon.ebots.core.IPlayerQueue;

public class PlayerQueue extends ServerObject implements IPlayerQueue {
	Map<String, Integer> playerQueue = new HashMap<String, Integer>();
	
	/* (non-Javadoc)
	 * @see org.eclipsecon.ebots.internal.core.IPlayers#getPlayerQueue()
	 */
	public Map<String, Integer> getPlayerQueue() {
		return Collections.unmodifiableMap(playerQueue);
	}

}
