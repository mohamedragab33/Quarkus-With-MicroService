package org.assignment.service;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.assignment.dto.EmployeeReq;
import org.assignment.dto.EmployeeRes;
import org.assignment.entity.Employee;
import org.assignment.exception.EmployeeNotFoundException;
import org.assignment.mapper.EntityMapper;
import org.assignment.repository.EmployeeRepository;
import org.bson.types.ObjectId;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Retry;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class EmployeeService {

    @Inject
    EmployeeRepository employeeRepository;

    private final EntityMapper mapper = EntityMapper.INSTANCE;

    @Retry(maxRetries = 4, delay = 1000)
    @CircuitBreaker(requestVolumeThreshold = 4, delay = 5000)
    public Uni<EmployeeRes> createEmployee(EmployeeReq employeeReq) {
        Employee employee = mapper.toEmployee(employeeReq);
        return employeeRepository.persist(employee)
                .onItem().transform(mapper::toEmployeeRes);
    }

    public Uni<EmployeeRes> getEmployeeById(ObjectId id) {
        return employeeRepository.findById(id)
                .onItem().ifNull().failWith(new EmployeeNotFoundException("Employee with ID " + id + " not found"))
                .onItem().transform(mapper::toEmployeeRes);
    }

    public Uni<List<EmployeeRes>> getAllEmployees() {
        return employeeRepository.listAll()
                .onItem().transform(employees -> employees.stream().map(mapper::toEmployeeRes).collect(Collectors.toList()));
    }

    public Uni<EmployeeRes> updateEmployee(ObjectId id, EmployeeReq employeeReq) {
        return employeeRepository.findById(id)
                .onItem().ifNull().failWith(new EmployeeNotFoundException("Employee with ID " + id + " not found"))
                .onItem().ifNotNull().transformToUni(existingEmployee -> {
                    existingEmployee.name = employeeReq.getName();
                    existingEmployee.position = employeeReq.getPosition();
                    existingEmployee.departmentId = employeeReq.getDepartmentId();
                    return employeeRepository.update(existingEmployee)
                            .onItem().transform(mapper::toEmployeeRes);
                });
    }

    public Uni<Boolean> deleteEmployee(ObjectId id) {
        return employeeRepository.findById(id)
                .onItem().ifNull().failWith(new EmployeeNotFoundException("Employee with ID " + id + " not found"))
                .onItem().ifNotNull().transformToUni(existingEmployee -> employeeRepository.deleteById(id));
    }
}
