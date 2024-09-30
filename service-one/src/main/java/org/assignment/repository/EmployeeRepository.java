package org.assignment.repository;

import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.assignment.entity.Employee;

@ApplicationScoped
public class EmployeeRepository implements ReactivePanacheMongoRepository<Employee> {}