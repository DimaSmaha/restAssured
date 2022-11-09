package tests;

import io.restassured.module.jsv.JsonSchemaValidator;
import org.junit.jupiter.api.Test;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasKey;

public class registerEndpoint {
    String baseUri = "https://reqres.in";
    String basePath = "/api/register";

    @Test
    public void successRegister(){
        // use json to java string online
        // for bodys use chain with .when()
        String requestBody = "{\"email\":\"eve.holt@reqres.in\",\"password\":\"pistol\"}";

        given()
                .baseUri(baseUri)
                .basePath(basePath)
                .header("Content-type", "application/json")
                .body(requestBody)
        .when()
                .post(baseUri+basePath)
        .then()
                .statusCode(200)
                .body("$", hasKey("id"))
                .body("$", hasKey("token"))
                .body(JsonSchemaValidator.matchesJsonSchema(new File("src/schemas/successRegisterSchema.json")))
                .log().all();
    }

    @Test
    public void unsuccessfulRegister(){
        // use json to java string online
        // for body's use chain with .when()
        String requestBody = "{\"email\":\"sydney@fife\"}";

        given()
                .baseUri(baseUri)
                .basePath(basePath)
                .header("Content-type", "application/json")
                .body(requestBody)
        .when()
                .post(baseUri+basePath)
        .then()
                .statusCode(400)
                .body("$", hasKey("error"))
                .body("error",equalTo("Missing password"))
                .log().all();
    }
}
