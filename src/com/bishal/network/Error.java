/**
 * 
 */
package com.bishal.network;

/**
 * @author bishal
 *
 */
public enum Error {
	  GET_QUEUE(0, ""),
	  PUT_QUEUE(1, ""),
	  DELETE_QUEUE(2, "");
	  
	  private final int code;
	  private final String description;

	  private Error(int code, String description) {
	    this.code = code;
	    this.description = description;
	  }

	  public String getDescription() {
	     return description;
	  }

	  public int getCode() {
	     return code;
	  }

	  @Override
	  public String toString() {
	    return code + ": " + description;
	  }
	}
