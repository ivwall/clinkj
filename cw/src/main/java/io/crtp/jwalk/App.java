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
public class App {
    public static void main( String[] args ) {
        System.out.println( "What's up Doc!" );
        System.out.println( "Exctract all the addresses." );
        System.out.println( "Follow the utx flow." );
		System.out.println();

		BitcoinRPCs bitcoinRPCs = new BitcoinRPCs();

		bitcoinRPCs.getBlockHash(0);		
		System.out.println();
		System.out.println();

		JWalk01 jw = new JWalk01();
		jw.getBlockCount();
		System.out.println("Hash of block     10 "+jw.getBlockHash(10));
		System.out.println("Hash of block 700321 "+jw.getBlockHash(700321));

		System.out.println();
		System.out.println((jw.getBlock(jw.getBlockHash(10),2)).toString());

		System.out.println();
		System.out.println((jw.getBlock(jw.getBlockHash(700321),2)).toString());

		System.out.println();
		System.out.println((jw.getBlock(jw.getBlockHash(350160),2)).toString());
    }
}
    

