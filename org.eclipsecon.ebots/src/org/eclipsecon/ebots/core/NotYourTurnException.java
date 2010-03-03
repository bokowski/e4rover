package org.eclipsecon.ebots.core;

/**
 * Thrown when a user attempts to command the robot when it is not their turn.
 * If you want to play the {@link IGame}, you need to register and set your
 * {@link IPlayer#MY_PLAYER_KEY} and get in line to play by calling
 * {@link ContestPlatform#enterPlayerQueue(String)}.
 * 
 * @see ContestPlatform#setRobotWheelVelocity(int, int, String)
 */
public class NotYourTurnException extends Exception {

	private static final long serialVersionUID = 5114543999566297554L;

	public NotYourTurnException(String string) {
		super(string);
	}

	
}
