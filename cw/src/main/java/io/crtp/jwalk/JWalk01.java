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

// -----------------------
// call python work
import org.python.util.PythonInterpreter;
import org.python.core.*;


// ------------------------
import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import javax.xml.bind.DatatypeConverter;

//-----------------------------------
import org.bitcoinj.core.TransactionOutput;
//ByteUtils.HEX.decode
//import org.bitcoinj.base.utils.ByteUtils;
//import org.bitcoinj.base.utils.ByteUtils;
import org.bitcoinj.core.Utils;
//import org.bitcoinj.base.Utils
import org.spongycastle.crypto.digests.RIPEMD160Digest;
import org.bitcoinj.params.MainNetParams;
import org.bitcoinj.core.*;
import org.bitcoinj.core.Address;

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
        System.out.println("");
        cypherDev(javaBlock);
        System.out.println("");
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
            System.out.println("  sha: " + toHexString(s1).toUpperCase());
            //# prints
            System.out.println("  sha: 7524DC35AEB4B62A0F1C90425ADC6732A7C5DF51A72E8B90983629A7AEC656A0");

            MessageDigest rmd = MessageDigest.getInstance("RipeMD160", "BC");
            byte[] r1 = rmd.digest(s1);

            byte[] r2 = new byte[r1.length + 1];
            r2[0] = 0;
            for (int i = 0 ; i < r1.length ; i++) r2[i+1] = r1[i];
            System.out.println("  rmd: " + toHexString(r2).toUpperCase());            
            System.out.println("  rmd: 00C5FAE41AB21FA56CFBAFA3AE7FB5784441D11CEC");


            byte[] s2 = sha.digest(r2);
            System.out.println("  sha: " + toHexString(s2).toUpperCase());
            byte[] s3 = sha.digest(s2);
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

    public void lmb( String e ){
        try {

            String a = e;
            System.out.println("learn me btc: https://learnmeabitcoin.com/technical/hash-function");
            String[] strArray = a.split(" ");
            System.out.println(strArray[0]);
            String ECDA = strArray[0];
            System.out.println(ECDA.length());
            byte[] pkArray = ECDA.getBytes();
            System.out.println("pubkey as bytes: "+Arrays.toString(pkArray));
            pkArray = ECDA.getBytes(StandardCharsets.UTF_8);
            System.out.println("pubkey as bytes: "+Arrays.toString(pkArray));
            System.out.println(bytesToHex2(pkArray));

            System.out.println(pkArray.length);
            Sha256Hash firstHash = Sha256Hash.of(ECDA.getBytes());
            System.out.println("  btcj "+ firstHash.toString());
            MessageDigest sha = MessageDigest.getInstance("SHA-256");
            byte[] s1 = sha.digest(ECDA.getBytes("UTF-8"));
            System.out.println("  sha: " + toHexString(s1).toUpperCase());

            MessageDigest rmd = MessageDigest.getInstance("RipeMD160", "BC");
            byte[] r1 = rmd.digest(s1);

            byte[] r2 = new byte[r1.length + 1];
            r2[0] = 0;
            for (int i = 0 ; i < r1.length ; i++) r2[i+1] = r1[i];
            System.out.println("  rmd: " + toHexString(r2).toUpperCase());
            
            byte[] iti = hexStringToByteArray("ab");
            System.out.println(new String(iti));

            Sha256Hash itiHash = Sha256Hash.of(iti);
            System.out.println("  iti  "+ itiHash.toString());

            String b1 = "04678AFDB0FE5548271967F1A67130B7105CD6A828E03909A67962E0EA1F61DEB649F6BC3F4CEF38C4F35504E51EC112DE5C384DF7BA0B8D578A4C702B6BF11D5F";
            System.out.println(b1);
            iti = hexStringToByteArray(b1);
            itiHash = Sha256Hash.of(iti);
            System.out.println("  iti  "+ itiHash.toString());

        } catch(Exception ex) {
            System.out.println(ex.toString());
            ex.printStackTrace();
        }
    }

    /* s must be an even-length string. */
    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                                + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }    

    public void addressFromPubKey( String e ){
        try {

            String a = e;
            System.out.println("addressFromPubKey");
            String[] strArray = a.split(" ");
            System.out.println(strArray[0]);
            String ECDA = strArray[0];
            System.out.println(ECDA.length());
            byte[] pkArray = ECDA.getBytes();
            System.out.println("pubkey as bytes: "+Arrays.toString(pkArray));

            System.out.println(pkArray.length);
            Sha256Hash firstHash = Sha256Hash.of(ECDA.getBytes());
            System.out.println("  btcj "+ firstHash.toString());
            MessageDigest sha = MessageDigest.getInstance("SHA-256");
            byte[] s1 = sha.digest(ECDA.getBytes("UTF-8"));
            System.out.println("  sha: " + toHexString(s1).toUpperCase());

            MessageDigest rmd = MessageDigest.getInstance("RipeMD160", "BC");
            byte[] r1 = rmd.digest(s1);

            byte[] r2 = new byte[r1.length + 1];
            r2[0] = 0;
            for (int i = 0 ; i < r1.length ; i++) r2[i+1] = r1[i];
            System.out.println("  rmd: " + toHexString(r2).toUpperCase());            

            byte[] s2 = sha.digest(r2);
            System.out.println("  sha: " + toHexString(s2).toUpperCase());
            byte[] s3 = sha.digest(s2);
            System.out.println("  sha: " + toHexString(s3).toUpperCase());  
                        
            byte[] a1 = new byte[25];
            for (int i = 0 ; i < r2.length ; i++) a1[i] = r2[i];
            for (int i = 0 ; i < 5 ; i++) a1[20 + i] = s3[i];
            
            System.out.println("  adr: " + Base58.encode(a1));

            //byte array to string
            //byte[] bytes = "hello world".getBytes();
            byte[] bytes = "04CAAA5C0BDDAA22C9D3C0DDAEC8550791891BB2C2FB0F9084D02F927537DE4F443ACED7DEB488E9BFE60D6C68596E6C78D95E20622CC05474FD962392BDC6AF29".getBytes();
            //Convert byte[] to String
            String s = Base64.getEncoder().encodeToString(bytes);             
            System.out.println(s);

            //System.out.println("  sha: "+encryptionIterator("04CAAA5C0BDDAA22C9D3C0DDAEC8550791891BB2C2FB0F9084D02F927537DE4F443ACED7DEB488E9BFE60D6C68596E6C78D95E20622CC05474FD962392BDC6AF29"));
            System.out.println("  sha: "+encryptionIterator("thisisatest"));

        } catch(Exception ex) {
            System.out.println(ex.toString());
            ex.printStackTrace();
        }
    }
    private static MessageDigest sha256;
    private static String encryptionIterator(String content) {
        try {
            sha256 = MessageDigest.getInstance("SHA-256");
            byte[] passBytes = (content).getBytes();
            sha256.reset();
            return bytesToHex2(sha256.digest(passBytes));
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        return "";
    }

    private static String bytesToHex2(byte[] bytes) {
        System.out.println(" bytesToHex2");
        //String _LOC = "[SB_UtilityBean: bytesToHex]";
    
        StringBuffer result = new StringBuffer();
        for (byte byt : bytes) result.append(Integer.toString((byt & 0xff) + 0x100, 16).substring(1));
    
        return result.toString();
    }

    static byte[] hash160(byte[] in) {
        MessageDigest d1;
        try {
            d1 = MessageDigest.getInstance("SHA-256");
        } catch(NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        d1.update(in);
        byte[] digest = d1.digest();
        RIPEMD160Digest d2 = new RIPEMD160Digest();
        d2.update(digest, 0, 32);
        byte[] ret = new byte[20];
        d2.doFinal(ret, 0);
        return ret;
    }    
    final protected static char[] hexArray = "0123456789abcdef".toCharArray();
    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
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

    public String pythonCall01 (String pubKey) {
        // https://www.tutorialspoint.com/jython/jython_java_application.htm
        // https://mr-dai.github.io/embedding-jython/
        System.out.println("pythonCall01 "+pubKey);
        try {
            PythonInterpreter interp = new PythonInterpreter();
            System.out.println("What's up Java Doc?");
            interp.execfile("pj.py");
            interp.set("a", new PyInteger(42));
            interp.exec("print a");
            interp.exec("x = 2+2");
            PyObject x = interp.get("x");
            System.out.println("x: "+x);
            System.out.println("Goodbye "); 
            interp.close();       
        } catch(Exception ex){
            System.out.println(ex.toString());
        }
        return "tbd";
    }

    public String pythonCall02 (String pubKey) {
        // https://www.tutorialspoint.com/jython/jython_java_application.htm
        // https://mr-dai.github.io/embedding-jython/
        System.out.println("pythonCall02 "+pubKey);
        try {
            PythonInterpreter interp = new PythonInterpreter();
            System.out.println("What's up Java Doc?");
            PyFunction pf = (PyFunction)interp.get("getAddr");
            System.out.println(pf.__call__(new PyString("silly wabbit")));
            interp.close();       
        } catch(Exception ex){
            System.out.println(ex.toString());
        }
        return "tbd";
    }

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    public static byte[] getSHA(String input) throws NoSuchAlgorithmException {
        // Static getInstance method is called with hashing SHA
        MessageDigest md = MessageDigest.getInstance("SHA-256");

        // digest() method called
        // to calculate message digest of an input
        // and return array of byte
        return md.digest(input.getBytes(StandardCharsets.UTF_8));
    }

    public static String toHexString2(byte[] hash) {
        // Convert byte array into signum representation
        BigInteger number = new BigInteger(1, hash);

        // Convert message digest into hex value
        StringBuilder hexString = new StringBuilder(number.toString(16));

        // Pad with leading zeros
        while (hexString.length() < 32) {
            hexString.insert(0, '0');
        }

        return hexString.toString();
    }


}
