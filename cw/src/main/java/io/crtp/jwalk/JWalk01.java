package io.crtp.jwalk;

import java.security.MessageDigest;
import java.math.BigInteger;
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

import java.util.Arrays;

// ------------------------
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;

//-----------------------------------
//ByteUtils.HEX.decode
import org.spongycastle.crypto.digests.RIPEMD160Digest;
import java.util.Base64;

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
        System.out.println("transition code");
        System.out.println("");
        System.out.println("");
        addressFromPubKey(javaBlock.getTempASM());
        //String hex = "4678afdb0fe5548271967f1a67130b7105cd6a828e03909a67962e0ea1f61deb649f6bc3f4cef38c4f35504e51ec112de5c384df7ba0b8d578a4c702b6bf11d5f";
        //addressFromPubKey(hex);
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

    public void lmb( String a ){
        try {
            System.out.println("learn me btc: https://learnmeabitcoin.com/technical/hash-function");
            //System.out.println(strArray[0]);
            //String ECDA = strArray[0];
            //Sha256Hash firstHash = Sha256Hash.of(ECDA.getBytes());
            //System.out.println("  btcj "+ firstHash.toString());
            //MessageDigest sha = MessageDigest.getInstance("SHA-256");
            //byte[] s1 = sha.digest(ECDA.getBytes("UTF-8"));
            //MessageDigest rmd = MessageDigest.getInstance("RipeMD160", "BC");
            //byte[] r1 = rmd.digest(s1);

            //https://gobittest.appspot.com/Address
            //  1 Public ECDSA Key
            //    a = ECDSA
            System.out.println(" Step 1 ecdsa      "+a);
            byte[] iti = hexStringToByteArray(a);
            Sha256Hash itiHash = Sha256Hash.of(iti);
            System.out.println(" Step 2 256        "+ itiHash.toString());

            MessageDigest rmdx = MessageDigest.getInstance("RipeMD160", "BC");
            byte[] r1x = rmdx.digest(itiHash.getBytes());
            //System.out.println(" Step 3 md160 of 2 "+ r1x.toString());

            //Converting the byte array in to HexString format
            StringBuffer hexString = new StringBuffer();
            for (int i = 0;i<r1x.length;i++) {
                hexString.append(Integer.toHexString(0xFF & r1x[i]));
            }
            System.out.println(" Step 3 md160 of 2 " + hexString.toString());

            String md160str = "00"+hexString.toString();
            System.out.println(" Step 4 add net    "+md160str+"  "+md160str.length());

            String str = md160str;      
            byte[] zero = hexStringToByteArray("00");
            //System.out.println(zero.toString()+"  "+zero.length);

            byte[] s5 = new byte[r1x.length + 1];
            s5[0] = zero[0];
            System.arraycopy(r1x, 0, s5, 1, r1x.length);
            
            System.out.println(byteToHexStr(s5));

            Sha256Hash s5x = Sha256Hash.of(s5);
            System.out.println(" Step 5 ha356 of 4 " + s5x.toString());

            Sha256Hash s6x = Sha256Hash.of(s5x.getBytes());
            System.out.println(" Step 6 ha356 of 5 " + s6x.toString());

            byte[] first4of6 = new byte[4];
            System.arraycopy(s6x.getBytes(), 0, first4of6, 0, 4);
            System.out.println(byteToHexStr(first4of6));

            byte[] s5xb = s5x.getBytes();
            byte[] s8 = new byte[s5.length + first4of6.length];

            //byte[] c = new byte[a.length + b.length];
            //System.arraycopy(a, 0, c, 0, a.length);
            //System.arraycopy(b, 0, c, a.length, b.length);

            System.arraycopy(s5, 0, s8, 0, s5.length);
            //System.out.println("s8.length "+s8.length);
            //System.out.println("first4of6.length "+first4of6.length);
            //System.out.println("s8.length - first4of6.length "+ (s8.length - first4of6.length));
            System.arraycopy(first4of6, 0, s8, s5.length, first4of6.length);
            System.out.println(byteToHexStr(s8));

            //7 - First four bytes of 6
            String addr = Base58.encode(s8);
            System.out.println(addr);
        } catch(Exception ex) {
            System.out.println(ex.toString());
            ex.printStackTrace();
        }
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
