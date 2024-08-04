package com.example.taskmanager;

import com.github.database.rider.cdi.api.DBRider;
import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.junit5.DBUnitExtension;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.hamcrest.Matchers.notNullValue;

@QuarkusTest
@DBRider
@ExtendWith(DBUnitExtension.class)
public class AuthControllerTest {

    @Test
    @DataSet("datasets/initial-user.yml")
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
