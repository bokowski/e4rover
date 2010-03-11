package org.eclipsecon.ebots.core;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

public class TestERover {
	@Test
	public void testXstreamSerialization() throws IOException {
		TestServer server = new TestServer();
		Assert.assertTrue(server.getLatest(IGoal.class) instanceof IGoal);
		Assert.assertTrue(server.getLatest(IPlayer.class) instanceof IPlayer);
		Assert.assertTrue(server.getLatest(IRobot.class) instanceof IRobot);
		Assert.assertTrue(server.getLatest(IGame.class) instanceof IGame);
		Assert.assertTrue(server.getLatest(IPlayers.class) instanceof IPlayers);
		Assert.assertTrue(server.getLatest(IPlayerQueue.class) instanceof IPlayerQueue);
		Assert.assertTrue(server.getLatest(IArenaCamImage.class) instanceof IArenaCamImage);
	}
	
	@Test
	public void testSingletonObjectListener() throws InterruptedException {
		ContestPlatform cp = new ContestPlatform();
		cp.setServer(new TestServer());

		cp.addUpdateListener(new IUpdateListener() {

			public void gameUpdated(IGame game) {
				System.out.println(game.toString());
			}

			public void robotUpdated(IRobot robot) {
				System.out.println(robot.toString());
			}

			public void playersUpdated(IPlayers players) {
				System.out.println(players.toString());
			}
			
			public void playerQueueUpdated(IPlayerQueue queue) {
				System.out.println(queue.toString());
			}

			public void arenaCamViewUpdated(IArenaCamImage pq) {
				System.out.println(pq.toString());
			}

		});
		cp.startUpdateThread();
		Thread.sleep(10000);
		
		cp.stop();
		Assert.assertTrue(cp.exceptionsCount == 0);
	}
	
	

}
