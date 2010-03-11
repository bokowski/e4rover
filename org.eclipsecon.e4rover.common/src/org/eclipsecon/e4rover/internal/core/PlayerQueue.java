package org.eclipsecon.e4rover.internal.core;

import java.util.Collections;
import java.util.List;

import org.eclipsecon.e4rover.core.IPlayerQueue;

public class PlayerQueue extends ServerObject implements IPlayerQueue {
	final List<String> playerQueue;
	
	public PlayerQueue(List<String> playerQueue) {
		this.playerQueue = playerQueue;
	}
	
	
	
	/* (non-Javadoc)
	 * @see org.eclipsecon.e4rover.internal.core.IPlayers#getPlayerQueue()
	 */
	public List<String> getPlayerQueue() {
		return Collections.unmodifiableList(playerQueue);
	}

}
