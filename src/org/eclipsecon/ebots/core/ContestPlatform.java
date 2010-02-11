package org.eclipsecon.ebots.core;

import java.util.ArrayList;
import java.util.List;

import org.eclipsecon.ebots.internal.core.ServerObject;


/**
 * This class holds the main singleton objects that competitors will need to
 * access in order to play the game. It also periodically updates them from the
 * server after the {@link #startUpdateThread()} method is called.
 * 
 * Note that the singleton objects returned from the getters in this class are
 * snapshots. They will *not* be subsequently updated.
 * 
 * Users that want to be notified when updated versions of the singletons are
 * retrieved should implement IUpdateListener and add themselves as a listener
 * on this class.
 */
public class ContestPlatform {

	private static IGame game;
	private static IRobot robot;
	private static IPlayers players;
	private static Thread updateThread;
	private static List<IUpdateListener> updateListeners = new ArrayList<IUpdateListener>();

	/**
	 * @return the latest copy of the Game object that has been retrieved from
	 *         the server, or null if none has been retrieved yet
	 */
	public static IGame getGame() {
		synchronized (IGame.class) {
			return game;			
		}
	}

	/**
	 * @return the latest copy of the Robot object that has been retrieved from
	 *         the server, or null if none has been retrieved yet
	 */
	public static IRobot getRobot() {
		synchronized(IRobot.class) {
			return robot;
		}
	}

	/**
	 * @return the latest copy of the Players object that has been retrieved
	 *         from the server, or null if none has been retrieved yet
	 */
	public static IPlayers getPlayers() {
		synchronized (IPlayers.class) {
			return players;
		}
	}

	/**
	 * Requests that the provided listener be notified whenever any of the
	 * contest singleton classes is updated
	 * 
	 * @param updateListener the listener to register
	 */
	public static void addUpdateListener(IUpdateListener updateListener) {
		updateListeners.add(updateListener);
	}

	/**
	 * Requests that notification of updates to the contest singleton classes no
	 * longer be delivered to the provided listener
	 * 
	 * @param updateListener the listener to unregister
	 */
	public static void removeUpdateListener(IUpdateListener updateListener) {
		updateListeners.remove(updateListener);
	}

	/**
	 * Starts a thread that periodically updates the contest singleton objects
	 * from the data stored on the server. Please do not edit this method!
	 */
	public static void startUpdateThread() {
		if (updateThread != null)
			return;
		updateThread = new Thread("Client Update Thread") {
			@Override
			public void run() {
				while (true) {
					try {
						for (int j = 0; j< 10; j++) {

							for (int i = 0; i < 4; i++) {
								Thread.sleep(250);
								// Every quarter of a second
								synchronized(IRobot.class) {
									robot = (IRobot) ServerObject.getLatest(IRobot.class);
								}
								fireRobotUpdated(robot);
							}
							// Every second
							synchronized(IGame.class) {
								game = (IGame) ServerObject.getLatest(IGame.class);
							}
							fireGameUpdated(game);

						}
						// Every 10 seconds
						synchronized (IPlayers.class) {
							players = (IPlayers) ServerObject.getLatest(IPlayers.class);
						}
						firePlayersUpdated(players);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		};
		updateThread.start();
	}
	
	private static void fireGameUpdated(IGame game) {
		IUpdateListener[] listeners = updateListeners.toArray(new IUpdateListener[]{});
		for (IUpdateListener listener : listeners) {
			listener.gameUpdated(game);
		}
	}
	
	private static void fireRobotUpdated(IRobot robot) {
		IUpdateListener[] listeners = updateListeners.toArray(new IUpdateListener[]{});
		for (IUpdateListener listener : listeners) {
			listener.robotUpdated(robot);
		}
	}
	
	private static void firePlayersUpdated(IPlayers players) {
		IUpdateListener[] listeners = updateListeners.toArray(new IUpdateListener[]{});
		for (IUpdateListener listener : listeners) {
			listener.playersUpdated(players);
		}
	}

}
