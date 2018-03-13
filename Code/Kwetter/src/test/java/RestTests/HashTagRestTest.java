/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RestTests;

import com.jayway.restassured.RestAssured;
import org.junit.BeforeClass;
import org.junit.Test;
import static com.jayway.restassured.RestAssured.given;
import java.util.HashMap;
import java.util.Map;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

/**
 *
 * @author Yannick
 */
public class HashTagRestTest {
    
//    @BeforeClass
//    public static void setup() {
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
//        given().when().get("hashtags").then().statusCode(200);
//    }
//    
//    @Test
//    public void invalidHashTagId() {
//        given().when().get("/hashtags/4")
//            .then().statusCode(500);
//    }
//    
//    @Test
//    public void verifyContentOfTag() {
//        given().when().get("/hashtags").then()
//            .body(containsString("#test"));
//    }
//    
//    @Test
//    public void verifyContentOfTagOne() {
//        given().when().get("/hashtags/1").then()
//            .body("content", equalTo("#test"));
//    }
//    
//    @Test
//    public void verifyTopLevelURL() {
//        given().when().get("/hashtags/1").then()
//        .body("content",equalTo("#test"))
//        .body("id",equalTo(1))
//        .statusCode(200);
//    }
//    
//    @Test
//    public void addHashTag() {
//        Map<String,String> hashtag = new HashMap<>();
//        hashtag.put("content", "#testContentje");
//
//        given()
//        .contentType("application/json")
//        .body(hashtag)
//        .when().post("/hashtags/add").then()
//        .statusCode(200);
//    }
//    
//    @Test
//    public void editHashTag(){
//        Map<String,String> hashtag = new HashMap<>();
//        hashtag.put("id", "1");
//        hashtag.put("content", "#test");
//
//        given()
//        .contentType("application/json")
//        .body(hashtag)
//        .when().put("/hashtags/edit").then()
//        .statusCode(200);
//    }
}
