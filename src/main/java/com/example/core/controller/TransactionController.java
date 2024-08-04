package com.example.core.controller;

import com.example.core.DTO.TotalTransactionResponse;
import com.example.core.domain.Transaction;
import com.example.core.service.PointsService;
import com.example.core.service.TransactionService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Optional;


@Path("/transaction")
public class TransactionController {

    @Inject
    TransactionService transactionService;

    @Inject
    PointsService pointsService;

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createTransaction (Transaction transaction) {
        transactionService.save(transaction);
        pointsService.processTransaction(transaction);
        return Response.status(Response.Status.CREATED).entity(transaction).build();
    }

    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getTransaction(@PathParam("id") Long id) {
        Optional<Transaction> transaction = transactionService.findById(id);
        return transaction
                .map(t -> Response.ok(t).build())
                .orElseGet(() -> Response.status(Response.Status.NOT_FOUND).build());


    }

    @GET
    @Path("/TotalTransactionsValue")
    @Produces({MediaType.APPLICATION_JSON})

    public Response getTotalTransaction(){
        Optional<TotalTransactionResponse> response = transactionService.getTotalTransactionResponse();
        return  response
                .map(t -> Response.ok(t).build())
                .orElseGet(()-> Response.status(Response.Status.NOT_FOUND).build());
    }
}
