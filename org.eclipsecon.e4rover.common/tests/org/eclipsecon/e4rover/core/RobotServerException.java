package org.eclipsecon.e4rover.core;

import java.io.IOException;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.URI;

@SuppressWarnings("serial")
public class RobotServerException extends IOException {
	protected int responseCode;
	protected URI uri;
	protected String message;
	
	public RobotServerException(int responseCode, URI uri, String message) {
		this.responseCode = responseCode;
		this.uri = uri;
		this.message = message;
	}

	public int getResponseCode() {
		return responseCode;
	}

	public URI getUri() {
		return uri;
	}
	
	public String getMessage() {
		return message;
	}
	
	public String toString() {
		return HttpStatus.getStatusText(responseCode) + " - " + uri
			+ " - " + message;
	}
}
