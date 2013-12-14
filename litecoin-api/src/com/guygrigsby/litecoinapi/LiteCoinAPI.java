package com.guygrigsby.litecoinapi;

import java.io.File;
import java.util.Scanner;

public class LiteCoinAPI {
	private static ICoinWallet litecoinWallet;
	private LiteCoinAPI() {
		
	}
	/**
	 * Convenience method for parsing the litecoin config file
	 * and return a wallet.
	 * @param configFile
	 * @return the wallet or {@code null} if something went wrong.
	 */
	public static ICoinWallet getLiteCoinWallet(File configFile) {
		if (litecoinWallet == null) {
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
				return new LitecoinWallet(rpcUser, rpcPassword,rpcPort);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return litecoinWallet;
	}
}
