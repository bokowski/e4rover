package org.eclipsecon.ebots.core;

import java.util.ArrayList;
import java.util.List;

import org.eclipsecon.ebots.internal.servers.AbstractServer;
import org.eclipsecon.ebots.internal.servers.TestServer;


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
	private static ContestPlatform singleton;
	
	static {
		singleton = new ContestPlatform();
		singleton.startUpdateThread();
	}
	
	public static ContestPlatform getDefault() {
		return singleton;
	}

	private AbstractServer server = new TestServer();		// HACK for testing
	private IGame game;
	private IRobot robot;
	private IPlayers players;
	private IPlayerQueue queue;
	private IArenaCamImage cameraImage;
	private ITelemetry telemetry;
	
	private Thread updateThread;
	private List<IUpdateListener> updateListeners = new ArrayList<IUpdateListener>();

	/**
	 * Number of exceptions that occurred in the event loop; 
	 * intended for debugging purposes.
	 */
	public int exceptionsCount = 0;

	/**
	 * @return the latest copy of the Game object that has been retrieved from
	 *         the server, or null if none has been retrieved yet
	 */
	public synchronized IGame getGame() {
		return game;			
	}

	/**
	 * @return the latest copy of the Robot object that has been retrieved from
	 *         the server, or null if none has been retrieved yet
	 */
	public synchronized IRobot getRobot() {
		return robot;
	}

	/**
	 * @return the latest copy of the Players object that has been retrieved
	 *         from the server, or null if none has been retrieved yet
	 */
	public synchronized IPlayers getPlayers() {
		return players;
	}

	/**
	 * @return the latest copy of the PlayerQueue object that has been retrieved from
	 *         the server, or null if none has been retrieved yet
	 */
	public synchronized IPlayerQueue getPlayerQueue() {
		return queue;
	}

	/**
	 * Returns an image captured from the game's arena camera.
	 */
	public synchronized IArenaCamImage getCameraImage() {
		return cameraImage;
	}


	/**
	 * Requests that the provided listener be notified whenever any of the
	 * contest singleton classes is updated
	 * 
	 * @param updateListener the listener to register
	 */
	public void addUpdateListener(IUpdateListener updateListener) {
		updateListeners.add(updateListener);
	}

	/**
	 * Requests that notification of updates to the contest singleton classes no
	 * longer be delivered to the provided listener
	 * 
	 * @param updateListener the listener to unregister
	 */
	public void removeUpdateListener(IUpdateListener updateListener) {
		updateListeners.remove(updateListener);
	}

	public boolean isRunning() {
		return updateThread != null && updateThread.isAlive() && !updateThread.isInterrupted();
	}
	
	/**
	 * Starts a thread that periodically updates the contest singleton objects
	 * from the data stored on the server. Please do not edit this method!
	 */
	public void startUpdateThread() {
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
								synchronized(this) {
									robot = server.getLatest(IRobot.class);
								}
								synchronized(this) {
									telemetry = server.getLatest(ITelemetry.class);
								}
								fireRobotUpdated(robot);
								fireTelemetryUpdated(telemetry);
							}
							// Every second
							synchronized(this) {
								game = server.getLatest(IGame.class);
							}
							synchronized(this) {
								queue = server.getLatest(IPlayerQueue.class);
							}
							fireGameUpdated(game);
							firePlayerQueueUpdated(queue);
						}
						// Every 10 seconds
						synchronized (this) {
							players = server.getLatest(IPlayers.class);
						}
						synchronized (this) {
							cameraImage = server.getLatest(IArenaCamImage.class);
						}
						firePlayersUpdated(players);
						fireArenaCamViewUpdated(cameraImage);
					} catch(InterruptedException e) {
						break;
					} catch (Exception e) {
						exceptionsCount++;
						e.printStackTrace();
					}
				}
				updateThread = null;
			}
		};
		updateThread.start();
	}
	
	private void fireGameUpdated(IGame game) {
		IUpdateListener[] listeners = updateListeners.toArray(new IUpdateListener[]{});
		for (IUpdateListener listener : listeners) {
			listener.gameUpdated(game);
		}
	}
	
	private void fireRobotUpdated(IRobot robot) {
		IUpdateListener[] listeners = updateListeners.toArray(new IUpdateListener[]{});
		for (IUpdateListener listener : listeners) {
			listener.robotUpdated(robot);
		}
	}
	
	private void firePlayersUpdated(IPlayers players) {
		IUpdateListener[] listeners = updateListeners.toArray(new IUpdateListener[]{});
		for (IUpdateListener listener : listeners) {
			listener.playersUpdated(players);
		}
	}
	
	private void firePlayerQueueUpdated(IPlayerQueue pq) {
		IUpdateListener[] listeners = updateListeners.toArray(new IUpdateListener[]{});
		for (IUpdateListener listener : listeners) {
			listener.playerQueueUpdated(pq);
		}
	}
	
	private void fireArenaCamViewUpdated(IArenaCamImage img) {
		IUpdateListener[] listeners = updateListeners.toArray(new IUpdateListener[]{});
		for (IUpdateListener listener : listeners) {
			listener.arenaCamViewUpdated(img);
		}
	}

	private void fireTelemetryUpdated(ITelemetry tm) {
		IUpdateListener[] listeners = updateListeners.toArray(new IUpdateListener[]{});
		for (IUpdateListener listener : listeners) {
			listener.telemetryUpdated(tm);
		}
	}

	/**
	 * Provide team's authentication details, used for manipulating the robot.
	 * @param username the team user name
	 * @param password the team's password token
	 */
	public void setAuthentication(String username, String password) {
		server.setAuthentication(username, password);
	}

	public void stop() {
		if(updateThread != null) { updateThread.interrupt(); }
	}	
}
