package org.assignment.service;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.assignment.dto.DepartmentReq;
import org.assignment.dto.DepartmentRes;
import org.assignment.entity.Department;
import org.assignment.exception.DepartmentNotFoundException;
import org.assignment.mapper.EntityMapper;
import org.assignment.repository.DepartmentRepository;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class DepartmentService {

    @Inject
    DepartmentRepository departmentRepository;

    private final EntityMapper mapper = EntityMapper.INSTANCE;

    public Uni<DepartmentRes> createDepartment(DepartmentReq departmentReq) {
        Department department = mapper.toDepartment(departmentReq);
        return departmentRepository.persist(department)
                .onItem().transform(mapper::toDepartmentRes);
    }

    public Uni<DepartmentRes> getDepartmentById(ObjectId id) {
        return departmentRepository.findById(id)
                .onItem().ifNull().failWith(new DepartmentNotFoundException("Department with ID " + id + " not found"))
                .onItem().transform(mapper::toDepartmentRes);
    }

    public Uni<List<DepartmentRes>> getAllDepartments() {
        return departmentRepository.listAll()
.onItem().transform(departments -> departments.stream().map(mapper::toDepartmentRes).collect(Collectors.toList()));    }

    public Uni<DepartmentRes> updateDepartment(ObjectId id, DepartmentReq departmentReq) {
        return departmentRepository.findById(id)
                .onItem().ifNull().failWith(new DepartmentNotFoundException("Department with ID " + id + " not found"))
                .onItem().ifNotNull().transformToUni(existingDepartment -> {
                    existingDepartment.name = departmentReq.getName();
                    return departmentRepository.update(existingDepartment)
                            .onItem().transform(mapper::toDepartmentRes);
                });
    }

    public Uni<Boolean> deleteDepartment(ObjectId id) {
        return departmentRepository.findById(id)
                .onItem().ifNull().failWith(new DepartmentNotFoundException("Department with ID " + id + " not found"))
                .onItem().ifNotNull().transformToUni(existingDepartment -> departmentRepository.deleteById(id));
    }
}