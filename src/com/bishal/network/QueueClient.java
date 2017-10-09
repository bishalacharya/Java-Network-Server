/**
 * Test Class to test QueueServer. This Class simulates 100 threads sending PUT
 * requests  in parallel followed by 100  more threads sending  get requests in  
 * parallel followed  by 100  more  threads sending  PUT requests in  parallel 
 * followed by 100 more threads sending DELETE requests in parallel.Data sent to
 * the server are random numbers from 1 to 100. At any instance,the client may
 * send get, put, delete requests to server in parallel.
 */
package com.bishal.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.Random;

/**
 * @author bishal
 *
 */
public class QueueClient implements Runnable {
	private volatile String requestType;
	private static final int ITERATIONS = 1000;

	public QueueClient() {
	}

	public QueueClient(String type) {
		this.requestType = type;
	}

	@Override
	public void run() {
		System.out.println(Utility.getLog("Thread",
				Long.toString(Thread.currentThread().getId()),
				"getting Executed .."));
		try (Socket clientSocket = new Socket(Utility.HOST, Utility.PORT);
				PrintWriter out = new PrintWriter(
						clientSocket.getOutputStream(), true);
				BufferedReader in = new BufferedReader(new InputStreamReader(
						clientSocket.getInputStream(), Utility.CHARSET));) {
			String request = null;
			if (this.requestType.equals("PUT")) {
				request = putRequest();
			} else if (this.requestType.equals("GET")) {
				request = getRequest();
			} else if (this.requestType.equals("DELETE")) {
				request = deleteRequest();
			}
			System.out.println(Utility.getLog("Sending request to Server :",
					request));
			out.print(request);
			out.flush();

			String responseFromServer = in.readLine();
			System.out.println(Utility.getLog(responseFromServer));
			in.close();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String putRequest() {
		Random r = new Random();
		int queueId = r.nextInt(Utility.QUEUE_CAPACITY);
		StringBuilder request = new StringBuilder("PUT");
		request.append(" ").append(queueId).append(" ").append("Element1 \n");
		return request.toString();
	}

	private String getRequest() {
		Random r = new Random();
		int queueId = r.nextInt(Utility.QUEUE_CAPACITY);
		StringBuilder request = new StringBuilder("GET");
		request.append(" ").append(queueId).append("\n");
		return request.toString();
	}

	private String deleteRequest() {
		Random r = new Random();
		int queueId = r.nextInt(Utility.QUEUE_CAPACITY);
		StringBuilder request = new StringBuilder("DELETE");
		request.append(" ").append(queueId).append("\n");
		return request.toString();
	}

	private void testMultipleGetPutDelete() {
		ExecutorService threadPool = Executors
				.newFixedThreadPool(Utility.POOL_SIZE);

		for (int i = 0; i < ITERATIONS; i++) {
			threadPool.execute(new QueueClient("PUT"));
		}

		for (int i = 0; i < ITERATIONS; i++) {
			threadPool.execute(new QueueClient("GET"));
		}

		for (int i = 0; i < ITERATIONS; i++) {
			threadPool.execute(new QueueClient("PUT"));
		}

		for (int i = 0; i < ITERATIONS; i++) {
			threadPool.execute(new QueueClient("DELETE"));
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new QueueClient().testMultipleGetPutDelete();
	}
}
