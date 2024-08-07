package com.example.controller;


import com.example.model.DTO.TransactionResponse;
import com.example.model.entity.TransactionEntity;
import com.example.service.TransactionService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import java.util.Optional;

@Path("/transaction")
public class TransactionController {

    @Inject
    TransactionService transactionService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createTransaction (TransactionEntity transaction) {
        transactionService.save(transaction);
        return Response.status(Response.Status.CREATED).entity(transaction).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTransaction(@PathParam("id") Long id) {
        Optional<TransactionEntity> transaction = transactionService.findById(id);

        return transaction
                .map(entity -> Response.ok(new TransactionResponse(entity)).build())
                .orElseGet(() -> Response.status(Response.Status.NOT_FOUND).build());
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Optional<TransactionEntity> updateTransaction(TransactionEntity transaction) {
        return transactionService.updateTransaction(transaction);

    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteTransaction(@PathParam("id")  Long id) {
        try{
            transactionService.delete(id);
            return Response.noContent().build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/getAllTransactions")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllTransactions(@QueryParam("page") @DefaultValue("0") int page,
                                       @QueryParam("size") @DefaultValue("10") int size) {
        return Response.ok(transactionService.findAll(page, size)).build();
    }
}
