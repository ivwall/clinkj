package io.crtp.jwalk;

import com.thetransactioncompany.jsonrpc2.client.*;
import com.thetransactioncompany.jsonrpc2.*;
import net.minidev.json.*;
import java.net.*;
import org.joda.time.LocalDate;

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
import org.json.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

public class BitcoinRPCs {

	//"10.10.89.92", 8332
	private String nodeIP  = "10.10.89.92";
	//"Anch0rCh@1n", "abc1234"
	private String user    = "Anch0rCh@1n";
	private String pw      = "abc1234";
	private String nodeURL = "http://localhost:8332";

    public BitcoinRPCs() {
    }

	private JSONObject invokeRPC(String id, String method, List<String> params) {
		DefaultHttpClient httpclient = new DefaultHttpClient();

		JSONObject json = new JSONObject();
		json.put("id", id);
		json.put("method", method);
		if (null != params) {
			JSONArray array = new JSONArray();
			array.addAll(params);
			json.put("params", params);
		}
		JSONObject responseJsonObj = null;
		try {
			httpclient.getCredentialsProvider().setCredentials(new AuthScope(nodeIP, 8332),
					new UsernamePasswordCredentials(user, pw));
			StringEntity myEntity = new StringEntity(json.toJSONString());
			System.out.println(json.toString());
			HttpPost httppost = new HttpPost(nodeURL);
			httppost.setEntity(myEntity);

			System.out.println("executing request" + httppost.getRequestLine());
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();

			System.out.println("----------------------------------------");
			System.out.println(response.getStatusLine());
			if (entity != null) {
				System.out.println("Response content length: " + entity.getContentLength());
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
		return responseJsonObj;
	}

	private JSONObject invokeRPC2(String id, String method, List<Object> params) {
		System.out.println("JSONObject invokeRPC2");
		DefaultHttpClient httpclient = new DefaultHttpClient();

		JSONObject json = new JSONObject();
		json.put("id", id);
		json.put("method", method);
		//json.put("params",params);
		if (null != params) {
			//JSONArray array = new JSONArray();
			//array.addAll(params);
			json.put("params", params);
		}
		JSONObject responseJsonObj = null;
		try {
			//httpclient.getCredentialsProvider().setCredentials(new AuthScope(nodeIP, 8332),
			//		new UsernamePasswordCredentials(user, pw));
			httpclient.getCredentialsProvider().setCredentials(new AuthScope("10.10.89.92", 8332),
					new UsernamePasswordCredentials("Anch0rCh@1n", "abc1234"));
			StringEntity myEntity = new StringEntity(json.toJSONString());
			System.out.println(json.toString());
			//HttpPost httppost = new HttpPost(nodeURL);
			HttpPost httppost = new HttpPost("http://10.10.89.92:8332");
			httppost.setEntity(myEntity);

			System.out.println("executing request" + httppost.getRequestLine());
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();

			System.out.println("----------------------------------------");
			System.out.println(response.getStatusLine());
			if (entity != null) {
				System.out.println("Response content length: " + entity.getContentLength());
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
		return responseJsonObj;
	}



    public void getBlockcount(){
        System.out.println("BitcoinRPCs.getBlockcont");

		DefaultHttpClient httpclient = new DefaultHttpClient();

		JSONObject json = new JSONObject();
		json.put("id", UUID.randomUUID().toString());
		json.put("method", "getblockcount");
		JSONObject responseJsonObj = null;
		try {
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
			}
			JSONParser parser = new JSONParser();
			responseJsonObj = (JSONObject) parser.parse(EntityUtils.toString(entity));
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (org.json.simple.parser.ParseException e) {
			e.printStackTrace();
		}
		Long blockcount = (Long)responseJsonObj.get("result");
		System.out.println(" blockcount = "+blockcount.toString() );
    }




	
    public void getBlockhash(){
        System.out.println("BitcoinPRCs.getBlockhash");
		DefaultHttpClient httpclient = new DefaultHttpClient();

		JSONObject json = new JSONObject();
		json.put("id", UUID.randomUUID().toString());
		JSONObject responseJsonObj = null;
		json.put("method", "getblockhash");
		String block100K = "100000";
		String[] params = { block100K };
		if (null != params) {
			Collection<Integer> list = new LinkedList<Integer>();
			list.add(10000);
			json.put("params", list);
		}
		try {
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
			} else {
				System.out.println("entity != null");
			}
			JSONParser parser = new JSONParser();
			responseJsonObj = (JSONObject)parser.parse(EntityUtils.toString(entity));
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (org.json.simple.parser.ParseException e) {
			e.printStackTrace();
		}
		String callResult = (String)responseJsonObj.get("result");
		System.out.println(" callResult = "+callResult );

    }

    public void getBlock(){
        System.out.println("BitcoinPRCs.getBlock");
		DefaultHttpClient httpclient = new DefaultHttpClient();

		JSONObject json = new JSONObject();
		json.put("id", UUID.randomUUID().toString());
		JSONObject responseJsonObj = null;
		json.put("method", "getblock");
		String[] params = { "100000" };
		if (null != params) {
			Collection<Object> list = new LinkedList<Object>();
			list.add("0000000099c744455f58e6c6e98b671e1bf7f37346bfd4cf5d0274ad8ee660cb");
			json.put("params", list);
		}
		try {
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
			} else {
				System.out.println("entity != null");
			}
			JSONParser parser = new JSONParser();
			responseJsonObj = (JSONObject)parser.parse(EntityUtils.toString(entity));
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (org.json.simple.parser.ParseException e) {
			e.printStackTrace();
		}
		//String callResult = (String)responseJsonObj.get("result");
		//System.out.println(" callResult = "+callResult );
		System.out.println(" callResult = "+responseJsonObj.toString() );
    }   


	public void getBlock2(){
        System.out.println("BitcoinPRCs.getBlock2(hash, 2)");

		try {
			Collection<Object> list = new LinkedList<Object>();
			list.add("0000000099c744455f58e6c6e98b671e1bf7f37346bfd4cf5d0274ad8ee660cb");
			list.add(2);

			JSONObject json = invokeRPC2( UUID.randomUUID().toString(),
										"getblock",	
										(LinkedList)list );

 			System.out.println(" callResult = " + json.toString() );

		} catch( Exception ex) {
			System.out.println(ex.toString());
		}
	}   
} 