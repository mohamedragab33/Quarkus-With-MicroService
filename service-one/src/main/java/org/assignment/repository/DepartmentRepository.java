package org.assignment.repository;

import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.assignment.entity.Department;

@ApplicationScoped
public class DepartmentRepository implements ReactivePanacheMongoRepository<Department> {}