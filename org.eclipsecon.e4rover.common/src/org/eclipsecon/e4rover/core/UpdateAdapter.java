package org.eclipsecon.e4rover.core;

/**
 * Convenience class for {@link IUpdateListener}.
 */
public class UpdateAdapter implements IUpdateListener {

	public void arenaCamViewUpdated(IArenaCamImage pq) {}

	public void gameUpdated(IGame game) {}

	public void playerQueueUpdated(IPlayerQueue queue) {}

	public void playersUpdated(IPlayers players) {}

	public void robotUpdated(IRobot robot) {}

}
