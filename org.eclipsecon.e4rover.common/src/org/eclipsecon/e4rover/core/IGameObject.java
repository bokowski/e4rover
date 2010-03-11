package org.eclipsecon.e4rover.core;

import java.util.Date;

/**
 * Base interface implemented by game objects. If the object has been retrieved
 * from the server, then the object will have a timestamp.
 */
public interface IGameObject {

	/**
	 * @return the date/time when this object was retrieved (number of
	 *         milliseconds since the Java epoch).
	 * @see System#currentTimeMillis()
	 * @see Date#Date(long)
	 */
	public long getTimestamp();
}
