package org.assignment.resource;

import io.smallrye.common.annotation.Blocking;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.assignment.dto.DepartmentReq;
import org.assignment.dto.DepartmentRes;
import org.assignment.service.DepartmentService;

import java.util.List;

@Path("/departments")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Blocking
public class DepartmentResource {

    @Inject
    DepartmentService departmentService;

    @POST
    public Uni<DepartmentRes> createDepartment(DepartmentReq departmentReq) {
        return departmentService.createDepartment(departmentReq);
    }

    @GET
    @Path("/{id}")
    public Uni<DepartmentRes> getDepartmentById(@PathParam("id") String id) {
        return departmentService.getDepartmentById(id);
    }

    @GET
    public Uni<List<DepartmentRes>> getAllDepartments() {
        return departmentService.getAllDepartments();
    }

    @PUT
    @Path("/{id}")
    public Uni<DepartmentRes> updateDepartment(@PathParam("id") String id, DepartmentReq departmentReq) {
        return departmentService.updateDepartment(id, departmentReq);
    }

    @DELETE
    @Path("/{id}")
    public Uni<Boolean> deleteDepartment(@PathParam("id") String id) {
        return departmentService.deleteDepartment(id);
    }
}