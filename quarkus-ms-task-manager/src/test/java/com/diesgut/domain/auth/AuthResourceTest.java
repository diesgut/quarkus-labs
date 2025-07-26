package com.diesgut.domain.auth;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import static org.hamcrest.Matchers.emptyString;
import org.junit.jupiter.api.Test;


@QuarkusTest
class AuthResourceTest {

    @Test
    void login() {
        RestAssured.given()
                .body("{\"name\":\"admin\",\"password\":\"admin\"}")
                .contentType(ContentType.JSON)
                .when().post("/api/v1/auth/login")
                .then()
                .statusCode(200)
                .body(Matchers.not(emptyString()));
    }

    @Test
    void loginInvalidCredentials() {
        RestAssured.given()
                .body("{\"name\":\"admin\",\"password\":\"1234\"}")
                .contentType(ContentType.JSON)
                .when().post("/api/v1/auth/login")
                .then()
                .statusCode(401);
    }
}