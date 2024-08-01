package com.example.taskmanager;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.notNullValue;

@QuarkusTest
public class TaskResourceTest {

    @Test
    @Transactional
    public void testCreateTask() {
        Task task = new Task();
        task.setTitle("New Task");
        task.setDescription("Task description");
        task.setDueDate(LocalDate.now().plusDays(1));

        RestAssured.given()
                .contentType("application/json")
                .body(task)
                .when().post("/tasks")
                .then()
                .statusCode(201)
                .body("id", notNullValue())
                .body("title", is("New Task"))
                .body("description", is("Task description"))
                .body("dueDate", is(LocalDate.now().plusDays(1).toString()));
    }
}
