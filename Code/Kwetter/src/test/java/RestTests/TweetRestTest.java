/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RestTests;

import com.jayway.restassured.RestAssured;
import static com.jayway.restassured.RestAssured.given;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Yannick van Leeuwen
 */
public class TweetRestTest {
    
//        public static void setup() {
//        String port = System.getProperty("server.port");
//        if (port == null) {
//            RestAssured.port = Integer.valueOf(8080);
//        }
//        else{
//            RestAssured.port = Integer.valueOf(port);
//        }
//
//        String basePath = System.getProperty("server.base");
//        if(basePath==null){
//            basePath = "/Kwetter/resources/";
//        }
//        RestAssured.basePath = basePath;
//
//        String baseHost = System.getProperty("server.host");
//        if(baseHost==null){
//            baseHost = "http://localhost";
//        }
//        RestAssured.baseURI = baseHost;
//    }
//    
//    @Test
//    public void basicPingTest() {
//        given().when().get("tweets").then().statusCode(200);
//    }
}
