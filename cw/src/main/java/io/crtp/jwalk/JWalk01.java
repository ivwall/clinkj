package io.crtp.jwalk;

import java.security.MessageDigest;
import java.math.BigInteger;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import java.security.Security;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONValue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.bitcoinj.core.Base58;
import org.bitcoinj.core.Sha256Hash;

// https://www.arcblock.io/blog/en/post/2018/08/16/index-bitcoin
// https://gobittest.appspot.com/Address

import java.util.Arrays;

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
        cypherDev(javaBlock);
        System.out.println("");
        System.out.println("");
        System.out.println("transition code");
        System.out.println("");
        System.out.println("");
        addressFromPubKey(javaBlock.getTempASM());
        System.out.println("");
        System.out.println("");
        String ecdsa = "04CAAA5C0BDDAA22C9D3C0DDAEC8550791891BB2C2FB0F9084D02F927537DE4F443ACED7DEB488E9BFE60D6C68596E6C78D95E20622CC05474FD962392BDC6AF29";
        addressFromPubKey(ecdsa);
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
            //Sha256Hash tx_hash = new Sha256Hash(ECDA);
            System.out.println("");
            Sha256Hash firstHash = Sha256Hash.of(ECDA.getBytes());
            System.out.println(firstHash.toString());
            byte[] hash2 =  Sha256Hash.hash(ECDA.getBytes());
            String hash2str = new String(hash2);
            System.out.println(hash2str);
            //"# prints
            //bcPub: 04CAAA5C0BDDAA22C9D3C0DDAEC8550791891BB2C2FB0F9084D02F927537DE4F443ACED7DEB488E9BFE60D6C68596E6C78D95E20622CC05474FD962392BDC6AF29"
            System.out.println("04CAAA5C0BDDAA22C9D3C0DDAEC8550791891BB2C2FB0F9084D02F927537DE4F443ACED7DEB488E9BFE60D6C68596E6C78D95E20622CC05474FD962392BDC6AF29");
            System.out.println("");
            System.out.println("");
            MessageDigest sha = MessageDigest.getInstance("SHA-256");
            byte[] s1 = sha.digest(ECDA.getBytes("UTF-8"));
            //System.out.println("  sha: " + bytesToHex(s1).toUpperCase());
            //System.out.println("  sha: " + bytesToHex(s1).toUpperCase());
            System.out.println("  sha: " + toHexString(s1).toUpperCase());
            //# prints
            System.out.println("  sha: 7524DC35AEB4B62A0F1C90425ADC6732A7C5DF51A72E8B90983629A7AEC656A0");

            MessageDigest rmd = MessageDigest.getInstance("RipeMD160", "BC");
            byte[] r1 = rmd.digest(s1);

            byte[] r2 = new byte[r1.length + 1];
            r2[0] = 0;
            for (int i = 0 ; i < r1.length ; i++) r2[i+1] = r1[i];
            //System.out.println("  rmd: " + bytesToHex(r2).toUpperCase());            
            System.out.println("  rmd: " + toHexString(r2).toUpperCase());            
            //# prints
            System.out.println("  rmd: 00C5FAE41AB21FA56CFBAFA3AE7FB5784441D11CEC");


            byte[] s2 = sha.digest(r2);
            //System.out.println("  sha: " + bytesToHex(s2).toUpperCase());
            System.out.println("  sha: " + toHexString(s2).toUpperCase());
            byte[] s3 = sha.digest(s2);
            //System.out.println("  sha: " + bytesToHex(s3).toUpperCase());            
            System.out.println("  sha: " + toHexString(s3).toUpperCase());  
                        
            byte[] a1 = new byte[25];
            for (int i = 0 ; i < r2.length ; i++) a1[i] = r2[i];
            for (int i = 0 ; i < 5 ; i++) a1[20 + i] = s3[i];
            
            System.out.println("  adr: " + Base58.encode(a1));
            //# prints
            System.out.println("  adr: 1K3pg1JFPtW7NvKNA77YCVghZRq2s1LwVF");

        } catch(Exception ex) {
            System.out.println(ex.toString());
            ex.printStackTrace();
        }
    }

    public void addressFromPubKey( String e ){
        try {
            String a = e;
            System.out.println("addressFromPubKey");
            String[] strArray = a.split(" ");
            System.out.println(strArray[0]);
            String ECDA = strArray[0];
            byte[] pkArray = ECDA.getBytes();
            System.out.println("pubkey as bytes: "+Arrays.toString(pkArray));
            Sha256Hash firstHash = Sha256Hash.of(ECDA.getBytes());
            System.out.println("       "+ firstHash.toString());
            //byte[] hash2 =  Sha256Hash.hash(ECDA.getBytes());
            //String hash2str = new String(hash2);
            //System.out.println(hash2str);
            //"# prints
            //bcPub: 04CAAA5C0BDDAA22C9D3C0DDAEC8550791891BB2C2FB0F9084D02F927537DE4F443ACED7DEB488E9BFE60D6C68596E6C78D95E20622CC05474FD962392BDC6AF29"
            //System.out.println("04CAAA5C0BDDAA22C9D3C0DDAEC8550791891BB2C2FB0F9084D02F927537DE4F443ACED7DEB488E9BFE60D6C68596E6C78D95E20622CC05474FD962392BDC6AF29");
            //System.out.println("");
            MessageDigest sha = MessageDigest.getInstance("SHA-256");
            byte[] s1 = sha.digest(ECDA.getBytes("UTF-8"));
            //System.out.println("  sha: " + bytesToHex(s1).toUpperCase());
            //System.out.println("  sha: " + bytesToHex(s1).toUpperCase());
            System.out.println("  sha: " + toHexString(s1).toUpperCase());
            //# prints
            //System.out.println("  sha: 7524DC35AEB4B62A0F1C90425ADC6732A7C5DF51A72E8B90983629A7AEC656A0");

            MessageDigest rmd = MessageDigest.getInstance("RipeMD160", "BC");
            byte[] r1 = rmd.digest(s1);

            byte[] r2 = new byte[r1.length + 1];
            r2[0] = 0;
            for (int i = 0 ; i < r1.length ; i++) r2[i+1] = r1[i];
            //System.out.println("  rmd: " + bytesToHex(r2).toUpperCase());            
            System.out.println("  rmd: " + toHexString(r2).toUpperCase());            
            //# prints
            //System.out.println("  rmd: 00C5FAE41AB21FA56CFBAFA3AE7FB5784441D11CEC");

            byte[] s2 = sha.digest(r2);
            //System.out.println("  sha: " + bytesToHex(s2).toUpperCase());
            System.out.println("  sha: " + toHexString(s2).toUpperCase());
            byte[] s3 = sha.digest(s2);
            //System.out.println("  sha: " + bytesToHex(s3).toUpperCase());            
            System.out.println("  sha: " + toHexString(s3).toUpperCase());  
                        
            byte[] a1 = new byte[25];
            for (int i = 0 ; i < r2.length ; i++) a1[i] = r2[i];
            for (int i = 0 ; i < 5 ; i++) a1[20 + i] = s3[i];
            
            System.out.println("  adr: " + Base58.encode(a1));
            //# prints
            //System.out.println("  adr: 1K3pg1JFPtW7NvKNA77YCVghZRq2s1LwVF");

        } catch(Exception ex) {
            System.out.println(ex.toString());
            ex.printStackTrace();
        }
    }

    public static String toHexString(byte[] hash) {
        BigInteger number = new BigInteger(1, hash);
        StringBuilder hexString = new StringBuilder(number.toString(16));
        while (hexString.length() < 64) {
            hexString.insert(0, '0');
        }
        return hexString.toString();
    }

    private static void convertStringToHex(String str) {
        StringBuilder stringBuilder = new StringBuilder();

        char[] charArray = str.toCharArray();

        for (char c : charArray) {
            String charToHex = Integer.toHexString(c);
            stringBuilder.append(charToHex);
        }

        System.out.println("Converted Hex from String: "+stringBuilder.toString());
    }


    static {
        Security.addProvider(new BouncyCastleProvider());
    }
}
