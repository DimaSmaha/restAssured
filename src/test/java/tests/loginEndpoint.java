package tests;

import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.junit.jupiter.api.Test;

import java.io.File;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.*;

public class loginEndpoint {
    // use https only because u will get redirect at CUD methods
    String baseUri = "https://reqres.in";
    String basePath = "/api/login";

    @Test
    public void loginSuccessful(){
        // use json to java string online
        // for bodys use chain with .when()
        String requestBody = "{\"email\":\"eve.holt@reqres.in\",\"password\":\"cityslicka\"}";
        //.contentType(ContentType.JSON)

        given()
                .baseUri(baseUri)
                .basePath(basePath)
                .header("Content-type", "application/json")
                .body(requestBody)
                .when()
                .post(baseUri+basePath)
                .then()
                .statusCode(200)
                .body("token",equalTo("QpwL5tke4Pnpja7X4"))
                .body("$", hasKey("token"))
                .log().all();
    }

    @Test
    public void loginUnuccessful(){
        // use json to java string online
        // for bodys use chain with .when()
        String requestBody = "{\"email\":\"peter@klaven\"}";
        //.contentType(ContentType.JSON)

        given()
                .baseUri(baseUri)
                .basePath(basePath)
                .header("Content-type", "application/json")
                .body(requestBody)
                .when()
                .post(baseUri+basePath)
                .then()
                .statusCode(400)
                .body("error",equalTo("Missing password"))
                .body("$", hasKey("error"))
                .log().all();
    }

}
