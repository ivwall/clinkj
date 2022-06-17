package io.crtp.jwalk;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONValue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.bitcoinj.core.Base58;
import org.bitcoinj.core.Sha256Hash;

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

        System.out.println("");
        System.out.println("blockJsonObj contains result "+blockJsonObj.containsKey("result"));
        System.out.println("blockJsonObj contains xyz "+blockJsonObj.containsKey("xyz"));
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
        Block javaBlock = new Block();  
        printJsonObject(resultJsonObj, javaBlock);
        System.out.println("");
        System.out.println("");
        //System.out.println("javaBlock.getTempASM "+javaBlock.getTempASM());
        //System.out.println("");
        //System.out.println("");

        cypherDev(javaBlock);

    }

    public void printJsonObject(JSONObject jsonObj, Block jBlock) {
        for (Object key : jsonObj.keySet()) {
            String keyStr = (String)key;
            Object keyvalue = jsonObj.get(keyStr);
    
            if (keyvalue instanceof JSONObject) {
                    System.out.println("");
                    System.out.println("  ~~~~   nested ~~~~~");
                    System.out.println("");        
                    printJsonObject((JSONObject)keyvalue, jBlock);
            } else if (keyvalue instanceof JSONArray) {
                System.out.println("key: "+ keyStr + " value: " + keyvalue);

                JSONArray jsonArray = (JSONArray)keyvalue;

                System.out.println();
                System.out.println();
                System.out.println("jsonArray.size() "+jsonArray.size());

                for (int i=0; i<jsonArray.size(); i++){
                    JSONObject obj = (JSONObject)jsonArray.get(i);
                    System.out.println(" array element "+i+" is "+obj.toString());
                    printJsonObject(obj, jBlock);
                }
            } else if(keyStr.equals("asm")){
                System.out.println("  "+ keyStr + " : " + keyvalue);                 
                jBlock.setTempASM((String)keyvalue);
            } else {
                System.out.println("  "+ keyStr + " : " + keyvalue);
            }
        }
    }

    public void cypherDev(Block b){
        try {
            String a = b.getTempASM();
            System.out.println("cypherDev");
            System.out.println("");
            //System.out.println(a);
            String[] strArray = a.split(" ");
            System.out.println(strArray[0]);
            String ECDA = strArray[0];
            System.out.println("");
            Sha256Hash firstHash = Sha256Hash.of(ECDA.getBytes());
            System.out.println(firstHash.toString());
            byte[] hash2 =  Sha256Hash.hash(ECDA.getBytes());
            String hash2str = new String(hash2);
            System.out.println(hash2str);
        } catch(Exception ex) {
            System.out.println(ex.toString());
            ex.printStackTrace();
        }
    }
}
