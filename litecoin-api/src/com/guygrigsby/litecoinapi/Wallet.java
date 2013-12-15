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
import java.util.Map;

public interface Wallet {

	public static final int LITECOIND_DEFAULT_PORT = 9333;
	public static final int LITECOIND_TESTNET_PORT = 19333;
	public static final String LITECOIN_TESTNET_ADDRESS = "n4NoMeyTYCMRwYY13eh4QAdVLsx5XuExMS";

	public static final String LOCALHOST_ADDRESS = "http://localhost";

	/**
	 * Backs up the wallet.
	 * 
	 * @param file
	 *            the destination. Must be absolute. Can be a directory or a
	 *            directory and filename. If just a directory is specified the
	 *            backup is named {@code wallet.dat.}
	 * @throws LitecoinAPIException
	 *             if the server cannot be reached
	 */
	public abstract void backupWallet(File file) throws LitecoinAPIException;
	/**
	 * Returns the account associated with the given address.
	 * @param address the address
	 * @return the account associated with the given address.
	 * @throws LitecoinAPIException
	 */
	public abstract String getAccount(String address) throws LitecoinAPIException;
	/**
	 * Returns the current Litecoin address for receiving payments to this
	 * account.
	 * 
	 * @return the current Litecoin address for receiving payments to this
	 *         account.
	 * @throws LitecoinAPIException
	 */
	public abstract String getAccountAddress(String accountName)
			throws LitecoinAPIException;

	/**
	 * Returns a {@code Map} that has account names as keys, account balances as
	 * values.
	 * 
	 * @return a {@code Map} that has account names as keys, account balances as
	 *         values.
	 * @throws LitecoinAPIException
	 */
	public abstract Map<String, Double> getAllAccounts()
			throws LitecoinAPIException;

	/**
	 * Returns the server's total available balance.
	 * 
	 * @return the server's total available balance.
	 * @throws LitecoinAPIException
	 */
	public abstract double getBalance() throws LitecoinAPIException;
	/**
	 * Gets info about the wallet.
	 * @return a {@code Map} of information about the wallet
	 * @throws LitecoinAPIException
	 */
	public abstract Map<String, String> getInfo() throws LitecoinAPIException;

	/**
	 * Returns a new Litecoin address for receiving payments. If {@code account} is
	 * specified (recommended), it is added to the address book so payments
	 * received with the address will be credited to {@code account}.
	 * 
	 * @param account the account. May be {@code null}
	 * @return the new address 
	 * @throws LitecoinAPIException
	 */
	public abstract String getNewAddress(String account)
			throws LitecoinAPIException;
	/**
	 * Sends a payment
	 * @param address the recipient
	 * @param amount the amount to send
	 * @return the tx id
	 * @throws LitecoinAPIException
	 */
	public abstract String sendPayment(String address, double amount)
			throws LitecoinAPIException;
	/**
	 * Return information about {@code address}.
	 * @param address the address
	 * @return the information as key value pairs
	 * @throws LitecoinAPIException
	 */
	public abstract Map<String, String> verifyAddress(String address)
			throws LitecoinAPIException;

}