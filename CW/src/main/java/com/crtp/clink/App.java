package com.crtp.clink;

// The Client sessions package
import com.thetransactioncompany.jsonrpc2.client.*;

// The Base package for representing JSON-RPC 2.0 messages
import com.thetransactioncompany.jsonrpc2.*;

// The JSON Smart package for JSON encoding/decoding (optional)
import net.minidev.json.*;

// For creating URLs
import java.net.*;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ) {
        System.out.println( "What's up Doc!" );

        // The JSON-RPC 2.0 server URL
        URL serverURL = null;

        try {
          serverURL = new URL("http://jsonrpc.example.com:8080");
        } catch (MalformedURLException e) {
	// handle exception...
       }

       // Create new JSON-RPC 2.0 client session
       JSONRPC2Session mySession = new JSONRPC2Session(serverURL);
    }
}
