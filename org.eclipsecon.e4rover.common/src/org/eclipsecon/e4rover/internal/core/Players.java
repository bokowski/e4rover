/*******************************************************************************
 * Copyright (c) 2010 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 ******************************************************************************/
package org.eclipsecon.e4rover.internal.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.eclipsecon.e4rover.core.ContestPlatform;
import org.eclipsecon.e4rover.core.IPlayer;
import org.eclipsecon.e4rover.core.IPlayers;

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
	 * @see org.eclipsecon.e4rover.core.IPlayers#getPlayerMap()
	 */
	public Map<String, IPlayer> getPlayerMap() {
		return Collections.unmodifiableMap(playerMap);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipsecon.e4rover.core.IPlayers#getTopPlayers()
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
