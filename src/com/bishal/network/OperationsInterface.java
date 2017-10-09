package com.bishal.network;

import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;

public interface OperationsInterface {
	public Response processRequest(Request request,
			Map<String, ArrayBlockingQueue<String>> queueMap);
}
