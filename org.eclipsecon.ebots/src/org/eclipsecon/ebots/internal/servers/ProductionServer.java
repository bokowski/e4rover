package org.eclipsecon.ebots.internal.servers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.eclipsecon.ebots.core.IArenaCamImage;
import org.eclipsecon.ebots.core.IGame;
import org.eclipsecon.ebots.core.IGameObject;
import org.eclipsecon.ebots.core.IPlayer;
import org.eclipsecon.ebots.core.IPlayerQueue;
import org.eclipsecon.ebots.core.IPlayers;
import org.eclipsecon.ebots.core.IRobot;
import org.eclipsecon.ebots.core.IServerConstants;
import org.eclipsecon.ebots.core.NotYourTurnException;
import org.eclipsecon.ebots.internal.core.ArenaCamImage;
import org.eclipsecon.ebots.internal.core.ServerObject;


public class ProductionServer extends AbstractServer {

	/** Map for looking up the right URI for each important game object */
	private Map<Class<?>, String> classToURIMap = new HashMap<Class<?>, String>();

	public ProductionServer() {
		classToURIMap.put(IGame.class, IServerConstants.GAME_FILE_URI);
		classToURIMap.put(IRobot.class, IServerConstants.ROBOT_FILE_URI);
		classToURIMap.put(IPlayers.class, IServerConstants.PLAYERS_FILE_URI);
		classToURIMap.put(IPlayerQueue.class, IServerConstants.QUEUE_FILE_URI);
		classToURIMap.put(IArenaCamImage.class, IServerConstants.IMAGE_FILE_URI);
	}

	public <T extends IGameObject> T getLatest(Class<T> desiredClass) throws IOException {

		// Get the URI for this object
		String uri = classToURIMap.get(desiredClass);
		if (uri == null) {
			throw new IllegalArgumentException("Class " + desiredClass + " is not a valid server object");
		}

		Object result = null;
		if (desiredClass == IArenaCamImage.class) { // binary product
			result = new ArenaCamImage(getContents(uri));
		} else { // XML product
			result = xstream.fromXML(getStringContents(uri, "UTF-8"));
		}
		// hmm, if it isn't a ServerObject, then that's a problem too!
		if(result instanceof ServerObject) {
			((ServerObject) result).setTimestamp(System.currentTimeMillis());
		}

		return desiredClass.cast(result);	// CCE indicates something is very wrong
	}

	@Override
	public void setWheelVelocity(int leftWheel, int rightWheel) throws IOException, NotYourTurnException {

		final PostMethod post = new PostMethod(IServerConstants.COMMAND_RESTLET_URI);
		try {
			String command = leftWheel + "," + rightWheel;
			//TODO replace with constants
			post.setRequestEntity(new StringRequestEntity(command, "text/xml", "UTF-8"));
			int resp = httpClient.executeMethod(post);
			if (resp == HttpStatus.SC_CONFLICT) {
				throw new NotYourTurnException(post.getResponseBodyAsString());
			}
			if (resp != HttpStatus.SC_OK) {
				throw new IOException("Server reported error " + resp + ".  Message: " + post.getResponseBodyAsString());
			}
		} finally {
			post.releaseConnection();
		}

	}

	@Override
	public int enterPlayerQueue() throws IOException {
		PostMethod post = new PostMethod(IServerConstants.QUEUE_RESTLET);
		
		try {
			NameValuePair[] form = new NameValuePair[1];
			form[0] = new NameValuePair(IServerConstants.HASH, IPlayer.MY_PLAYER_KEY);
			post.setRequestBody(form);
			int resp = AbstractServer.httpClient.executeMethod(post);
			if (resp == HttpStatus.SC_OK) {
				return Integer.parseInt(post.getResponseHeader(IServerConstants.QUEUE_POSITION).getValue());
			}
			else 
				throw new IOException("Server reported error " + resp + ".  Message: " + post.getResponseBodyAsString());
		} finally {
			post.releaseConnection();
		}
	}
	
}