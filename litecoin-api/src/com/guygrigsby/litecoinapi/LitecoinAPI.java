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
import java.util.Scanner;

public class LitecoinAPI {
	private static ICoinWallet litecoinWallet;

	private LitecoinAPI() {

	}

	/**
	 * Method for parsing the litecoin config file and creating a wallet. This
	 * should only be called once unless the config file changes. To get a
	 * wallet that has already been created call
	 * {@link LiteCoinAPI#getLiteCoinWallet()}
	 * 
	 * @param configFile
	 *            a {@code java.io.File} representing the litecoin configuration
	 *            file.
	 * @return the newly created wallet.
	 * @throws LitecoinAPIException
	 *             if the wallet could not be created
	 */
	public static ICoinWallet createLiteCoinWallet(File configFile)
			throws LitecoinAPIException {
		try (Scanner in = new Scanner(configFile)) {
			String rpcUser = null;
			String rpcPassword = null;
			int rpcPort = 0;
			while (in.hasNext()) {
				String next = in.nextLine();
				String value = next.substring(next.lastIndexOf("=") + 1).trim();
				if (next.startsWith("rpcuser")) {
					rpcUser = value;
				}
				if (next.startsWith("rpcpassword")) {
					rpcPassword = value;
				}
				if (next.startsWith("rpcport")) {
					rpcPort = Integer.parseInt(value);
				}
			}
			litecoinWallet = new LitecoinWallet(rpcUser, rpcPassword, rpcPort);
		} catch (Exception e) {
			throw new LitecoinAPIException(
					"LitecoinWallet could not be created.", e);
		}
		return litecoinWallet;
	}

	/**
	 * Returns the wallet
	 * 
	 * @return the wallet
	 */
	public static ICoinWallet getLiteCoinWallet() {
		if (litecoinWallet == null) {
			throw new RuntimeException(
					"Must call LiteCoinAPI.createLiteCoinWallet() first");
		}
		return litecoinWallet;
	}
}
