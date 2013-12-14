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

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import net.minidev.json.JSONObject;


public class LitecoinWallet extends JSONRPCMethodCaller implements ICoinWallet {

	public LitecoinWallet(String user, String pass) {
		this(user, pass, LITECOIND_DEFAULT_PORT);
	}

	public LitecoinWallet(String user, String pass, int port) {
		this(user, pass, port, LOCALHOST_ADDRESS);
	}
	
	public LitecoinWallet(String user, String pass, int port, String serverAddress) {
		super(user, pass, port, serverAddress);
	}

	/* (non-Javadoc)
	 * @see com.guygrigsby.liteclypse.wallet.ICoinWallet#setPassord(java.lang.String)
	 */
	@Override
	public void setPassword(String pass) {
		rpcPassword = pass;		
	}

	/* (non-Javadoc)
	 * @see com.guygrigsby.liteclypse.wallet.ICoinWallet#setUser(java.lang.String)
	 */
	@Override
	public void setUser(String user) {
		rpcUser = user;
	}
	/*
	 * (non-Javadoc)
	 * @see com.guygrigsby.liteclypse.wallet.ICoinWallet#setPort(int)
	 */
	@Override
	public void setPort(int port) {
		rpcPort = port;
		
	}

	/* (non-Javadoc)
	 * @see com.guygrigsby.liteclypse.wallet.ICoinWallet#verifyAddress(java.lang.String)
	 */
	@Override
	public String verifyAddress(String address) throws LitecoinAPIException{
		List<Object> params = new LinkedList<Object>();
		params.add(address);
		return callJSONRPCMethodForStringResponse("validateaddress", params);
	}
	
	/* (non-Javadoc)
	 * @see com.guygrigsby.liteclypse.wallet.ICoinWallet#sendPayment(java.lang.String, double)
	 */
	@Override
	public String sendPayment(String address, double amount) throws LitecoinAPIException  {
		List<Object> params = new LinkedList<Object>();
		params.add(address); // testwallet at http://testnet.litecointools.com/
		params.add(amount);
		return callJSONRPCMethodForStringResponse("sendtoaddress", params);
	}
	
	/* (non-Javadoc)
	 * @see com.guygrigsby.liteclypse.wallet.ICoinWallet#getBalance()
	 */
	@Override
	public double getBalance() throws LitecoinAPIException  {
		return callJSONRPCMethodForDoubleResponse("getbalance", null);
	}
	/*
	 * (non-Javadoc)
	 * @see com.guygrigsby.litecoinapi.ICoinWallet#backupWallet(java.lang.String)
	 */
	@Override
	public void backupWallet(File destination) throws LitecoinAPIException {
		List<Object> params = new LinkedList<Object>();
		params.add(destination.getAbsolutePath());
		callJSONRPCMethodForStringResponse("backupwallet", params);
	}
	
	/* (non-Javadoc)
	 * @see com.guygrigsby.liteclypse.wallet.ICoinWallet#getAnonomousAddress()
	 */
	@Override
	public String getNewAddress(String account) throws LitecoinAPIException  {
		List<Object> params = null;
		if (account != null) {
			params = new LinkedList<Object>();
			params.add(account);
		}
		return callJSONRPCMethodForStringResponse("getnewaddress", params);
	}
	
	/* (non-Javadoc)
	 * @see com.guygrigsby.liteclypse.wallet.ICoinWallet#getAllAccounts()
	 */
	@Override
	public Map<String, Double> getAllAccounts() throws LitecoinAPIException  {
		JSONObject ret = (JSONObject) callJSONRPCMethod("listaccounts", null);
		Set<String> keys = ret.keySet();
		Map<String, Double> accounts = new HashMap<String, Double>();
		for (String key: keys) {
			accounts.put(key, new Double((double)ret.get(key)));
		}
		return accounts;
	}
	
	/* (non-Javadoc)
	 * @see com.guygrigsby.liteclypse.wallet.ICoinWallet#getInfo()
	 */
	@Override
	public Map<String, String> getInfo() throws LitecoinAPIException {
		JSONObject ret =  (JSONObject) callJSONRPCMethod("getinfo", null);
		Set<String> keys = ret.keySet();
		Map<String, String> infos = new HashMap<String, String>();
		for (String key: keys) {
			infos.put(key, (String) ret.get(key));
		}
		return infos;
	}
	/*
	 * (non-Javadoc)
	 * @see com.guygrigsby.litecoinapi.ICoinWallet#getAccountAddress(java.lang.String)
	 */
	@Override
	public String getAccountAddress(String accountName) throws LitecoinAPIException {
		List<Object> params = new LinkedList<Object>();
		params.add(accountName);
		return callJSONRPCMethodForStringResponse("getaccountaddress", params);
	}
}
