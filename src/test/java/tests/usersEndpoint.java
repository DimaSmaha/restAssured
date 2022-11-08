package tests;

import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.junit.jupiter.api.Test;

import java.io.File;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.*;

public class usersEndpoint {
// use https only because u will get redirect at CUD methods
String baseUri = "https://reqres.in";
String basePath = "/api/users";

    @Test
    public void getListOfUsers(){
        given()
                .baseUri(baseUri)
                .basePath(basePath+"?page=2")
        .when()
                .get(baseUri+basePath+"?page=2")
        .then()
                .statusCode(200)
                .body("per_page",equalTo(6))
                .log().all();
    }

    @Test
    public void getSingleUser(){
        given()
                .baseUri(baseUri)
                .basePath(basePath+"/2")
        .when()
                .get(baseUri+basePath+"/2")
        .then()
                .statusCode(200)
                .body("data.first_name",equalTo("Janet"))
                .body(JsonSchemaValidator.matchesJsonSchema(new File("src/schemas/singleUserSchema.json")))
                .log().all();
    }

    @Test
    public void getSingleUserNotFound(){
        given()
                .baseUri(baseUri)
                .basePath(basePath+"/23")
        .when()
                .get(baseUri+basePath+"/23")
        .then()
                .statusCode(404)
                .body(equalTo("{}"))
                .log().all();
    }

    @Test
    public void createSingleUser(){
        // use json to java string online
        // for bodys use chain with .when()
        String requestBody = "{\"name\":\"Dima\",\"job\":\"QA\"}";
        //.contentType(ContentType.JSON)

        given()
                .baseUri(baseUri)
                .basePath(basePath)
                .header("Content-type", "application/json")
                .body(requestBody)
        .when()
                .post(baseUri+basePath)
        .then()
                .statusCode(201)
                .body("name",equalTo("Dima"))
                .body("$", hasKey("id"))
                .body("$", hasKey("createdAt"))
                .log().all();
    }

    @Test
    public void updateSingleUser(){
        // use json to java string online
        // for bodys use chain with .when()

        String requestBody = "{\"name\":\"Dima\",\"job\":\"QA\"}";

        given()
                .baseUri(baseUri)
                .basePath(basePath+"/2")
                .header("Content-type", "application/json")
                .body(requestBody)
        .when()
                .put(baseUri+basePath+"/2")
        .then()
                .statusCode(200)
                .body("name",equalTo("Dima"))
                .body("$", hasKey("updatedAt"))
                .log().all();
    }

    @Test
    public void updateSingleUserByPatch(){
        // use json to java string online
        // for bodys use chain with .when()

        String requestBody = "{\"name\":\"Dima\",\"job\":\"QA\"}";

        given()
                .baseUri(baseUri)
                .basePath(basePath+"/2")
                .header("Content-type", "application/json")
                .body(requestBody)
                .when()
                .patch(baseUri+basePath+"/2")
                .then()
                .statusCode(200)
                .body("name",equalTo("Dima"))
                .body("$", hasKey("updatedAt"))
                .log().all();
    }

    @Test
    public void deleteSingleUser(){
        given()
                .baseUri(baseUri)
                .basePath(basePath+"/2")
                .header("Content-type", "application/json")
        .when()
                .delete(basePath+"/2")
        .then()
                .statusCode(204)
                .log().all();
    }

}
