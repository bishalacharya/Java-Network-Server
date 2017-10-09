/**
 * 
 */
package com.bishal.network;

/**
 * @author bishal
 *
 */
public class Response {
	private String status;
	private Error errorCode;
	private String queueId;

	public Response() {
	}

	public Response(String status) {
		this.status = status;
	}

	public Response(String status, Error errorCode) {
		this(status);
		this.errorCode = errorCode;
	}

	public Response(Error errorCode, String queueId) {
		this.errorCode = errorCode;
		this.queueId = queueId;
	}

	public Response(String status, Error errorCode, String queueId) {
		this(status, errorCode);
		this.queueId = queueId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Error getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Error errorCode) {
		this.errorCode = errorCode;
	}

	public String getQueueId() {
		return queueId;
	}

	public void setQueueId(String queueId) {
		this.queueId = queueId;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (null != status) {
			sb.append(status);
		}
		if (null != errorCode) {
			sb.append(errorCode);
		}
		if (null != queueId) {
			sb.append(queueId);
		}
		return sb.toString();
	}
}
