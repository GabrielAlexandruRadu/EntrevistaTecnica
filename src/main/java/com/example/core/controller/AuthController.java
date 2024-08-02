package com.example.core.controller;


import com.example.core.DTO.LoginRequest;
import com.example.core.DTO.LoginResponse;
import com.example.core.service.AuthService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

public class AuthController {
    @Inject
    AuthService authService;

    @POST
    @Path("/login")
    public Response login(LoginRequest request) {
        String token = authService.authenticate(request);
        return Response.ok(new LoginResponse(token)).build();
    }

}
