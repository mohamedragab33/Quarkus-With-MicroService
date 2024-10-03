package org.assignment.service;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.assignment.dto.DepartmentReq;
import org.assignment.dto.DepartmentRes;
import org.assignment.rest.DepartmentClient;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
public class DepartmentService {

    @RestClient
    @Inject
    DepartmentClient departmentClient;

    public Uni<DepartmentRes> createDepartment(DepartmentReq departmentReq) {
        return departmentClient.createDepartment(departmentReq);
    }

    public Uni<DepartmentRes> getDepartmentById(String id) {
        return departmentClient.getDepartmentById(id);
    }

    public Uni<List<DepartmentRes>> getAllDepartments() {
        return departmentClient.getAllDepartments();
    }

    public Uni<DepartmentRes> updateDepartment(String id, DepartmentReq departmentReq) {
        return departmentClient.updateDepartment(id, departmentReq);
    }

    public Uni<Boolean> deleteDepartment(String id) {
        return departmentClient.deleteDepartment(id);
    }
}
