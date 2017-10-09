/**
 * 
 */
package com.bishal.network;

import java.util.Arrays;
import java.util.List;

/**
 * @author bishal
 *
 */
public class Request {
	private QueueOperationEnum operation;
	private String queueId;
	private String elements;

	public Request(QueueOperationEnum operation, String id, String element) {
		this.operation = operation;
		this.queueId = id;
		this.elements = element;
	}

	public Request(QueueOperationEnum operation, String id) {
		this.operation = operation;
		this.queueId = id;
	}

	public QueueOperationEnum getOperation() {
		return operation;
	}

	public void setOperation(QueueOperationEnum operation) {
		this.operation = operation;
	}

	public String getQueueId() {
		return queueId;
	}

	public void setQueueId(String queueId) {
		this.queueId = queueId;
	}

	public String getElements() {
		return elements;
	}

	public void setElements(String elements) {
		this.elements = elements;
	}

	public List<String> getElementsList() {
		List<String> elementsList = null;
		if (null != elements) {
			String[] arrays = elements.split("\\s+");
			elementsList = Arrays.asList(arrays);
		}
		return elementsList;
	}

	public int getElementsSize() {
		int size = 0;
		if (null != elements) {
			size = elements.split("\\s+").length;
		}
		return size;
	}
}
