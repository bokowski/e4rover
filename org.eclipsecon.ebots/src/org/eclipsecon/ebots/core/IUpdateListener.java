package org.eclipsecon.ebots.core;

import java.util.EventListener;

/**
 * Listener interface that allows users to receive updates when ContestPlatform
 * retrieves an updated version of one of the contest singleton classes.
 * 
 */
public interface IUpdateListener extends EventListener {

	/**
	 * Called when the ContestPlatform has retrieved an updated copy of the Game object.  
	 * @param game the newly updated copy of the game object
	 */
	public void gameUpdated(IGame game);
	
	/**
	 * Called when the ContestPlatform has retrieved an updated copy of the Robot object.  
	 * @param robot the newly updated copy of the robot object
	 */
	public void robotUpdated(IRobot robot);
	
	/**
	 * Called when the ContestPlatform has retrieved an updated copy of the Players object.  
	 * @param players the newly updated copy of the players object
	 */
	public void playersUpdated(IPlayers players);

	/**
	 * Called when the ContestPlatform has retrieved an updated copy of the PlayerQueue object.  
	 * @param players the newly updated copy of the players object
	 */
	public void playerQueueUpdated(IPlayerQueue queue);

	/**
	 * Called when the ContestPlatform has retrieved an updated view from the ArenaCam.
	 * @param img the updated view
	 */
	public void arenaCamViewUpdated(IArenaCamImage img);

	/**
	 * Called when the ContestPlatform has retrieved an updated telemetry data.
	 * @param tm the updated telemetry data
	 */
	public void telemetryUpdated(ITelemetry tm);
}
