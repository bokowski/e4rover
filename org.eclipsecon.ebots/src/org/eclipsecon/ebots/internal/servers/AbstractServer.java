package org.eclipsecon.ebots.internal.servers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.io.IOUtils;
import org.eclipsecon.ebots.core.IGame;
import org.eclipsecon.ebots.core.IGameObject;
import org.eclipsecon.ebots.core.IGoal;
import org.eclipsecon.ebots.core.IPlayer;
import org.eclipsecon.ebots.core.IPlayerQueue;
import org.eclipsecon.ebots.core.IPlayers;
import org.eclipsecon.ebots.core.IRobot;
import org.eclipsecon.ebots.core.NotYourTurnException;
import org.eclipsecon.ebots.internal.core.Game;
import org.eclipsecon.ebots.internal.core.Goal;
import org.eclipsecon.ebots.internal.core.Player;
import org.eclipsecon.ebots.internal.core.PlayerQueue;
import org.eclipsecon.ebots.internal.core.Players;
import org.eclipsecon.ebots.internal.core.Robot;

import com.thoughtworks.xstream.XStream;

public abstract class AbstractServer {

	/** Static XStream instance, used to serialize and deserialize all contest objects */  
	public static final XStream xstream;

	public static final HttpClient httpClient = new HttpClient(new MultiThreadedHttpConnectionManager());

	static {
		// configure XStream to produce prettier XML
		xstream = new XStream();
		xstream.alias("game", IGame.class, Game.class);
		xstream.alias("robot", IRobot.class, Robot.class);	
		xstream.alias("players", IPlayers.class, Players.class);
		xstream.alias("player", IPlayer.class, Player.class);
		xstream.alias("queue", IPlayerQueue.class, PlayerQueue.class);
		xstream.alias("goal", IGoal.class, Goal.class);
	}

	public static String toXML(Object o) {
		return xstream.toXML(o);
	}

	public static Object fromXML(String s) {
		return xstream.fromXML(s);
	}

	public static Object fromXML(InputStream s) {
		return xstream.fromXML(s);
	}

	/**
	 * Fetch the XML file for the specified class of object from the server,
	 * deserialize it, and return it.
	 * 
	 * @param desiredClass the class of the object you want to retrieve
	 * @return a deserialized object of desiredClass
	 * @throws IOException if a problem occurs while fetching the object
	 */
	public abstract <T extends IGameObject> T getLatest(Class<T> desiredClass) throws IOException;

	public abstract void setWheelVelocity(int leftWheel, int rightWheel) throws IOException, NotYourTurnException;

	protected String getStringContents(String uri, String encoding) throws IOException {
		return new String(getContents(uri),encoding);
	}

	protected byte[] getContents(String uri) throws IOException {
		final GetMethod get = new GetMethod(uri);
		try {
			AbstractServer.httpClient.executeMethod(get);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			IOUtils.copy(get.getResponseBodyAsStream(), baos);
			return baos.toByteArray();
		} finally {
			get.releaseConnection();
		}
	}
}
