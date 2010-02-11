package org.eclipsecon.ebots.internal.core;

import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.URIUtil;
import org.eclipsecon.ebots.core.ContestPlatform;
import org.eclipsecon.ebots.core.IPlayers;

public class Players extends ServerObject implements IPlayers {

	public static final String PLAYERS_FILE_NAME = "players.xml";
	public static final URI PLAYERS_FILE_URI = URIUtil.append(ContestPlatform.EROVER_SERVER_URI, PLAYERS_FILE_NAME);
	
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

	@Override
	protected URI getURI() {
		return PLAYERS_FILE_URI;
	}
	
}
