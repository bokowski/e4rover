package org.eclipsecon.e4rover.internal.core;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipsecon.e4rover.core.IGameObject;
import org.eclipsecon.e4rover.core.XmlSerializer;

/**
 * Base level server object.
 */
public class ServerObject implements IGameObject {
	protected transient long timestamp;
	
	public long getTimestamp() {
		return timestamp;
	}
	
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp; 
	}
	
	@Override
	public String toString() {
		SimpleDateFormat formatter = new SimpleDateFormat("kk:mm:ss.SSS");	// expensive but worth it
		return "<!-- timestamp: " + formatter.format(new Date(timestamp)) + " -->\n" +
			new XmlSerializer().toXML(this);
	}
}
