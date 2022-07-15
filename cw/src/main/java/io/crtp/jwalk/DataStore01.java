package io.crtp.jwalk;

import java.io.File;  // Import the File class
import java.io.FileWriter;   // Import the FileWriter class
import java.io.IOException;  // Import the IOException class to handle errors

public class DataStore01 implements IDataStore {

    FileWriter myWriter = null;

    public DataStore01() {
        try {
            myWriter = new FileWriter("filename.txt");
        }catch(Exception ex){
            System.out.println(ex.toString());
        }
    }
 
    public void putAddrData(String addr, String blockNum) {
        try {
            myWriter.write(addr+"  "+blockNum+System.getProperty("line.separator"));
        }catch(Exception ex){
            System.out.println(ex.toString());
        }
        System.out.println(" addr "+addr+"  blk ");
    }

    public void close() {
        try {
            myWriter.close();
        }catch(Exception ex){
            System.out.println(ex.toString());
        }
    }

    
}
