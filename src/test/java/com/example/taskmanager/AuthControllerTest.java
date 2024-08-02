package com.example.taskmanager;

import com.github.database.rider.cdi.api.DBRider;
import com.github.database.rider.core.api.dataset.DataSet;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.notNullValue;

@QuarkusTest
@DBRider
public class AuthControllerTest {

    @Test

    public void GivenValidCredentialsWhenLoginThenExpectToken() {
        RestAssured.given()
                .contentType("application/json")
                .body("""
                        {
                        "username" : "testuser",
                        "password" : "testpassword"
                        }
                        """)
                .when().post("/auth/login")
                .then()
                .statusCode(200)
                .body("token", notNullValue());
    }
}
