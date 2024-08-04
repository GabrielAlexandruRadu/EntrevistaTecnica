package com.example.core.controller;


import com.example.core.repository.PointsHistoryRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/points-history")
public class PointsHistoryController {

    @Inject
    PointsHistoryRepository pointsHistoryRepository;

    @GET
    public Response getAllPointsHistory() {
        return Response.ok(pointsHistoryRepository.listAll()).build();
    }
}
