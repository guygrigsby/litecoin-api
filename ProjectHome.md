# Overview #
This is a java wrapper for making calls to a litecoin wallet server. I decided to write an application in Java that uses the Litecoin wallet and could not find a Java API to do it so I am writing this as I write the other program and thought it may be of use to others. I will continue to update this as I need to. The project is in it's early stages and as such only the basic calls have been implemented. Included in this source is a slightly modified version of JSONRPC-Base by Vladimir Dzhuvinov. Required libraries are json-smart-1.2.jar and jsonrpc-client-1.14.4.jar. Other versions of these libraries have not been tested with this project yet.
## Example of use: ##

```
	File config = new File( "//path//to//litecoin.conf");
	ICoinWallet wallet = LiteCoinAPI.getLiteCoinWallet(config);
	double amountToSend = 2.1;
	String tx = wallet.sendPayment(LITECOIN_TESTNET_ADDRESS, amountToSend);
	pl(tx);
```
## or ##
```
	String rpcUser = "username";
	String rpcPassword = "userPass";
	ICoinWallet wallet = new LitecoinWallet(rpcUser, rpcPassword);
```
## Documentation ##
I am trying to keep the javadoc comments updated so all you have to do is build the javadocs.