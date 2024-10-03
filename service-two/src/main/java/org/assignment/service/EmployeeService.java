package org.assignment.service;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.assignment.dto.EmployeeReq;
import org.assignment.dto.EmployeeRes;
import org.assignment.rest.EmployeeClient;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;

@ApplicationScoped
public class EmployeeService {
    @RestClient
    @Inject
    EmployeeClient employeeClient;

    public Uni<EmployeeRes> createEmployee(EmployeeReq employeeReq) {
        return employeeClient.createEmployee(employeeReq);
    }

    public Uni<EmployeeRes> getEmployeeById(String id) {
        return employeeClient.getEmployeeById(id);
    }

    public Uni<List<EmployeeRes>> getAllEmployees() {
        return employeeClient.getAllEmployees();
    }

    public Uni<EmployeeRes> updateEmployee(String id, EmployeeReq employeeReq) {
        return employeeClient.updateEmployee(id, employeeReq);
    }

    public Uni<Boolean> deleteEmployee(String id) {
        return employeeClient.deleteEmployee(id);
    }
}
