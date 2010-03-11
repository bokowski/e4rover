/*******************************************************************************
 * Copyright (c) 2010 Brian de Alwis and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Brian de Alwis - initial API and implementation
 ******************************************************************************/
package org.eclipsecon.e4rover.core;

import java.io.IOException;

import org.eclipsecon.e4rover.core.ContestPlatform;
import org.eclipsecon.e4rover.core.IArenaCamImage;
import org.eclipsecon.e4rover.core.IGame;
import org.eclipsecon.e4rover.core.IGoal;
import org.eclipsecon.e4rover.core.IPlayer;
import org.eclipsecon.e4rover.core.IPlayerQueue;
import org.eclipsecon.e4rover.core.IPlayers;
import org.eclipsecon.e4rover.core.IRobot;
import org.eclipsecon.e4rover.core.IUpdateListener;
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
