package org.eclipsecon.ebots.core;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.eclipsecon.ebots.internal.core.Game;
import org.eclipsecon.ebots.internal.core.Players;
import org.eclipsecon.ebots.internal.core.Robot;

public class ContestPlatform {

	public static URI EROVER_SERVER_URI;
	static {
		try {
			EROVER_SERVER_URI= new URI("http://ebots.s3.amazonaws.com/");
		} catch (URISyntaxException e) {/*gulp*/} 
	}
	private static Game game = new Game();
	private static Robot robot = new Robot();
	private static Players players = new Players();
	
	public static synchronized IGame getGame() {
		return game;
	}
	
	public static synchronized IRobot getRobot() {
		return robot;
	}

	public static synchronized IPlayers getPlayers() {
		return players;
	}
	
	public static synchronized void updateFromServer() {
		try {
			game = (Game) game.getNextFromServer();
			robot = (Robot) robot.getNextFromServer();
			players = (Players) players.getNextFromServer();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	
}
