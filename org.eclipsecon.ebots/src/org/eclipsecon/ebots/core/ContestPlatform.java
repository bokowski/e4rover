package org.eclipsecon.ebots.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipsecon.ebots.IServer;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;

/**
 * This class holds the main game objects that competitors will need to access
 * in order to play. It also periodically updates them from the server after the
 * {@link #startUpdateThread()} method is called.
 * 
 * Note that the game objects returned from the getters in this class are
 * snapshots. They will *not* be kept up to date after they are returned to the
 * caller.
 * 
 * Users that want to be notified when updated versions of the singletons are
 * retrieved should implement {@link IUpdateListener} and add themselves as a
 * listener on this class.
 */
public class ContestPlatform {

	private IServer server;		

	// Current contest singleton objects
	private IGame game;
	private IRobot robot;
	private IPlayers players;
	private IPlayerQueue queue;
	private IArenaCamImage arenaCameraImage;

	private Thread updateThread;
	private List<IUpdateListener> updateListeners = new ArrayList<IUpdateListener>();

	/**
	 * Number of exceptions that occurred in the event loop; 
	 * intended for debugging purposes.
	 */
	public int exceptionsCount = 0;

	private EventAdmin eventAdmin;

	public ContestPlatform() {
		System.out.println("Contest Platform: creation");
	}
	
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

	// this method is being called through OSGI DS, see contestplatform.xml 
	public void setServer(IServer server) {
		System.out.println("ContestPlatform: provided server: " + server);
		this.server = server;
		startUpdateThread();
	}
	
	public void unsetServer(IServer server) {
		System.out.println("ContestPlatform: server retracted: " + server);
		stop();
		if(this.server == server) { server = null; }
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
	public synchronized IArenaCamImage getArenaCameraImage() {
		return arenaCameraImage;
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

	/**
	 * @return true iff the automatic update thread is running (started by calling {@link #startUpdateThread()})
	 */
	public boolean isRunning() {
		return updateThread != null && updateThread.isAlive() && !updateThread.isInterrupted();
	}

	/**
	 * Starts a thread that periodically updates the contest singleton objects
	 * from the data stored on the server. 
	 * 
	 * Clients should *not* edit this method!
	 */
	public void startUpdateThread() {
		if (isRunning())
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
								fireRobotUpdated(robot);
							}
							// Every second
							synchronized(this) {
								game = server.getLatest(IGame.class);
							}
							synchronized (this) {
								arenaCameraImage = server.getLatest(IArenaCamImage.class);
							}
							fireGameUpdated(game);
							fireArenaCamViewUpdated(arenaCameraImage);
						}
						// Every 10 seconds
						synchronized (this) {
							players = server.getLatest(IPlayers.class);
						}
						synchronized(this) {
							queue = server.getLatest(IPlayerQueue.class);
						}
						firePlayersUpdated(players);
						firePlayerQueueUpdated(queue);
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

	public int enterPlayerQueue(String playerKey) throws IOException {
		return server.enterPlayerQueue(playerKey);
	}

	/**
	 * Sends a command to the robot that will set the velocity of its left and
	 * right wheels. If your IServerConstants.PLAYER_KEY is valid and it is
	 * currently your turn, the robot will immediately execute the command and
	 * continue driving at the requested velocities until another command is
	 * received or the game concludes.  If it is not your turn, then this method
	 * throws a NotYourTurnException.
	 * 
	 * Valid wheel velocities are between -100 (max backward speed) and 100 (max
	 * forward speed). To turn the robot in place, set left and right wheel
	 * velocities opposite each other. Here are some example drive velocities and
	 * the expected results:
	 * 
	 * 0,0 : stop the robot
	 * 100, 100 : drive the robot forward full speed
	 * 100, -100 : turn the robot in place to the right without moving forward 
	 *             or backward (spin clockwise very fast)
	 * 50, 100 : drive the robot forwards while turning to the left
	 * 
	 * @param leftWheel rotation rate for the left wheel, must be between -100 and 100
	 * @param rightWheel rotation rate for the right wheel, must be between -100 and 100
	 * @param playerKey TODO
	 * @throws IOException if a problem occurs while sending the command to the server
	 * @throws NotYourTurnException if someone else is controlling the robot
	 * @throws IllegalArgumentException if an invalid wheel velocity is provided
	 */
	public void setRobotWheelVelocity(int leftWheel, int rightWheel, String playerKey) throws IOException, NotYourTurnException {
		if (leftWheel < -100 || leftWheel > 100 || rightWheel < -100 || rightWheel > 100)
			throw new IllegalArgumentException("Valid wheel velocities are between -100 and 100 but received (" + 
					leftWheel + "," + rightWheel + ").");
		server.setWheelVelocity(leftWheel, rightWheel, playerKey);
	}

	/**
	 * Stops the automatic update thread that retrieves the contest singleton
	 * objects from the server and announces updates to listeners.
	 */
	public void stop() {
		if(updateThread != null) { updateThread.interrupt(); }
	}	

	/* internal methods for announcing that various contest singletons have been updated */

	private void fireGameUpdated(IGame game) {
		if (eventAdmin != null) {
			eventAdmin.postEvent(new Event(IGame.TOPIC, Collections.singletonMap(IEventBroker.DATA, game)));
		}
	}

	private void fireRobotUpdated(IRobot robot) {
		if (eventAdmin != null) {
			eventAdmin.postEvent(new Event(IRobot.TOPIC, Collections.singletonMap(IEventBroker.DATA, robot)));
		}
	}

	private void firePlayersUpdated(IPlayers players) {
		if (eventAdmin != null) {
			eventAdmin.postEvent(new Event(IPlayers.TOPIC, Collections.singletonMap(IEventBroker.DATA, players)));
		}
	}

	private void firePlayerQueueUpdated(IPlayerQueue pq) {
		if (eventAdmin != null) {
			eventAdmin.postEvent(new Event(IPlayerQueue.TOPIC, Collections.singletonMap(IEventBroker.DATA, pq)));
		}
	}

	private void fireArenaCamViewUpdated(IArenaCamImage img) {
		if (eventAdmin != null) {
			eventAdmin.postEvent(new Event(IArenaCamImage.TOPIC, Collections.singletonMap(IEventBroker.DATA, img)));
		}
	}

	// this method is being called through OSGI DS, see contestplatform.xml 
	public void setEventAdmin(EventAdmin eventAdmin) {
		this.eventAdmin = eventAdmin;
	}

//	public static void main(String[] args) {
//		ContestPlatform cp = new ContestPlatform();
//		cp.setServer(new ProductionServer());
//		cp.startUpdateThread();
//		
//		while (true) {
//			try {
//				Thread.sleep(2000);
//				Random rand = new Random();
//				while (true) {
//					cp.setRobotWheelVelocity(100 - rand.nextInt(200), 100 - rand.nextInt(200));
//				}
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (NotYourTurnException e) {
//				e.printStackTrace();
//				try {
//					int pos = cp.enterPlayerQueue();
//					System.err.println("Position: " + pos);
//				} catch (IOException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//	}

}
