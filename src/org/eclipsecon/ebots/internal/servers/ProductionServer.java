package org.eclipsecon.ebots.internal.servers;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.URIUtil;
import org.eclipsecon.ebots.core.IGame;
import org.eclipsecon.ebots.core.IPlayers;
import org.eclipsecon.ebots.core.IRobot;
import org.eclipsecon.ebots.core.IS3Constants;
import org.eclipsecon.ebots.core.IGameObject;
import org.eclipsecon.ebots.core.ITelemetry;
import org.eclipsecon.ebots.internal.core.ServerObject;


public class ProductionServer extends AbstractServer {

	/* URIs for key game objects */
	/** The S3 server for the competition */
	private static URI EROVER_SERVER_URI;
	static {
		try {
			EROVER_SERVER_URI= new URI(IS3Constants.S3_BASE_URL);
		} catch (URISyntaxException e) {/*gulp*/} 
	}
	private static final URI GAME_FILE_URI = URIUtil.append(EROVER_SERVER_URI, IS3Constants.GAME_FILE_NAME);
	private static final URI ROBOT_FILE_URI = URIUtil.append(EROVER_SERVER_URI, IS3Constants.ROBOT_FILE_NAME);
	private static final URI PLAYERS_FILE_URI = URIUtil.append(EROVER_SERVER_URI, IS3Constants.PLAYERS_FILE_NAME);

	/** Map for looking up the right URI for each important game object */
	private Map<Class<?>, URI> classToURIMap = new HashMap<Class<?>, URI>();
	public ProductionServer() {
		classToURIMap.put(IGame.class, GAME_FILE_URI);
		classToURIMap.put(IRobot.class, ROBOT_FILE_URI);
		classToURIMap.put(ITelemetry.class, ROBOT_FILE_URI);
		classToURIMap.put(IPlayers.class, PLAYERS_FILE_URI);
		// classToURIMap.put(IPlayerQueue.class, "");
		// classToURIMap.put(IArenaCamImage.class, "");
	}

	public <T extends IGameObject> T getLatest(Class<T> desiredClass) throws IOException {
		URI uri = classToURIMap.get(desiredClass);
		if (uri == null) {
			throw new IllegalArgumentException("Class " + desiredClass + " is not a valid server object");
		}
		
		Object result = xstream.fromXML(getStringContents(uri, "UTF-8"));
		// hmm, if it isn't a ServerObject, then that's a problem too!
		if(result instanceof ServerObject) {
			((ServerObject) result).setTimestamp(System.currentTimeMillis());
		}
		return desiredClass.cast(result);	// CCE indicates something is very wrong
	}
}