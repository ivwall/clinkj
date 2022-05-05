package com.mkyong.core.utils;

import com.thetransactioncompany.jsonrpc2.client.*;
import com.thetransactioncompany.jsonrpc2.*;
import net.minidev.json.*;
import java.net.*;
import org.joda.time.LocalDate;

// ----------------------------------------
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

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

		System.out.println();
		System.out.println();
		System.out.println();

		DefaultHttpClient httpclient = new DefaultHttpClient();

		JSONObject json = new JSONObject();
		//json.put("id", id);
		//json.put("method", method);
		json.put("id", UUID.randomUUID().toString());
		json.put("method", "getblockcount");
		/***
		if (null != params) {
			JSONArray array = new JSONArray();
			array.addAll(params);
			json.put("params", params);
		}
		***/
		JSONObject responseJsonObj = null;
		try {
			//httpclient.getCredentialsProvider().setCredentials(new AuthScope("localhost", 8332),
			//		new UsernamePasswordCredentials("btc", "123"));
			httpclient.getCredentialsProvider().setCredentials(new AuthScope("10.10.89.92", 8332),
					new UsernamePasswordCredentials("Anch0rCh@1n", "abc1234"));
			StringEntity myEntity = new StringEntity(json.toJSONString());
			System.out.println(json.toString());
			HttpPost httppost = new HttpPost("http://10.10.89.92:8332");
			httppost.setEntity(myEntity);

			System.out.println("executing request" + httppost.getRequestLine());
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();

			System.out.println("----------------------------------------");
			System.out.println(response.getStatusLine());
			if (entity != null) {
				System.out.println("Response content length: " + entity.getContentLength());
				// System.out.println(EntityUtils.toString(entity));
			}
			JSONParser parser = new JSONParser();
			responseJsonObj = (JSONObject) parser.parse(EntityUtils.toString(entity));
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (org.json.simple.parser.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// When HttpClient instance is no longer needed,
			// shut down the connection manager to ensure
			// immediate deallocation of all system resources
			httpclient.getConnectionManager().shutdown();
		}
		Long blockcount = (Long)responseJsonObj.get("result");
		System.out.println(" blockcount = "+blockcount.toString() );

		System.out.println();
		System.out.println();
		System.out.println();



		System.out.println(getLocalCurrentDate());
	}

	private static String getLocalCurrentDate() {
		
		LocalDate date = new LocalDate();
		return date.toString();
		
	}

}
