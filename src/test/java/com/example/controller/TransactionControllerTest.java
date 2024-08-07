package com.example.controller;

import com.github.database.rider.cdi.api.DBRider;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.junit5.DBUnitExtension;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.stream.IntStream;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.hasSize;

@QuarkusTest
@DBRider
@ExtendWith(DBUnitExtension.class)
public class TransactionControllerTest {
    static {
        RestAssured.baseURI  = "/transaction";
        RestAssured.defaultParser = Parser.JSON;
    }

    @Test
    @ExpectedDataSet("datasets/expected-transaction.yml")
    public void GivenTransactionWhenCreatingTransactionThenCreatedTransaction (){

        RestAssured.given()
                .contentType("application/json")
                .body("""
                        {
                        "location" : "New York",
                        "currency" : "USD",
                        "amount" : 100,
                        "merchant_category_code" : "123code"
                        }
                        """)
                .when().post("/transaction")
                .then()
                .statusCode(201)
                .body("id", notNullValue())
                .body("location" , is("New York"))
                .body("currency", is("USD"))
                .body("amount", is(100))
                .body("merchant_category_code", is("123code"));

        RestAssured.given()
                .when().get("transaction/1" )
                .then()
                .statusCode(200)
                .body("id", notNullValue())
                .body("location", is("New York"))
                .body("currency", is("USD"))
                .body("amount", is(100))
                .body("merchantCategoryCode", is("123code"))
                .body("saludo", is("hola"));

    }

    @Test
    @ExpectedDataSet("datasets/expected-updated-transaction.yml")
    public void GivenTransactionWhenUpdatingTransactionThenUpdatedTransaction() {

        Integer transactionID = RestAssured.given()
                .contentType("application/json")
                .body("""
                        {
                        "location" : "New York",
                        "currency" : "USD",
                        "amount" : 100,
                        "merchant_category_code" : "123code"
                        }
                        """)
                .when().post("/transaction")
                .then()
                .statusCode(201)
                .extract().path("id");

        // update the transaction
        given()
                .contentType(ContentType.JSON)
                .body("""
                        {
                        "id" : %d,
                        "location": "Los Angeles",
                        "currency": "EUR",
                        "amount": 200,
                        "merchant_category_code": "456code"
                        }
                        """.formatted(transactionID))
                .when().put("/transaction")
                .then()
                .statusCode(200)
                .body("id", notNullValue())
                .body("location", is("Los Angeles"))
                .body("currency", is("EUR"))
                .body("amount", is(200))
                .body("merchant_category_code", is("456code"));


    }

    @Test
    public void GivenTransactionWhenDeletingTransactionThenNoContent() {

        // do the transaction
        RestAssured.given()
                .contentType("application/json")
                .body("""
                        {
                        "location" : "New York",
                        "currency" : "USD",
                        "amount" : 100,
                        "merchant_category_code" : "123code"
                        }
                        """)
                .when().post("/transaction")
                .then()
                .statusCode(201);

        //delete the transaction
        given()
                .when().delete("transaction/1")
                .then()
                .statusCode(204);

        // verify there is no transaction
        given()
                .when().get("transaction/1")
                .then()
                .statusCode(404);
    }

    @Test
    public void givenDeletedTransactionWhenFindingTransactionThenReturnNotFound () {

        RestAssured.given()
                .when().delete("transaction/12312")
                .then()
                .statusCode(404);
    }

    @Test
    public void givenInvalidTransactionIdWhenFindingTransactionThenReturnNotFound () {


        RestAssured.given()
                .get("transaction/22222")
                .then()
                .statusCode(404);
    }

    @Test
    public void givenTransactionsWhenGettingAllWithPaginationThenReturnPagedResults() {

        IntStream.range(0, 15).forEach(i -> {
            given()
                    .contentType(ContentType.JSON)
                    .body(String.format("""
                        {
                        "location": "Location %d",
                        "currency": "USD",
                        "amount": %d,
                        "merchant_category_code": "code%d"
                        }
                        """, i, i * 100, i))
                    .when().post("/transaction")
                    .then()
                    .statusCode(201);
        });

        // Retrieve the first page with 10 elements
        // Retrieve the first page with 10 elements
        given()
                .queryParam("page", 0)
                .queryParam("size", 10)
                .when().get("/transaction/getAllTransactions")
                .then()
                .statusCode(200)
                .body("$", hasSize(10))
                .body("[0].id", notNullValue())
                .body("[0].location", is("Location 0"))
                .body("[9].location", is("Location 9"));

        // Retrieve the second page with 5 elements remaining
        given()
                .queryParam("page", 1)
                .queryParam("size", 10)
                .when().get("/transaction/getAllTransactions")
                .then()
                .statusCode(200)
                .body("$", hasSize(5))
                .body("[0].id", notNullValue())
                .body("[0].location", is("Location 10"))
                .body("[4].location", is("Location 14"));
    }


}
