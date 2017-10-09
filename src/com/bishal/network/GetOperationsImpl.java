package com.bishal.network;

import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;

class GetOperationsImpl implements OperationsInterface {

	@Override
	public Response processRequest(Request request,
			Map<String, ArrayBlockingQueue<String>> queueMap) {
		System.out.println(Utility.getLog("Process GET Request"));
		Response response = new Response();
		if (!queueMap.containsKey(request.getQueueId())) {
			System.out.println(Utility.getLog("queueMap does not contains Key",
					request.getQueueId()));
			response.setErrorCode(Error.GET_QUEUE);
			response.setQueueId(request.getQueueId()+"\n");
			return response;
		}
		System.out.println(Utility.getLog("queueMap.get queueId :",
				request.getQueueId()));
		ArrayBlockingQueue<String> queue = queueMap.get(request.getQueueId());
		String removedElement = null;
		try {
			removedElement = queue.take();
			System.out
					.println(Utility.getLog("removedElement", removedElement));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		response.setStatus(removedElement);
		return response;
	}

}
