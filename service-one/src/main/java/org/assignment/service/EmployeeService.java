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

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class EmployeeService {

    @Inject
    EmployeeRepository employeeRepository;

    private static final EntityMapper mapper = EntityMapper.INSTANCE;

    public Uni<EmployeeRes> createEmployee(EmployeeReq employeeReq) {
        Employee employee = mapper.toEmployee(employeeReq);
        return employeeRepository.persist(employee)
                .onItem().transform(mapper::toEmployeeRes);
    }

    public Uni<EmployeeRes> getEmployeeById(String id) {
        ObjectId objectId = mapper.stringToObjectId(id);
        return employeeRepository.findById(objectId)
                .onItem().ifNull().failWith(new EmployeeNotFoundException("Employee with ID " + id + " not found"))
                .onItem().transform(mapper::toEmployeeRes);
    }

    public Uni<List<EmployeeRes>> getAllEmployees() {
        return employeeRepository.listAll()
                .onItem().transform(employees -> employees.stream()
                        .map(mapper::toEmployeeRes)
                        .collect(Collectors.toList()));
    }

    public Uni<EmployeeRes> updateEmployee(String id, EmployeeReq employeeReq) {
        ObjectId objectId = mapper.stringToObjectId(id);
        return employeeRepository.findById(objectId)
                .onItem().ifNull().failWith(new EmployeeNotFoundException("Employee with ID " + id + " not found"))
                .onItem().ifNotNull().transformToUni(existingEmployee -> {
                    existingEmployee.setName(employeeReq.getName());
                    existingEmployee.setPosition(employeeReq.getPosition());
                    existingEmployee.setDepartmentId(mapper.stringToObjectId(employeeReq.getDepartmentId()));
                    return employeeRepository.update(existingEmployee)
                            .onItem().transform(mapper::toEmployeeRes);
                });
    }

    public Uni<Boolean> deleteEmployee(String id) {
        ObjectId objectId = mapper.stringToObjectId(id);
        return employeeRepository.findById(objectId)
                .onItem().ifNull().failWith(new EmployeeNotFoundException("Employee with ID " + id + " not found"))
                .onItem().ifNotNull().transformToUni(existingEmployee -> employeeRepository.deleteById(objectId));
    }
}
