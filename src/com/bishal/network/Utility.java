/**
 * 
 */
package com.bishal.network;

import java.time.Instant;

/**
 * @author bishal
 *
 */
public class Utility {
	/**
	 * HostName of the Server
	 */
	public static final String HOST = "localhost";
	/**
	 * Server listens to PORT
	 */
	public static final int PORT = 43626;
	/**
	 * A call to accept will block for this amount of time
	 */
	public static final int TIMEOUT = 10000;
	/**
	 * Capacity of the Queue
	 */
	public static final int QUEUE_CAPACITY = 100;
	/**
	 * Capacity of Thread Pool
	 */
	public static final int POOL_SIZE = 100;
	/**
	 * fairness true grants threads access in FIFO order
	 */
	public static final boolean FAIRNESS = true;
	/**
	 * Default Character Set 8 bit transformation format
	 */
	public static final String CHARSET = "UTF-8";
	/**
	 * Max Length of Request
	 */
	public static final int MAX_REQUEST_LENGTH = 3;

	/**
	 * checks whether input string is null or empty
	 * 
	 * @param value
	 * @return
	 */
	public static final boolean isNotNullOrEmpty(String request) {
		return (request != null) && (!request.trim().isEmpty());
	}

	/**
	 * Checks for Validity of Request
	 * 
	 * @param request
	 * @return
	 */
	public static final boolean isValidRequest(String request) {
		boolean isValid = false;

		if (!isNotNullOrEmpty(request)) {
			return false;
		}

		String[] requestArray = request.split("\\s+");
		if (requestArray.length < 2) {
			return false;
		}

		for (QueueOperationEnum value : QueueOperationEnum.values()) {
			if (requestArray[0].equalsIgnoreCase(value.toString())) {
				isValid = true;
				break;
			}
		}

		return isValid;
	}

	/**
	 * Gets the Current Timestamp
	 * 
	 * @return Current Timestamp
	 */
	public static final String getTimestamp() {
		return Instant.now().toString() + " : ";
	}

	/**
	 * 
	 * @param string
	 *            input
	 * @return Log with TimeStamp
	 */
	public static final String getLog(String... input) {
		StringBuilder sb = new StringBuilder(getTimestamp());
		for (String value : input) {
			sb.append(" ").append(value);
		}
		return sb.toString();
	}
}
