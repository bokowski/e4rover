package org.eclipsecon.ebots.internal.servers;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.eclipsecon.ebots.core.IArenaCamImage;
import org.eclipsecon.ebots.core.IDrive;
import org.eclipsecon.ebots.core.IGame;
import org.eclipsecon.ebots.core.IGoal;
import org.eclipsecon.ebots.core.IPlayer;
import org.eclipsecon.ebots.core.IPlayerQueue;
import org.eclipsecon.ebots.core.IPlayers;
import org.eclipsecon.ebots.core.IRobot;
import org.eclipsecon.ebots.core.IServerObject;
import org.eclipsecon.ebots.core.ITelemetry;
import org.eclipsecon.ebots.internal.core.ArenaCamImage;
import org.eclipsecon.ebots.internal.core.ServerObject;


public class TestServer extends AbstractServer {

	@Override
	public <T extends IServerObject> T getLatest(Class<T> desiredClass) throws IOException {
		Object result = null;
		try {
			if(desiredClass == IArenaCamImage.class) {
				// HACK: just for testing
				URI uri = new URI("http://www.collineduparlement-parliamenthill.gc.ca/text/newhillcam.jpg");
				result = new ArenaCamImage(getByteContents(uri));
			} else if(desiredClass == ITelemetry.class) {
				result = fromXML(generateTelemetry());
			} else if(desiredClass == IRobot.class) {
				result = fromXML(generateRobot());
			} else if(desiredClass == IPlayer.class) {
				result = fromXML(generatePlayer());
			} else if(desiredClass == IPlayers.class) {
				result = fromXML(generatePlayers());
			} else if(desiredClass == IPlayerQueue.class) {
				result = fromXML(generatePlayerQueue());
			} else if(desiredClass == IGame.class) {
				result = fromXML(generateGame());
			} else if(desiredClass == IGoal.class) {
				result = fromXML(generateGoal());
			} else if(desiredClass == IDrive.class) {
				result = fromXML(generateDrive());
			} 
		} catch(URISyntaxException e) { /* impossible! */ }
		if(result != null) {
			((ServerObject)result).setTimestamp(System.currentTimeMillis());
			return desiredClass.cast(result);
		}
		return null;
	}

	private String generatePlayer() {
		return "<player name=\"Jeff Norris\" timesPlayed=\"10\" bestScore=\"1234\"/>";
	}

	private String generatePlayerQueue() {
		return "<queue>\n" +
				"<player name=\"Khawaja Shams\" secondsToWait=\"150\" />\n" +
				"<player name=\"Jeff Norris\" secondsToWait=\"300\" />\n" +
				"</queue>";
	}

	private String generateGame() {
		return "<game t=\"123456789\" userComment=\"I rule!\" score=\"4\" secondsRemaining=\"150\">\n" +
			generateDrive() + "\n" + 
			generateTelemetry() + "\n" +
			generateGoal() + "\n" +
			"</game>";
	}
	
	private String generateGoal() {
		return "<goal last=\"A1\" next=\"B1\" nextReward=\"2\" />";
	}

	private String generateDrive() {
		return "<drive velocity=\"0\" radius=\"0\" distance=\"0\" />";
	}

	private String generatePlayers() {
		return "<players>\n" +
				(username == null ? "" : 
					"<player name=\"" + username + "\" timesPlayed=\"0\" bestScore=\"0\" />\n") +
				"<player name=\"Khawaja Shams\" timesPlayed=\"4\" bestScore=\"123\" />\n" +
				"<player name=\"Jeff Norris\"  timesPlayed=\"4\" bestScore=\"123\" />\n" +
				"</players>";
	}

	private String generateRobot() {
		return "<robot>\n" +
				"<batteryLevel>7657</batteryLevel>\n" +
				"<leftPower>40</leftPower>\n" +
				"<rightPower>40</rightPower>\n" +
				"<leftOdom>0</leftOdom>\n" +
				"<rightOdom>0</rightOdom>\n" +
				"</robot>";
	}

	private String generateTelemetry() {
		return "<telemetry>\n" +
				"<t k=\"voltage\" v=\"5\"/>\n" +
				"<t k=\"current\" v=\"5\"/>\n" +
				"</telemetry>";
	}

}
