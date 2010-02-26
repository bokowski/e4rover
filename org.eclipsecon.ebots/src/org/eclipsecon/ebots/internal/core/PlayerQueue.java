package org.eclipsecon.ebots.internal.core;

import java.util.Collections;
import java.util.List;

import org.eclipsecon.ebots.core.IPlayerQueue;

public class PlayerQueue extends ServerObject implements IPlayerQueue {
	final List<String> playerQueue;
	
	public PlayerQueue(List<String> playerQueue) {
		this.playerQueue = playerQueue;
	}
	
	
	
	/* (non-Javadoc)
	 * @see org.eclipsecon.ebots.internal.core.IPlayers#getPlayerQueue()
	 */
	public List<String> getPlayerQueue() {
		return Collections.unmodifiableList(playerQueue);
	}

}
