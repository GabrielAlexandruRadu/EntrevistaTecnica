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

import static org.hamcrest.Matchers.equalTo;

@QuarkusTest
@DBRider
@ExtendWith(DBUnitExtension.class)
public class PointsServiceTest {

    @Test
    @DataSet("datasets/initial-user.yml")
    @ExpectedDataSet("datasets/expected-points-history.yml")
    public void GivenTransactionWhenProcessedThenPointsUpdatedAndLogged() {
        RestAssured.given()
                .contentType("application/json")
                .body("""
                        {
                        "id":1,
                        "userId":1,
                        "quantity": 100,
                        "currency": "EUR",
                        "location": "Test Location",
                        "merchantCategoryCode": "12 Test Code"
                        }
                        """)
                .when().put("/transaction")
                .then().statusCode(201);

        // Check the user's points
        RestAssured.given()
                .when().get("/user/1")
                .then()
                .statusCode(200)
                .body("points", equalTo(200)); // Assuming initial points are 100 and transaction adds 100 points

        // Check points history entry
        RestAssured.given()
                .when().get("/points-history")
                .then()
                .statusCode(200)
                .body("[0].user.id", equalTo(1))
                .body("[0].points", equalTo(100)) // Assuming the transaction adds 100 points
                .body("[0].newTotal", equalTo(200)) // New total after adding 100 points
                .body("[0].transactionId", equalTo(1));
    }
}
