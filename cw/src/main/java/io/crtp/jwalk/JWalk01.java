package io.crtp.jwalk;

import java.security.MessageDigest;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import java.security.Security;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.bitcoinj.core.Base58;
import org.bitcoinj.core.Sha256Hash;

// https://www.arcblock.io/blog/en/post/2018/08/16/index-bitcoin
// https://gobittest.appspot.com/Address
//-----------------------------------

public class JWalk01 {
    BitcoinRPCs bitcoinRPCs = new BitcoinRPCs();
    DataStore01 ds01 = new DataStore01();

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
        for (int blockX=0; blockX<1000; blockX++) {
        //for (int blockX=0; blockX<100; blockX++) {
        //for (int blockX=0; blockX<10; blockX++) {
        //for (int blockX=0; blockX<3; blockX++) {
            /***
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
             */
            parseBlock(blockX);
            //System.out.println(" "+blockX+" "+bitcoinRPCs.getBlock(bitcoinRPCs.getBlockHash(blockX),2));
        }
    }

    public void parseBlock(int blockNum) {
        int blockX=blockNum;
        System.out.println("block "+blockNum+"  hash  "+bitcoinRPCs.getBlockHash(blockNum));

        JSONObject blockJsonObj = bitcoinRPCs.getBlock(bitcoinRPCs.getBlockHash(blockX),2);
        JSONObject resultJsonObj = (JSONObject)blockJsonObj.get("result");
        String resultStr = resultJsonObj.toString();
        try {
            Object jsonObject = mapper.readValue(resultStr, Object.class);
            String prettyJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject);
            System.out.println(" result"+blockNum+" "+prettyJson);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        String addr = "ns";
        //System.out.println("printJsonObject vvvvvvvvv");
        Block javaBlock = new Block();  
        printJsonObject(resultJsonObj, javaBlock);
        //System.out.println("");
        addr = addressFromPubKey(javaBlock.getTempASM());
        //System.out.println(addr);
        ds01.putAddrData(addr, ""+blockNum);
        System.out.println();
        System.out.println();
        System.out.println("- - - - - - - - - - - - - - ");
        System.out.println();
        System.out.println();
    }

    public void parseTheFirstBlock() {
        int blockX=0;
        System.out.print(" blockX "+blockX+"  ");
        System.out.println(bitcoinRPCs.getBlockHash(blockX));

        JSONObject blockJsonObj = bitcoinRPCs.getBlock(bitcoinRPCs.getBlockHash(blockX),2);

        System.out.println("blockJsonObj contains result "+blockJsonObj.containsKey("result"));
        JSONObject resultJsonObj = (JSONObject)blockJsonObj.get("result");
        String resultStr = resultJsonObj.toString();
        try {
            Object jsonObject = mapper.readValue(resultStr, Object.class);
            String prettyJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject);
            System.out.println(" resultX "+prettyJson);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        String addr = "ns";
        //System.out.println("printJsonObject vvvvvvvvv");
        Block javaBlock = new Block();  
        printJsonObject(resultJsonObj, javaBlock);
        System.out.println("");
        addr = addressFromPubKey(javaBlock.getTempASM());
        System.out.println(addr);
    }

    public void printJsonObject(JSONObject jsonObj, Block jBlock) {
        for (Object key : jsonObj.keySet()) {
            String keyStr = (String)key;
            Object keyvalue = jsonObj.get(keyStr);
    
            if (keyvalue instanceof JSONObject) {
                    System.out.println("  ~~~~   nested ~~~~~");
                    printJsonObject((JSONObject)keyvalue, jBlock);
            } else if (keyvalue instanceof JSONArray) {
                System.out.println("key: "+ keyStr + " value: " + keyvalue);
                System.out.println("key: "+ keyStr);

                if (keyStr.contains("address")) {
                    ds01.putAddrData(keyvalue.toString(), "  -1");
                } else {

                    JSONArray jsonArray = (JSONArray)keyvalue;
                    //System.out.println();
                    System.out.println("jsonArray.size() "+jsonArray.size());

                    for (int i=0; i<jsonArray.size(); i++){
                        try {
                            JSONObject obj = (JSONObject)jsonArray.get(i);
                            //System.out.println(" array element "+i+" is "+obj.toString());
                            //System.out.println(" array element "+i);
                            printJsonObject(obj, jBlock);
                        } catch( Exception ex ){
                            System.out.println("error "+ex.toString());
                            System.out.println(jsonArray.get(i));
                            ex.printStackTrace();
                        }
                    }
                }
            } else if(keyStr.equals("asm")){
                System.out.println("  "+ keyStr + " : " + keyvalue);
                String[] splited = ((String)keyvalue).split(" ");
                if(splited.length < 2){
                    System.out.println();
                    System.out.println("  *******   "+keyvalue);
                    System.out.println();
                } else {
                    jBlock.setTempASM(splited[0]);
                }
            } else {
                System.out.println("  "+ keyStr + " : " + keyvalue);
            }
        }
    }

    public String addressFromPubKey( String a ){
        String result = "ns";
        try {
            //System.out.println("learn me btc: https://learnmeabitcoin.com/technical/hash-function");
            //https://gobittest.appspot.com/Address
            //System.out.println(" Step 1 ecdsa      "+a);
            byte[] iti = hexStringToByteArray(a);
            Sha256Hash itiHash = Sha256Hash.of(iti);
            //System.out.println(" Step 2 256        "+ itiHash.toString());

            MessageDigest rmdx = MessageDigest.getInstance("RipeMD160", "BC");
            byte[] r1x = rmdx.digest(itiHash.getBytes());
            //Converting the byte array in to HexString format
            StringBuffer hexString = new StringBuffer();
            for (int i = 0;i<r1x.length;i++) {
                hexString.append(Integer.toHexString(0xFF & r1x[i]));
            }
            //System.out.println(" Step 3 md160 of 2 " + hexString.toString());

            byte[] zero = hexStringToByteArray("00");
            byte[] s5 = new byte[r1x.length + 1];
            s5[0] = zero[0];
            System.arraycopy(r1x, 0, s5, 1, r1x.length);
            
            ////System.out.println(byteToHexStr(s5));
            Sha256Hash s5x = Sha256Hash.of(s5);
            //System.out.println(" Step 5 ha356 of 4 " + s5x.toString());

            Sha256Hash s6x = Sha256Hash.of(s5x.getBytes());
            //System.out.println(" Step 6 ha356 of 5 " + s6x.toString());

            byte[] first4of6 = new byte[4];
            System.arraycopy(s6x.getBytes(), 0, first4of6, 0, 4);
            //System.out.println(byteToHexStr(first4of6));

            byte[] s8 = new byte[s5.length + first4of6.length];

            System.arraycopy(s5, 0, s8, 0, s5.length);
            System.arraycopy(first4of6, 0, s8, s5.length, first4of6.length);
            //System.out.println(byteToHexStr(s8));

            //7 - First four bytes of 6
            result = Base58.encode(s8);
            //System.out.println(result);
        } catch(Exception ex) {
            System.out.println(ex.toString());
            ex.printStackTrace();
        }
        return result;
    }

    public static String byteToHexStr(byte[] b1) {
        StringBuilder strBuilder = new StringBuilder();
        for(byte val : b1) {
           strBuilder.append(String.format("%02x", val&0xff));
        }
        return strBuilder.toString();
    }

    /* s must be an even-length string. */
    //https://stackoverflow.com/questions/140131/convert-a-string-representation-of-a-hex-dump-to-a-byte-array-using-java/140861#140861
    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                                + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }    

    static {
        Security.addProvider(new BouncyCastleProvider());
    }
}
