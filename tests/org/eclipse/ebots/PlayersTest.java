package org.eclipse.ebots;

import junit.framework.Assert;

import org.eclipse.ebots.Player;
import org.eclipse.ebots.Players;
import org.junit.Test;

public class PlayersTest {

	@Test
	public void testXStreaming() {
		Players players = Players.instance;
		Player jeff = new Player("Jeff");
		jeff.incrementTimesPlayed();
		jeff.setBestScore(9999);
		players.put(jeff.getName(), jeff);
		players.put("Khawaja", new Player("Khawaja"));
		
		Assert.assertEquals(players.toXML(), players.fromXML(players.toXML()).toXML());
	}
	
}
