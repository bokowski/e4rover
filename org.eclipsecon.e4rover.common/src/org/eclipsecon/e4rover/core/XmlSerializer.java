package org.eclipsecon.e4rover.core;

import java.io.InputStream;

import org.eclipsecon.e4rover.internal.core.Game;
import org.eclipsecon.e4rover.internal.core.Goal;
import org.eclipsecon.e4rover.internal.core.Player;
import org.eclipsecon.e4rover.internal.core.PlayerQueue;
import org.eclipsecon.e4rover.internal.core.Players;
import org.eclipsecon.e4rover.internal.core.Robot;

import com.thoughtworks.xstream.XStream;

public class XmlSerializer {
	/** XStream instance, used to serialize and deserialize all contest objects */  
	protected final XStream xstream = new XStream();

	public XmlSerializer() {
		// configure XStream to produce prettier XML
		xstream.alias("game", IGame.class, Game.class);
		xstream.alias("robot", IRobot.class, Robot.class);	
		xstream.alias("players", IPlayers.class, Players.class);
		xstream.alias("player", IPlayer.class, Player.class);
		xstream.alias("queue", IPlayerQueue.class, PlayerQueue.class);
		xstream.alias("goal", IGoal.class, Goal.class);
	}

	
	public String toXML(Object o) {
		return xstream.toXML(o);
	}

	public Object fromXML(String s) {
		return xstream.fromXML(s);
	}

	public Object fromXML(InputStream s) {
		return xstream.fromXML(s);
	}


	/**
	 * Return the Xstream, intended for further configuration
	 * @return the instance's xstream
	 */
	public XStream getXstream() {
		return xstream;
	}
}
