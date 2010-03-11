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
import java.net.URL;

import org.eclipsecon.e4rover.IServer;
import org.eclipsecon.e4rover.core.IArenaCamImage;
import org.eclipsecon.e4rover.core.IGame;
import org.eclipsecon.e4rover.core.IGameObject;
import org.eclipsecon.e4rover.core.IGoal;
import org.eclipsecon.e4rover.core.IPlayer;
import org.eclipsecon.e4rover.core.IPlayerQueue;
import org.eclipsecon.e4rover.core.IPlayers;
import org.eclipsecon.e4rover.core.IRobot;
import org.eclipsecon.e4rover.core.XmlSerializer;
import org.eclipsecon.e4rover.internal.core.ArenaCamImage;
import org.eclipsecon.e4rover.internal.core.ServerObject;


public class TestServer implements IServer {

	protected XmlSerializer xmlserializer = new XmlSerializer();

	public <T extends IGameObject> T getLatest(Class<T> desiredClass) throws IOException {
		Object result = null;
		if(desiredClass == IArenaCamImage.class) {
			// HACK: just for testing
			String uri = "http://www.collineduparlement-parliamenthill.gc.ca/text/newhillcam.jpg";
			result = new ArenaCamImage((byte[])new URL(uri).getContent());
		} else if(desiredClass == IRobot.class) {
			result = xmlserializer.fromXML(generateRobot());
		} else if(desiredClass == IPlayers.class) {
			result = xmlserializer.fromXML(generatePlayers());
		} else if(desiredClass == IPlayerQueue.class) {
			result = xmlserializer.fromXML(generatePlayerQueue());
		} else if(desiredClass == IGame.class) {
			result = xmlserializer.fromXML(generateGame());
		} else if(desiredClass == IGoal.class) {
			result = xmlserializer.fromXML(generateGoal());
		} else if(desiredClass == IPlayer.class) {
			result = xmlserializer.fromXML(generatePlayer());
		}
		if(result != null) {
			((ServerObject)result).setTimestamp(System.currentTimeMillis());
			return desiredClass.cast(result);
		}
		return null;
	}

	private String generatePlayer() {
		return "<player name=\"Khawaja Shams\" secondsToWait=\"150\" />\n";
	}

	private String generateGoal() {
		return "<goal target='ADIRONDACK' instrument='MICROSCOPE'/>";
	}

	private String generatePlayerQueue() {
		return "<queue>\n" +
		"<player name=\"Khawaja Shams\" secondsToWait=\"150\" />\n" +
		"<player name=\"Jeff Norris\" secondsToWait=\"300\" />\n" +
		"</queue>";
	}

	private String generateGame() {
		return "<game> " +
		"<playerName>Bradbury</playerName> " +
		"<score>35</score> " +
		"<countdownSeconds>1</countdownSeconds> " +
		"<remainingSeconds>99</remainingSeconds> " +
		"<lastGoal> " +
		" <target>ADIRONDACK</target> " +
		" <instrument>MICROSCOPE</instrument> " +
		"</lastGoal> " +
		"<nextGoal> " +
		"  <target>MAZATZAL</target> " +
		"  <instrument>BRUSH</instrument> " +
		"</nextGoal> " +
		"<nextReward>42</nextReward> " +
		"</game>";
	}

	private String generatePlayers() {
		return "<players>\n" +
		"<player name=\"Khawaja Shams\" timesPlayed=\"4\" bestScore=\"123\" />\n" +
		"<player name=\"Jeff Norris\"  timesPlayed=\"4\" bestScore=\"123\" />\n" +
		"</players>";
	}

	private String generateRobot() {
		return "<robot>\n" +
		"<batteryLevel>7657</batteryLevel>\n" +
		"<leftWheelVelocity>40</leftWheelVelocity>\n" +
		"<rightWheelVelocity>40</rightWheelVelocity>\n" +
		"<leftWheelOdometry>0</leftWheelOdometry>\n" +
		"<rightWheelOdometry>0</rightWheelOdometry>\n" +
		"</robot>";
	}

	public void setWheelVelocity(int leftWheel, int rightWheel, String playerKey)
			throws IOException {
		// do nothing
	}
	
	public int enterPlayerQueue(String playerKey) throws IOException {
	    // do nothing
		return 0;
	}
}
