package io.crtp.jwalk;

// https://github.com/cdelargy/BitcoinRPCClient
// https://github.com/clanie/bitcoind-client
// https://github.com/johannbarbie/BitcoindClient4J
// https://github.com/priiduneemre/btcd-cli4j
// https://github.com/SulacoSoft/BitcoindConnector4J
// https://github.com/nitinsurana/Litecoin-Bitcoin-RPC-Java-Connector
// https://software.dzhuvinov.com/json-rpc-2.0-client.html
// https://en.bitcoin.it/wiki/Bitcoin-JSON-RPC-Client
// https://bitcoin.stackexchange.com/questions/7529/how-to-communicate-between-java-and-bitcoind
// https://github.com/ConsensusJ/consensusj
// https://github.com/BlockchainCommons/Learning-Bitcoin-from-the-Command-Line/blob/master/04_4__Interlude_Using_Curl.md

import org.json.simple.JSONObject;

//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.SerializationFeature;

public class App {
    public static void main( String[] args ) {
        //System.out.println( "What's up Doc!" );
        //System.out.println( "Exctract all the addresses." );
        //System.out.println( "Follow the utx flow." );
		//System.out.println();

		JWalk01 jw = new JWalk01();
		//jw.showTheFirst10Blocks();
        //jw.parseTheFirstBlock();
        jw.lmb("04678afdb0fe5548271967f1a67130b7105cd6a828e03909a67962e0ea1f61deb649f6bc3f4cef38c4f35504e51ec112de5c384df7ba0b8d578a4c702b6bf11d5f");
    }
}
    

