package com.example.taskmanager;

import io.smallrye.common.annotation.Blocking;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;


@Blocking
@Path("/tasks")
public class TaskResource {

    @Inject
    TaskService taskService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> createTask(Task task) {
        return taskService.createTask(task)
                .replaceWith(Response.status(Response.Status.CREATED).entity(task).build());
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<List<Task>> getAllTasks() {
        return taskService.getAllTasks();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> getTaskById(@PathParam("id") Long id) {
        return taskService.getTaskById(id)
                .onItem().transform(task -> Response.ok(task).build())
                .onFailure().recoverWithItem(Response.status(Response.Status.NOT_FOUND).entity("Task not found").build());
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> updateTask(@PathParam("id") Long id, Task updatedTask) {
        return taskService.updateTask(id, updatedTask)
                .onItem().transform(task -> Response.ok(task).build())
                .onFailure().recoverWithItem(Response.status(Response.Status.NOT_FOUND).entity("Task not found").build());
    }

    @DELETE
    @Path("/{id}")
    public Uni<Response> deleteTask(@PathParam("id") Long id) {
        return taskService.deleteTask(id)
                .replaceWith(Response.noContent().build())
                .onFailure().recoverWithItem(Response.status(Response.Status.NOT_FOUND).entity("Task not found").build());
    }
}
