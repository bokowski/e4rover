package org.eclipsecon.ebots.internal.servers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URLConnection;

import org.eclipsecon.ebots.core.IDrive;
import org.eclipsecon.ebots.core.IGame;
import org.eclipsecon.ebots.core.IGoal;
import org.eclipsecon.ebots.core.IPlayer;
import org.eclipsecon.ebots.core.IPlayerQueue;
import org.eclipsecon.ebots.core.IPlayers;
import org.eclipsecon.ebots.core.IRobot;
import org.eclipsecon.ebots.core.IGameObject;
import org.eclipsecon.ebots.core.ITelemetry;
import org.eclipsecon.ebots.internal.core.Drive;
import org.eclipsecon.ebots.internal.core.Game;
import org.eclipsecon.ebots.internal.core.Goal;
import org.eclipsecon.ebots.internal.core.Player;
import org.eclipsecon.ebots.internal.core.PlayerQueue;
import org.eclipsecon.ebots.internal.core.Players;
import org.eclipsecon.ebots.internal.core.Robot;
import org.eclipsecon.ebots.internal.core.Telemetry;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.Base64Encoder;

public abstract class AbstractServer {
	private static final String HEADER_AUTHORIZATION = "Authorization";
	private static final String AUTH_BASIC = "Basic ";
	/** Static XStream instance, used to serialize and deserialize all contest objects */  
	protected static XStream xstream;
	
	static {
		// configure XStream to produce prettier XML
		xstream = new XStream();
		xstream.alias("game", IGame.class, Game.class);
		xstream.alias("robot", IRobot.class, Robot.class);	
		xstream.alias("players", IPlayers.class, Players.class);
		xstream.alias("player", IPlayer.class, Player.class);
		xstream.alias("queue", IPlayerQueue.class, PlayerQueue.class);
		xstream.alias("drive", IDrive.class, Drive.class);
		xstream.alias("goal", IGoal.class, Goal.class);
		xstream.alias("telemetry", ITelemetry.class, Telemetry.class);
		xstream.registerConverter(new TelemetryConverter());
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
	
	protected String username;
	protected String password;
	private Base64Encoder encoder = new Base64Encoder();
	
	public void setAuthentication(String username, String password) {
		this.username = username;
		this.password = password;
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

	protected String getStringContents(URI uri, String encoding)
			throws IOException {
				return getContents(uri).toString(encoding);
			}

	protected byte[] getByteContents(URI uri) throws IOException {
		return getContents(uri).toByteArray();
	}

	protected ByteArrayOutputStream getContents(URI uri) throws IOException {
		URLConnection uc = uri.toURL().openConnection();
		if(username != null && password != null) {
			String encoded = encoder.encode((username + ":" + password).getBytes());
			uc.setRequestProperty(HEADER_AUTHORIZATION, AUTH_BASIC + encoded);
		}
		uc.connect();
		// FIXME: cast to HttpURLConnection and check response code?
		
		InputStream in = uc.getInputStream();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		
		try {
			byte[] buffer = new byte[8192];
			while (true) {
				int bytesRead = -1;
					bytesRead = in.read(buffer);
				if (bytesRead == -1)
					break;
					out.write(buffer, 0, bytesRead);
			}
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				// ignore
			} finally {
				//close destination in finally in case source.close fails
				try {
					out.close();
				} catch (IOException e) {
					// ignore
				}
			}
		}
		return out;
	}

}
