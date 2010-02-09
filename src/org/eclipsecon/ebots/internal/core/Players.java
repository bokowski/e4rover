package org.eclipsecon.ebots.internal.core;

import java.util.HashMap;
import java.util.List;

import com.thoughtworks.xstream.XStream;

public class Players extends HashMap<String, Player> {
	private static XStream xstream;
	
	public static final Players instance = new Players();
	
	static {
		xstream = new XStream();
		xstream.alias("players", List.class);
		xstream.alias("player", Player.class);	
	}

	/**
	 * Only the server gets to use this constructor. Clients use the fromXML()
	 * method, below.
	 */
	Players() {/*empty*/}
	
	static Players fromXML(String xml) {
		return (Players) xstream.fromXML(xml);
	}
	
	@Override
	public String toString() {
		return xstream.toXML(this);
	}
	
	public String toXML() {
		return this.toString();
	}
	
}
