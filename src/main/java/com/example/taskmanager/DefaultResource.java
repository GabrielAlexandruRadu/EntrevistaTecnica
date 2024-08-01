package com.example.taskmanager;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/")
public class DefaultResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response defaultEndpoint() {
        return Response.ok("Welcome to the Task Manager API. Use /tasks to manage your tasks.").build();
    }
}
