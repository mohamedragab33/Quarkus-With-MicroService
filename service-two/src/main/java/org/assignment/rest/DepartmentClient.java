package org.assignment.rest;

import io.smallrye.mutiny.Uni;
import org.assignment.dto.DepartmentReq;
import org.assignment.dto.DepartmentRes;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/departments")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RegisterRestClient(configKey = "department-api")
public interface DepartmentClient {

    @POST
    Uni<DepartmentRes> createDepartment(DepartmentReq departmentReq);

    @GET
    @Path("/{id}")
    Uni<DepartmentRes> getDepartmentById(@PathParam("id") String id);

    @GET
    Uni<List<DepartmentRes>> getAllDepartments();

    @PUT
    @Path("/{id}")
    Uni<DepartmentRes> updateDepartment(@PathParam("id") String id, DepartmentReq departmentReq);

    @DELETE
    @Path("/{id}")
    Uni<Boolean> deleteDepartment(@PathParam("id") String id);
}
