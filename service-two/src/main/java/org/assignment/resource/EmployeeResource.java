package org.assignment.resource;

import io.smallrye.common.annotation.Blocking;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.assignment.dto.EmployeeReq;
import org.assignment.dto.EmployeeRes;
import org.assignment.service.EmployeeService;

import java.util.List;

@Path("/employees")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Blocking
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
        return employeeService.getEmployeeById(id);
    }

    @GET
    public Uni<List<EmployeeRes>> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @PUT
    @Path("/{id}")
    public Uni<EmployeeRes> updateEmployee(@PathParam("id") String id, EmployeeReq employeeReq) {
        return employeeService.updateEmployee(id, employeeReq);
    }

    @DELETE
    @Path("/{id}")
    public Uni<Boolean> deleteEmployee(@PathParam("id") String id) {
        return employeeService.deleteEmployee(id);
    }
}