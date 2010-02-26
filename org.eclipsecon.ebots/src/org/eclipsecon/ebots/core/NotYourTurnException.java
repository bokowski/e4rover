package org.eclipsecon.ebots.core;

/**
 * Thrown when a user attempts to command the robot when it is not their turn.
 */
public class NotYourTurnException extends Exception {

	private static final long serialVersionUID = 5114543999566297554L;

	public NotYourTurnException(String string) {
		super(string);
	}

	
}
