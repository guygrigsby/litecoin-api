package com.guygrigsby.litecoinapi;

import com.thetransactioncompany.jsonrpc2.JSONRPC2Error;

public class LitecoinAPIException extends Exception {

	public LitecoinAPIException(JSONRPC2Error error) {
		super(error);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -4301959100909017050L;

}
