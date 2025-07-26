package com.diesgut.domain.user;

import com.diesgut.domain.auth.AuthDto;
import com.diesgut.domain.user.dto.PasswordChangeDto;
import com.diesgut.domain.user.dto.UserDto;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
class UserResourceTest {

    @Test
    @Order(1)
    @TestSecurity(user = "admin", roles = "admin")
    void all() {
        given()
                .when().get("/api/v1/users")
                .then()
                .statusCode(200)
                .body("$.size()", greaterThanOrEqualTo(1),
                        "[0].name", is("admin"),
                        "[0].password", nullValue());
    }

    @Test
    @Order(2)
    @TestSecurity(user = "admin", roles = "admin")
    void create() {
        given()
                .body("{\"name\":\"test\",\"password\":\"test\",\"roles\":[\"user\"]}")
                .contentType(ContentType.JSON)
                .when().post("/api/v1/users")
                .then()
                .statusCode(201)
                .body(
                        "name", Matchers.is("test"),
                        "password", nullValue(),
                        "created_at",Matchers. not(emptyString())
                );
    }

    @Test
    @Order(3)
    @TestSecurity(user = "admin", roles = "admin")
    void createDuplicate() {
        given()
                .body("{\"name\":\"user\",\"password\":\"test\",\"roles\":[\"user\"]}")
                .contentType(ContentType.JSON)
                .when().post("/api/v1/users")
                .then()
                .statusCode(409);
    }

    @Test
    @Order(4)
    @TestSecurity(user = "admin", roles = "admin")
    void get() {
        given()
                .when().get("/api/v1/users/1")
                .then()
                .statusCode(200)
                .body("name", is("admin"));
    }

    @Test
    @Order(5)
    @TestSecurity(user = "admin", roles = "admin")
    void getNotFound() {
        given()
                .when().get("/api/v1/users/1337")
                .then()
                .statusCode(404);
    }

    @Test
    @Order(6)
    @TestSecurity(user = "admin", roles = "admin")
    void update() {
        var user = given()
                .body("{\"name\":\"to-update\",\"password\":\"test\",\"roles\":[\"user\"]}")
                .contentType(ContentType.JSON)
                .when().post("/api/v1/users")
                .as(UserDto.class);
        user.setName("updated");

        given()
                .body(user)
                .contentType(ContentType.JSON)
                .when().put("/api/v1/users/" + user.getId())
                .then()
                .statusCode(200)
                .body(
                        "name", is("updated"),
                        "version", is(user.getVersion() + 1)
                );
    }

    @Test
    @Order(7)
    @TestSecurity(user = "admin", roles = "admin")
    void updateOptimisticLock() {
        given()
                .body("{\"name\":\"updated\",\"version\":1337}")
                .contentType(ContentType.JSON)
                .when().put("/api/v1/users/1")
                .then()
                .statusCode(409);
    }

    @Test
    @Order(8)
    @TestSecurity(user = "admin", roles = "admin")
    void updateNotFound() {
        given()
                .body("{\"name\":\"i-dont-exist\",\"password\":\"pa33\"}")
                .contentType(ContentType.JSON)
                .when().put("/api/v1/users/1337")
                .then()
                .statusCode(404);
    }

    @Test
    @Order(9)
    @TestSecurity(user = "admin", roles = "admin")
    void delete() {
        var toDelete = given()
                .body("{\"name\":\"to-delete\",\"password\":\"test\"}")
                .contentType(ContentType.JSON)
                .post("/api/v1/users")
                .as(UserDto.class);

        given()
                .when().delete("/api/v1/users/" + toDelete.getId())
                .then()
                .statusCode(204);

        given()
                .when().get("/api/v1/users/" + toDelete.getId())
                .then()
                .statusCode(404);

        //  assertThat(User.findById(toDelete.id).await().indefinitely(), nullValue());
    }

    @Test
    @Order(10)
    @TestSecurity(user = "admin", roles = "user")
    void getCurrentUser() {
        given()
                .when().get("/api/v1/users/self")
                .then()
                .statusCode(200)
                .body("name", is("admin"));
    }


    @Test
    @Order(11)
    @TestSecurity(user = "admin", roles = "user")
    void changePassword() {
        PasswordChangeDto passwordChangeDto = new PasswordChangeDto("admin", "changed");
        given()
                .body(passwordChangeDto)
                .contentType(ContentType.JSON)
                .when().put("/api/v1/users/self/password")
                .then()
                .statusCode(200);
     /*   assertTrue(BcryptUtil.matches("changed",
                UserEntity.<UserEntity>findById(1L).await().indefinitely().password)
        );*/

        AuthDto authDto = new AuthDto("admin", "changed");
        RestAssured.given()
                .body(authDto)
                .contentType(ContentType.JSON)
                .when().post("/api/v1/auth/login")
                .then()
                .statusCode(200)
                .body(Matchers.not(emptyString()));
    }


    @Order(12)
    @TestSecurity(user = "admin", roles = "user")
    void changePasswordDoesntMatch() {
        PasswordChangeDto passwordChangeDto = new PasswordChangeDto("wrong", "changed");
        given()
                .body(passwordChangeDto)
                .contentType(ContentType.JSON)
                .when().put("/api/v1/users/self/password")
                .then()
                .statusCode(409);
    }
}