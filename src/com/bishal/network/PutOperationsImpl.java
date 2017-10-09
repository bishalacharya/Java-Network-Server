package com.bishal.network;

import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;

class PutOperationsImpl implements OperationsInterface {

	@Override
	public Response processRequest(Request request,
			Map<String, ArrayBlockingQueue<String>> queueMap) {
		System.out.println(Utility.getLog("Process PUT Request"));
		if (!queueMap.containsKey(request.getQueueId())) {
			System.out.println(Utility
					.getLog("queue does not exist create it !"));
			ArrayBlockingQueue<String> blockingQueue = new ArrayBlockingQueue<String>(
					Utility.QUEUE_CAPACITY, Utility.FAIRNESS,
					request.getElementsList());
			queueMap.put(request.getQueueId(), blockingQueue);
		} else {
			for (String element : request.getElementsList()) {
				queueMap.get(request.getQueueId()).add(element);
			}
		}
		Response response = new Response("OK");
		return response;
	}
}
