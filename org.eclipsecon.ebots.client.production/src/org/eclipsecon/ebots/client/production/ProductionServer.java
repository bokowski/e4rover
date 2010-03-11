package org.eclipsecon.ebots.client.production;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.io.IOUtils;
import org.eclipsecon.ebots.IServer;
import org.eclipsecon.ebots.core.IArenaCamImage;
import org.eclipsecon.ebots.core.IGame;
import org.eclipsecon.ebots.core.IGameObject;
import org.eclipsecon.ebots.core.IPlayer;
import org.eclipsecon.ebots.core.IPlayerQueue;
import org.eclipsecon.ebots.core.IPlayers;
import org.eclipsecon.ebots.core.IRobot;
import org.eclipsecon.ebots.core.IServerConstants;
import org.eclipsecon.ebots.core.NotYourTurnException;
import org.eclipsecon.ebots.core.XmlSerializer;
import org.eclipsecon.ebots.internal.core.ArenaCamImage;
import org.eclipsecon.ebots.internal.core.ServerObject;


public class ProductionServer implements IServer {

	// requests should time out after 3 seconds
	private static final int REQUEST_TIMEOUT = 3000;

	protected XmlSerializer xmlserializer = new XmlSerializer();

	protected HttpClient httpClient;

	/** Map for looking up the right URI for each important game object */
	private Map<Class<?>, String> classToURIMap = new HashMap<Class<?>, String>();

	public ProductionServer() {
		HttpConnectionManager cm = new MultiThreadedHttpConnectionManager();
		cm.getParams().setConnectionTimeout(REQUEST_TIMEOUT);
		httpClient = new HttpClient(cm);
		
		classToURIMap.put(IGame.class, IServerConstants.GAME_FILE_URI);
		classToURIMap.put(IRobot.class, IServerConstants.ROBOT_FILE_URI);
		classToURIMap.put(IPlayers.class, IServerConstants.PLAYERS_FILE_URI);
		classToURIMap.put(IPlayerQueue.class, IServerConstants.QUEUE_FILE_URI);
		classToURIMap.put(IArenaCamImage.class, IServerConstants.IMAGE_FILE_URI);
	}

	// Fetch the XML file for the specified class of object from the server,
	// deserialize it, and return it.
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
			result = xmlserializer.fromXML(getStringContents(uri, "UTF-8"));
		}
		// hmm, if it isn't a ServerObject, then that's a problem too!
		if(result instanceof ServerObject) {
			((ServerObject) result).setTimestamp(System.currentTimeMillis());
		}

		return desiredClass.cast(result);	// CCE indicates something is very wrong
	}

	public void setWheelVelocity(int leftWheel, int rightWheel, String playerKey) throws IOException, NotYourTurnException {

		final PostMethod post = new PostMethod(IServerConstants.COMMAND_RESTLET_URI + playerKey);
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

	public int enterPlayerQueue(String playerKey) throws IOException {
		PostMethod post = new PostMethod(IServerConstants.QUEUE_RESTLET);
		
		try {
			NameValuePair[] form = new NameValuePair[1];
			form[0] = new NameValuePair(IServerConstants.HASH, playerKey);
			post.setRequestBody(form);
			int resp = httpClient.executeMethod(post);
			if (resp == HttpStatus.SC_OK) {
				return Integer.parseInt(post.getResponseHeader(IServerConstants.QUEUE_POSITION).getValue());
			}
			else 
				throw new IOException("Server reported error " + resp + ".  Message: " + post.getResponseBodyAsString());
		} finally {
			post.releaseConnection();
		}
	}
	
	protected String getStringContents(String uri, String encoding) throws IOException {
		return new String(getContents(uri),encoding);
	}

	protected byte[] getContents(String uri) throws IOException {
		final GetMethod get = new GetMethod(uri);
		try {
			httpClient.executeMethod(get);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			IOUtils.copy(get.getResponseBodyAsStream(), baos);
			return baos.toByteArray();
		} finally {
			get.releaseConnection();
		}
	}


}