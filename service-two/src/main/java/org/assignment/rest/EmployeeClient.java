package org.assignment.rest;

import io.smallrye.mutiny.Uni;
import org.assignment.dto.EmployeeReq;
import org.assignment.dto.EmployeeRes;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/employees")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RegisterRestClient(configKey = "employee-api")
public interface EmployeeClient {

    @POST
    Uni<EmployeeRes> createEmployee(EmployeeReq employeeReq);

    @GET
    @Path("/{id}")
    Uni<EmployeeRes> getEmployeeById(@PathParam("id") String id);

    @GET
    Uni<List<EmployeeRes>> getAllEmployees();

    @PUT
    @Path("/{id}")
    Uni<EmployeeRes> updateEmployee(@PathParam("id") String id, EmployeeReq employeeReq);

    @DELETE
    @Path("/{id}")
    Uni<Boolean> deleteEmployee(@PathParam("id") String id);
}
