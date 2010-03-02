package org.eclipsecon.ebots.internal.servers;

import java.io.IOException;

import org.eclipsecon.ebots.core.IArenaCamImage;
import org.eclipsecon.ebots.core.IGame;
import org.eclipsecon.ebots.core.IGameObject;
import org.eclipsecon.ebots.core.IPlayerQueue;
import org.eclipsecon.ebots.core.IPlayers;
import org.eclipsecon.ebots.core.IRobot;
import org.eclipsecon.ebots.internal.core.ArenaCamImage;
import org.eclipsecon.ebots.internal.core.ServerObject;


public class TestServer extends AbstractServer {

	@Override
	public <T extends IGameObject> T getLatest(Class<T> desiredClass) throws IOException {
		Object result = null;
		if(desiredClass == IArenaCamImage.class) {
			// HACK: just for testing
			String uri = "http://www.collineduparlement-parliamenthill.gc.ca/text/newhillcam.jpg";
			result = new ArenaCamImage(getContents(uri));
		} else if(desiredClass == IRobot.class) {
			result = fromXML(generateRobot());
		} else if(desiredClass == IPlayers.class) {
			result = fromXML(generatePlayers());
		} else if(desiredClass == IPlayerQueue.class) {
			result = fromXML(generatePlayerQueue());
		} else if(desiredClass == IGame.class) {
			result = fromXML(generateGame());
		}
		if(result != null) {
			((ServerObject)result).setTimestamp(System.currentTimeMillis());
			return desiredClass.cast(result);
		}
		return null;
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

	@Override
	public void setWheelVelocity(int leftWheel, int rightWheel)
	throws IOException {
		// do nothing
	}
	
	@Override
	public int enterPlayerQueue() throws IOException {
	    // do nothing
		return 0;
	}
}
