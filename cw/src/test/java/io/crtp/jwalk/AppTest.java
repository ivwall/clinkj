package io.crtp.jwalk;

//https://www.javaguides.net/2018/07/junit-getting-started-guide.html

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import io.crtp.jwalk.App;
import io.crtp.jwalk.BitcoinRPCs;

public class AppTest extends TestCase {

    /**
     * Create the test case
     * @param testName name of the test case
     */
    public AppTest( String testName ) {
        super( testName );
        System.out.println("AppTest ");
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        System.out.println("AppTest.Test ");
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp() {
        System.out.println("AppTest.testApp");
        //App app = App();
        BitcoinRPCs btcRBCs = new BitcoinRPCs();
        assertTrue( true );
    }
}