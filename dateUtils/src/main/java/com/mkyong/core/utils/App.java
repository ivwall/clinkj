package com.mkyong.core.utils;

import com.thetransactioncompany.jsonrpc2.client.*;
import com.thetransactioncompany.jsonrpc2.*;
import net.minidev.json.*;
import java.net.*;
import org.joda.time.LocalDate;

//https://software.dzhuvinov.com/json-rpc-2.0-client.html
//
//https://github.com/johannbarbie/BitcoindClient4J
//https://github.com/priiduneemre/btcd-cli4j
//https://en.bitcoin.it/wiki/Bitcoin-JSON-RPC-Client
//https://bitcoin.stackexchange.com/questions/7529/how-to-communicate-between-java-and-bitcoind
//https://github.com/ConsensusJ/consensusj
//https://github.com/priiduneemre/btcd-cli4j
//https://github.com/BlockchainCommons/Learning-Bitcoin-from-the-Command-Line/blob/master/04_4__Interlude_Using_Curl.md
//
//

public class App {

	public static void main(String[] args) {
		URL serverURL = null;
		try {
			String method = "getblockcount";
			int requestID = 0;
			JSONRPC2Request request = new JSONRPC2Request(method,requestID);
			JSONRPC2Response response = null;
			serverURL = new URL("http://10.10.89.92:8332");
			JSONRPC2Session mySession = new JSONRPC2Session(serverURL);
			response = mySession.send(request);
		}catch(Exception ex){
			System.out.println(ex.toString());
		}
		System.out.println(getLocalCurrentDate());
	}

	private static String getLocalCurrentDate() {
		
		LocalDate date = new LocalDate();
		return date.toString();
		
	}

}
