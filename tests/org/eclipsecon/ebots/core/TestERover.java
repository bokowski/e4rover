package org.eclipsecon.ebots.core;

import org.junit.Test;

public class TestERover {
	
	@Test
	public void testSingletonObjectListener() throws InterruptedException {
		ContestPlatform.startUpdateThread();
		ContestPlatform.addUpdateListener(new IUpdateListener() {

			public void gameUpdated(IGame game) {
				System.out.println(game.toString());
			}

			public void robotUpdated(IRobot robot) {
				System.out.println(robot.toString());
			}

			public void playersUpdated(IPlayers players) {
				System.out.println(players.toString());
			}
			
		});
		Thread.sleep(10000);
	}

}
