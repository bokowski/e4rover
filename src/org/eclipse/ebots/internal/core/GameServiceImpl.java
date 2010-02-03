/*******************************************************************************
 * Copyright (c) 2010 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.ebots.internal.core;

import java.io.*;
import java.net.URI;
import org.eclipse.core.runtime.URIUtil;
import org.eclipsecon.ebots.core.IGame;
import org.eclipsecon.ebots.core.IGameService;

/**
 * 
 */
public class GameServiceImpl implements IGameService {

	public IGame createGame(URI server) throws IOException {
		URI gameURI = URIUtil.append(server, "game");
		IGame game = Game.fromXML(uriToString(gameURI));
		return null;
	}

	private String uriToString(URI uri) throws IOException {
		InputStream in = uri.toURL().openStream();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		transferStreams(in, out);
		return out.toString("UTF-8");
	}
	protected static void transferStreams(InputStream source, OutputStream destination) throws IOException {
		try {
			byte[] buffer = new byte[8192];
			while (true) {
				int bytesRead = -1;
					bytesRead = source.read(buffer);
				if (bytesRead == -1)
					break;
					destination.write(buffer, 0, bytesRead);
			}
		} finally {
			try {
				source.close();
			} catch (IOException e) {
				// ignore
			} finally {
				//close destination in finally in case source.close fails
				try {
					destination.close();
				} catch (IOException e) {
					// ignore
				}
			}
		}
	}

}
