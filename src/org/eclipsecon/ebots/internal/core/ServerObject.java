package org.eclipsecon.ebots.internal.core;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import com.thoughtworks.xstream.XStream;

public abstract class ServerObject<T> {

	protected static XStream xstream;
	static {
		xstream = new XStream();
		xstream.alias("game", Game.class);
		xstream.omitField(Game.class, "gameService");
		xstream.alias("players", Players.class);
		xstream.alias("player", Player.class);
		xstream.alias("robot", Robot.class);	
	}
	
	public ServerObject getNextFromServer() throws IOException {
		return (ServerObject) xstream.fromXML(uriToString(getURI()));
	}
	
	protected abstract URI getURI();
	
	@Override
	public String toString() {
		return xstream.toXML(this);
	}

	private static String uriToString(URI uri) throws IOException {
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
		return out.toString("UTF-8");
	}
}