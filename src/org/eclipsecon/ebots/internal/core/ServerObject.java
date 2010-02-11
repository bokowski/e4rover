package org.eclipsecon.ebots.internal.core;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.URIUtil;
import org.eclipsecon.ebots.core.IGame;
import org.eclipsecon.ebots.core.IPlayers;
import org.eclipsecon.ebots.core.IRobot;

import com.thoughtworks.xstream.XStream;

public abstract class ServerObject<T> {

	/** Static XStream instance, used to serialize and deserialize all contest objects */  
	private static XStream xstream;
	static {
		// configure XStream to produce prettier XML
		xstream = new XStream();
		xstream.alias("game", Game.class);
		xstream.alias("robot", Robot.class);	
		xstream.alias("players", Players.class);
		xstream.alias("player", Player.class);
	}
	
	/* URIs for key game objects */
	/** The S3 server for the competition */
	private static URI EROVER_SERVER_URI;
	static {
		try {
			EROVER_SERVER_URI= new URI("http://ebots.s3.amazonaws.com/");
		} catch (URISyntaxException e) {/*gulp*/} 
	}
	static final String GAME_FILE_NAME = "game.xml";
	private static final URI GAME_FILE_URI = URIUtil.append(EROVER_SERVER_URI, GAME_FILE_NAME);
	static final String ROBOT_FILE_NAME = "robot.xml";
	private static final URI ROBOT_FILE_URI = URIUtil.append(EROVER_SERVER_URI, ROBOT_FILE_NAME);
	static final String PLAYERS_FILE_NAME = "players.xml";
	private static final URI PLAYERS_FILE_URI = URIUtil.append(EROVER_SERVER_URI, PLAYERS_FILE_NAME);
	
	/** Map for looking up the right URI for each important game object */
	private static Map<Class, URI> classToURIMap = new HashMap<Class, URI>();
	static {
		classToURIMap.put(IGame.class, GAME_FILE_URI);
		classToURIMap.put(IRobot.class, ROBOT_FILE_URI);
		classToURIMap.put(IPlayers.class, PLAYERS_FILE_URI);
	}

	/**
	 * Fetch the XML file for the specified class of object from the server,
	 * deserialize it, and return it.
	 * 
	 * @param desiredClass the class of the object you want to retrieve
	 * @return a deserialized object of desiredClass
	 * @throws IOException if a problem occurs while fetching the object
	 */
	public static ServerObject getLatest(Class desiredClass) throws IOException {
		URI uri = classToURIMap.get(desiredClass);
		if (uri == null)
			throw new IllegalArgumentException("Class " + desiredClass + " is not a valid server object");
		
		InputStream in = uri.toURL().openStream();
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
		
		return (ServerObject) xstream.fromXML(out.toString("UTF-8"));
	}
	
	@Override
	public String toString() {
		return xstream.toXML(this);
	}
}