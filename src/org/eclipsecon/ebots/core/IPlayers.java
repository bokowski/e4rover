package org.eclipsecon.ebots.core;

import java.util.List;
import java.util.Map;

import org.eclipsecon.ebots.internal.core.Player;

public interface IPlayers {

	public Map<String, Player> getPlayerMap();

	public List<String> getPlayerQueue();

}