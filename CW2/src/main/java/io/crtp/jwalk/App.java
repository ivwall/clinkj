package io.crtp.jwalk;

// ----------------------------------------
// https://github.com/cdelargy/BitcoinRPCClient

// -------------
// other RPC java implementations
// https://github.com/clanie/bitcoind-client
// https://github.com/johannbarbie/BitcoindClient4J
// https://github.com/priiduneemre/btcd-cli4j
// https://github.com/SulacoSoft/BitcoindConnector4J
// https://github.com/nitinsurana/Litecoin-Bitcoin-RPC-Java-Connector

// earlier links
//https://software.dzhuvinov.com/json-rpc-2.0-client.html
//
//https://github.com/johannbarbie/BitcoindClient4J
//https://github.com/priiduneemre/btcd-cli4j
//https://en.bitcoin.it/wiki/Bitcoin-JSON-RPC-Client
//https://bitcoin.stackexchange.com/questions/7529/how-to-communicate-between-java-and-bitcoind
//https://github.com/ConsensusJ/consensusj
//https://github.com/priiduneemre/btcd-cli4j
//https://github.com/BlockchainCommons/Learning-Bitcoin-from-the-Command-Line/blob/master/04_4__Interlude_Using_Curl.md

public class App {
    public static void main( String[] args ) {
        System.out.println( "What's up Doc!" );

		BitcoinRPCs bitcoinRPCs = new BitcoinRPCs();
		bitcoinRPCs.getBlockcount();
		System.out.println();
		System.out.println();

		bitcoinRPCs.getBlockhash();		
		System.out.println();
		System.out.println();
    }
}
    

