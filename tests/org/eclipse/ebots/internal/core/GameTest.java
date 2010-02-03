package org.eclipse.ebots.internal.core;


import org.eclipse.ebots.internal.core.Game;

import junit.framework.Assert;

import org.junit.Test;


public class GameTest {

	@Test
	public void testXStreaming() {
		Game game = Game.getInstance();
		Assert.assertEquals(game.toXML(), Game.fromXML(game.toXML()).toXML());
	}
	

}
