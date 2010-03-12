/*******************************************************************************
 * Copyright (c) 2010 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 ******************************************************************************/
package org.eclipsecon.e4rover.core;

/**
 * Constants for accessing key resources on the server.  Users should not need to edit this file.
 */
public interface IServerConstants {

	
	/* Downlink */
	public static final String E4ROVER_BUCKET_NAME = "ebots";
	public static final String E4ROVER_DOWNLINK_SERVER_URI = "http://" + E4ROVER_BUCKET_NAME +  ".s3.amazonaws.com/";
	
	public static final String GAME_FILE_NAME = "game.xml";
	public static final String ROBOT_FILE_NAME = "robot.xml";
	public static final String PLAYERS_FILE_NAME = "players.xml";
	public static final String QUEUE_FILE_NAME = "queue.xml";
	public static final String IMAGE_FILE_NAME = "img.jpg";

	public static final String GAME_FILE_URI = E4ROVER_DOWNLINK_SERVER_URI + GAME_FILE_NAME;
	public static final String ROBOT_FILE_URI = E4ROVER_DOWNLINK_SERVER_URI + ROBOT_FILE_NAME;
	public static final String PLAYERS_FILE_URI = E4ROVER_DOWNLINK_SERVER_URI + PLAYERS_FILE_NAME;
	public static final String QUEUE_FILE_URI = E4ROVER_DOWNLINK_SERVER_URI + QUEUE_FILE_NAME;
	public static final String IMAGE_FILE_URI = E4ROVER_DOWNLINK_SERVER_URI + IMAGE_FILE_NAME;
	
	/* Uplink */
	public static final String EROVER_UPLINK_SERVER_URI ="http://e4rover.eclipsecon.org/";
//	public static final String EROVER_UPLINK_SERVER_URI ="http://137.78.29.221:9111/";
	
	public static final String COMMAND_RESTLET_URI = EROVER_UPLINK_SERVER_URI + "cmd/";
	public static final String QUEUE_RESTLET = EROVER_UPLINK_SERVER_URI + "queue";

	public static final String HASH = "hash";
	public static final String QUEUE_POSITION = "position";
	
	
}
