/*
 * Copyright 2014 Guy J Grigsby

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
package com.guygrigsby.litecoinapi;

import java.net.Authenticator;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.util.List;

import com.thetransactioncompany.jsonrpc2.JSONRPC2Error;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Request;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Response;
import com.thetransactioncompany.jsonrpc2.client.JSONRPC2Session;
import com.thetransactioncompany.jsonrpc2.client.JSONRPC2SessionException;
import com.thetransactioncompany.jsonrpc2.client.JSONRPC2SessionOptions;

public abstract class JSONRPCMethodCaller {
	
	private String rpcUser;
	private String rpcPassword;
	private int rpcPort;
	private String rpcAddress;
	
	protected JSONRPCMethodCaller(String user, String pass, int port, String serverAddress) {
		rpcUser = user;
		rpcPassword = pass;
		rpcPort = port;
		rpcAddress = serverAddress;
	}
	protected final String callJSONRPCMethodForStringResponse(String method, List<Object> params) throws LitecoinAPIException {
		return callJSONRPCMethod(method, params).toString();
	}
	protected final double callJSONRPCMethodForDoubleResponse(String method, List<Object> params) throws LitecoinAPIException {
		return Double.parseDouble(callJSONRPCMethod(method, params).toString());
	}
	protected final Object callJSONRPCMethod(String method, List<Object> params) throws LitecoinAPIException {
		JSONRPC2Session session = getSession();
		JSONRPC2Request request;
		if (params == null) {
			request = new JSONRPC2Request(method, getNextRequestId());
		} else {
			request = new JSONRPC2Request(method, params, getNextRequestId());
		}
		JSONRPC2Response response = sendRequestToServer(session, request);
		
		if (response.indicatesSuccess())
			return response.getResult();
		else {
			handleResponseError(response);
		}
		return null;
	}
	
	private JSONRPC2Response sendRequestToServer(JSONRPC2Session session, JSONRPC2Request request) throws LitecoinAPIException {
		JSONRPC2Response response = null;

		try {
			JSONRPC2SessionOptions options = new JSONRPC2SessionOptions();
			options.ignoreVersion(true);
			session.setOptions(options);
			response = session.send(request);

		} catch (JSONRPC2SessionException e) {
			handleResponseError(response);
		}
		return response;
	}
	
	private void handleResponseError(JSONRPC2Response response) throws LitecoinAPIException {
		JSONRPC2Error err = response.getError();
		System.err.println("\terror.code    : " + err.getCode());
		System.err.println("\terror.message : " + err.getMessage());
		System.err.println("\terror.data    : " + err.getData());
		throw new LitecoinAPIException(err);
	}

	private JSONRPC2Session getSession() {

		Authenticator.setDefault(new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(rpcUser, rpcPassword
						.toCharArray());
			}
		});
		URL serverURL = null;

		try {
			serverURL = new URL(rpcAddress + ":" + rpcPort);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return new JSONRPC2Session(serverURL);
	}

	private static int nextRequestNumber = 0;
	
	private static String getNextRequestId() {
		nextRequestNumber++;
		return "rqsr:" + nextRequestNumber; 
	}
	

	public void setPassword(String pass) {
		rpcPassword = pass;		
	}

	public void setUser(String user) {
		rpcUser = user;
	}

	public void setPort(int port) {
		rpcPort = port;
		
	}
}
