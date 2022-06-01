package io.crtp.jwalk;

import org.json.simple.JSONObject;

public class JWalk01 {
    BitcoinRPCs bitcoinRPCs = new BitcoinRPCs();

    public JWalk01() {
    }

    public void getBlockCount(){
        bitcoinRPCs.getBlockCount();
    }

    public int getBlockCount2(){
        return 0;
    }

    public String getBlockHash(int b){
        return bitcoinRPCs.getBlockHash(b);
    }

    public JSONObject getBlock(String hash, int verbosity){
        return bitcoinRPCs.getBlock(hash,verbosity);
    }

}
