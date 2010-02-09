package org.eclipsecon.ebots.internal.core;

import org.eclipsecon.ebots.internal.core.Player;
import org.eclipsecon.ebots.internal.core.Players;

import junit.framework.Assert;

import org.junit.Test;

public class PlayersTest {

	@Test
	public void testXStreaming() {
		Players players = Players.instance;
		Player jeff = new Player("Jeff");
		jeff.incrementPlayCount();
		jeff.setHighScore(9999);
		players.put(jeff.getName(), jeff);
		players.put("Khawaja", new Player("Khawaja"));
		
		Assert.assertEquals(players.toXML(), players.fromXML(players.toXML()).toXML());
	}
	
}
