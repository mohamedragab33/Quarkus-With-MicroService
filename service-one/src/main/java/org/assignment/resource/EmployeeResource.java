package org.assignment.resource;

import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.assignment.dto.EmployeeReq;
import org.assignment.dto.EmployeeRes;
import org.assignment.service.EmployeeService;
import org.bson.types.ObjectId;

import java.util.List;

@Path("/employees")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EmployeeResource {

    @Inject
    EmployeeService employeeService;

    @POST
    public Uni<EmployeeRes> createEmployee(EmployeeReq employeeReq) {
        return employeeService.createEmployee(employeeReq);
    }

    @GET
    @Path("/{id}")
    public Uni<EmployeeRes> getEmployeeById(@PathParam("id") String id) {
        return employeeService.getEmployeeById(new ObjectId(id));
    }

    @GET
    public Uni<List<EmployeeRes>> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @PUT
    @Path("/{id}")
    public Uni<EmployeeRes> updateEmployee(@PathParam("id") String id, EmployeeReq employeeReq) {
        return employeeService.updateEmployee(new ObjectId(id), employeeReq);
    }

    @DELETE
    @Path("/{id}")
    public Uni<Boolean> deleteEmployee(@PathParam("id") String id) {
        return employeeService.deleteEmployee(new ObjectId(id));
    }
}
