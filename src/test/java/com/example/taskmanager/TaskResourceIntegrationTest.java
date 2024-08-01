package com.example.taskmanager;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.notNullValue;

@QuarkusTest
public class TaskResourceIntegrationTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskResourceIntegrationTest.class);

    @BeforeEach
    @Transactional
    public void setUp() {
        // Clean up database before each test
        LOGGER.info("Cleaning up the database...");
        RestAssured.given()
                .when().delete("/tasks")
                .then().statusCode(204);
    }

    @Test
    public void testCreateTask() {
        Task task = new Task();
        task.setTitle("New Task");
        task.setDescription("Task description");
        task.setDueDate(LocalDate.now().plusDays(1));

        LOGGER.info("Creating a new task: {}", task);

        given()
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

    @Test
    public void testGetAllTasks() {
        // Create a task first
        Task task = new Task();
        task.setTitle("New Task");
        task.setDescription("Task description");
        task.setDueDate(LocalDate.now().plusDays(1));

        given()
                .contentType("application/json")
                .body(task)
                .when().post("/tasks")
                .then()
                .statusCode(201);

        LOGGER.info("Fetching all tasks...");

        // Get all tasks
        given()
                .when().get("/tasks")
                .then()
                .statusCode(200)
                .body("size()", is(1))
                .body("[0].title", is("New Task"))
                .body("[0].description", is("Task description"))
                .body("[0].dueDate", is(LocalDate.now().plusDays(1).toString()));
    }

    @Test
    public void testGetTaskById() {
        // Create a task first
        Task task = new Task();
        task.setTitle("New Task");
        task.setDescription("Task description");
        task.setDueDate(LocalDate.now().plusDays(1));

        String id = given()
                .contentType("application/json")
                .body(task)
                .when().post("/tasks")
                .then()
                .statusCode(201)
                .extract().path("id").toString();

        LOGGER.info("Fetching task by ID: {}", id);

        // Get task by id
        given()
                .when().get("/tasks/" + id)
                .then()
                .statusCode(200)
                .body("title", is("New Task"))
                .body("description", is("Task description"))
                .body("dueDate", is(LocalDate.now().plusDays(1).toString()));
    }

    @Test
    public void testUpdateTask() {
        // Create a task first
        Task task = new Task();
        task.setTitle("New Task");
        task.setDescription("Task description");
        task.setDueDate(LocalDate.now().plusDays(1));

        String id = given()
                .contentType("application/json")
                .body(task)
                .when().post("/tasks")
                .then()
                .statusCode(201)
                .extract().path("id").toString();

        // Update the task
        Task updatedTask = new Task();
        updatedTask.setTitle("Updated Task");
        updatedTask.setDescription("Updated description");
        updatedTask.setDueDate(LocalDate.now().plusDays(2));

        LOGGER.info("Updating task ID: {} with {}", id, updatedTask);

        given()
                .contentType("application/json")
                .body(updatedTask)
                .when().put("/tasks/" + id)
                .then()
                .statusCode(200)
                .body("title", is("Updated Task"))
                .body("description", is("Updated description"))
                .body("dueDate", is(LocalDate.now().plusDays(2).toString()));
    }

    @Test
    public void testDeleteTask() {
        // Create a task first
        Task task = new Task();
        task.setTitle("New Task");
        task.setDescription("Task description");
        task.setDueDate(LocalDate.now().plusDays(1));

        String id = given()
                .contentType("application/json")
                .body(task)
                .when().post("/tasks")
                .then()
                .statusCode(201)
                .extract().path("id").toString();

        LOGGER.info("Deleting task ID: {}", id);

        // Delete the task
        given()
                .when().delete("/tasks/" + id)
                .then()
                .statusCode(204);

        // Verify the task is deleted
        given()
                .when().get("/tasks/" + id)
                .then()
                .statusCode(404);
    }
}
