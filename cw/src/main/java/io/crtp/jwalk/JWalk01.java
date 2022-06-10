package io.crtp.jwalk;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONValue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.bitcoinj.core.Base58;

// https://www.arcblock.io/blog/en/post/2018/08/16/index-bitcoin
// https://gobittest.appspot.com/Address




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

    ObjectMapper mapper = new ObjectMapper();
    public void showTheFirst10Blocks() {
        //for (int blockX=0; blockX<1000; blockX++) {
        for (int blockX=0; blockX<10; blockX++) {
            System.out.println(" blockX "+blockX);
            System.out.println(bitcoinRPCs.getBlockHash(blockX));
            System.out.println();

            String block = ((JSONObject)(bitcoinRPCs.getBlock(bitcoinRPCs.getBlockHash(blockX),2))).toString();
            try {
                Object jsonObject = mapper.readValue(block, Object.class);
                String prettyJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject);
                System.out.println(" blockX "+blockX+" "+prettyJson);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            //System.out.println(" "+blockX+" "+bitcoinRPCs.getBlock(bitcoinRPCs.getBlockHash(blockX),2));
        }
    }

    public void parseTheFirstBlock() {
        int blockX=0;
        System.out.println(" blockX "+blockX);
        System.out.println(bitcoinRPCs.getBlockHash(blockX));
        System.out.println();

        String block = ((JSONObject)(bitcoinRPCs.getBlock(bitcoinRPCs.getBlockHash(blockX),2))).toString();

        try {
            Object jsonObject = mapper.readValue(block, Object.class);
            String prettyJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject);
            System.out.println(" blockX "+blockX+" "+prettyJson);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }        

        System.out.println();
        System.out.println();
        System.out.println();

        JSONObject blockJsonObj = bitcoinRPCs.getBlock(bitcoinRPCs.getBlockHash(blockX),2);

        //HashMap keySet = blockJsonObj.keySet();
        //String[] fields = wtf.getNames(blockJsonObj);
        System.out.println("");
        System.out.println("blockJsonObj contains result "+blockJsonObj.containsKey("result"));
        System.out.println("blockJsonObj contains xyz "+blockJsonObj.containsKey("xyz"));
        //Set resultKeySet = blockJsonObj.keySet();
        JSONObject resultJsonObj = (JSONObject)blockJsonObj.get("result");
        String resultStr = resultJsonObj.toString();
        try {
            Object jsonObject = mapper.readValue(resultStr, Object.class);
            String prettyJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject);
            System.out.println(" resultX "+prettyJson);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        System.out.println("");
        System.out.println("printJsonObject vvvvvvvvv");
        System.out.println("");        
        printJsonObject(resultJsonObj);
    }

    public void printJsonObject(JSONObject jsonObj) {
        for (Object key : jsonObj.keySet()) {
            String keyStr = (String)key;
            Object keyvalue = jsonObj.get(keyStr);
    
            //System.out.println("key: "+ keyStr + " value: " + keyvalue);
            //for nested objects iteration if required
            if (keyvalue instanceof JSONObject) {
                    System.out.println("");
                    System.out.println("  ~~~~   nested ~~~~~");
                    System.out.println("");        
                    printJsonObject((JSONObject)keyvalue);
            } else if (keyvalue instanceof JSONArray) {
                System.out.println("key: "+ keyStr + " value: " + keyvalue);

                JSONArray jsonArray = (JSONArray)keyvalue;

                System.out.println();
                System.out.println();
                System.out.println("jsonArray.size() "+jsonArray.size());

                for (int i=0; i<jsonArray.size(); i++){
                    JSONObject obj = (JSONObject)jsonArray.get(i);
                    System.out.println(" array element "+i+" is "+obj.toString());
                    printJsonObject(obj);
                }
                 
            } else {
                System.out.println("  "+ keyStr + " : " + keyvalue);
            }
        }
    }
}
