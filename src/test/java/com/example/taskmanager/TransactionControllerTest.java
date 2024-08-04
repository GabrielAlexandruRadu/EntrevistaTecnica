package com.example.taskmanager;

import com.example.core.entity.TransactionEntity;
import com.github.database.rider.cdi.api.DBRider;
import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.junit5.DBUnitExtension;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;


@QuarkusTest
@DBRider
@ExtendWith(DBUnitExtension.class)
class TransactionControllerTest {

    @Test
    @DataSet("datasets/expected-transactions.yml")
    public void GiveTransactionsWhenQuantitySumThenExpectTotalSum () {
        RestAssured.defaultParser = Parser.JSON;

        RestAssured.given()
                .when().get("/transaction/TotalTransactionsValue")
                .then()
                .statusCode(200)
                .body("totalBalance", equalTo(150))
                .body("transactions[0].id", equalTo(1))
                .body("transactions[0].location", equalTo("New York"))
                .body("transactions[0].quantity", equalTo(50))
                .body("transactions[0].currency", equalTo("USD"))
                .body("transactions[0].merchantCategoryCode", equalTo("1234"))
                .body("transactions[1].id", equalTo(2))
                .body("transactions[1].location", equalTo("Los Angeles"))
                .body("transactions[1].quantity", equalTo(100))
                .body("transactions[1].currency", equalTo("USD"))
                .body("transactions[1].merchantCategoryCode", equalTo("5678"));

    }



}

