package com.bishal.network;

import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;

class DeleteOperationsImpl implements OperationsInterface {
	/**
	 * Deletes a blocking Queue
	 * 
	 * @return ResponseEnum a response status
	 */
	@Override
	public Response processRequest(Request request,
			Map<String, ArrayBlockingQueue<String>> queueMap) {
		System.out.println(Utility.getLog("Process DELETE Request"));
		ArrayBlockingQueue<String> returnVal = queueMap.remove(request
				.getQueueId());
		Response response = null;
		if (returnVal == null) {
			response = new Response(Error.DELETE_QUEUE, request.getQueueId());
		} else {
			response = new Response();
			response.setStatus("OK");
		}
		return response;
	}
}
