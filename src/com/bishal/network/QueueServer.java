/**
 * 
 */
package com.bishal.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author bishal
 *
 */
public class QueueServer implements Runnable {
	private Request request;
	private Response response;
	private Socket clientSocket;

	private Map<String, ArrayBlockingQueue<String>> queueMap;
	private Map<QueueOperationEnum, OperationsInterface> requestProcessors;

	public QueueServer() {
		initialize();
	}

	public QueueServer(Socket socket,
			Map<String, ArrayBlockingQueue<String>> map) {
		initialize();
		clientSocket = socket;
		queueMap = map;
	}

	private void initialize() {
		requestProcessors = new HashMap<>();
		queueMap = new ConcurrentHashMap<>();

		requestProcessors.put(QueueOperationEnum.GET, new GetOperationsImpl());
		requestProcessors.put(QueueOperationEnum.PUT, new PutOperationsImpl());
		requestProcessors.put(QueueOperationEnum.DELETE,
				new DeleteOperationsImpl());
	}

	public void run() {
		System.out.println(Utility.getLog("Client Connected Thread ",
				Long.toString(Thread.currentThread().getId()),
				"getting Executed ......"));
		try (PrintWriter out = new PrintWriter(clientSocket.getOutputStream(),
				true);
				BufferedReader in = new BufferedReader(new InputStreamReader(
						clientSocket.getInputStream(), Utility.CHARSET));) {
			String requestFromClient = in.readLine();
			System.out.println(Utility.getLog("requestFromClient :",
					requestFromClient));
			parseRequest(requestFromClient);
			processRequest();
			System.out.println(Utility.getLog("responseToCLient :",
					response.toString()));
			out.print(response);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Parses input string to a Request Object
	 * 
	 * @param input
	 *            String Value passed by Client
	 */
	private void parseRequest(String input) {
		System.out.println(Utility.getLog("Parsing Request Input :", input));
		if (!Utility.isNotNullOrEmpty(input)) {
			throw new IllegalArgumentException("Request has no Content !");
		} else if (!Utility.isValidRequest(input)) {
			throw new IllegalArgumentException(
					"Request is not in Proper Format !" + input + ":");
		}
		String[] values = input.split("\\s", Utility.MAX_REQUEST_LENGTH);
		if (values.length >= Utility.MAX_REQUEST_LENGTH) {
			request = new Request(QueueOperationEnum.valueOf(values[0]),
					values[1], values[2]);
		} else {
			request = new Request(QueueOperationEnum.valueOf(values[0]),
					values[1]);
		}
	}

	/**
	 * process Incoming Request
	 */
	private void processRequest() {
		response = requestProcessors.get(request.getOperation())
				.processRequest(request, queueMap);
	}

	/**
	 * start Network Queue Server
	 * 
	 * @throws IOException
	 */
	public void startServer() throws IOException {
		ExecutorService threadPool = Executors.newCachedThreadPool();
		System.out.println(Utility.getLog("Server listening in port :",
				Integer.toString(Utility.PORT)));
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(Utility.PORT);
			while (true) {
				Socket sock = serverSocket.accept();
				threadPool.execute(new QueueServer(sock, queueMap));
			}
		} finally {
			if (null != serverSocket)
				serverSocket.close();
		}
	}

	public static void main(String[] args) throws IOException {
		new QueueServer().startServer();
	}
}
