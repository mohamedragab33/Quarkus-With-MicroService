package org.assignment.mapper;

import org.assignment.dto.DepartmentReq;
import org.assignment.dto.DepartmentRes;
import org.assignment.dto.EmployeeReq;
import org.assignment.dto.EmployeeRes;
import org.assignment.entity.Department;
import org.assignment.entity.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.bson.types.ObjectId;

@Mapper
public interface EntityMapper {
    EntityMapper INSTANCE = Mappers.getMapper(EntityMapper.class);

    // Department Mappings
    @Mapping(target = "id", source = "id")
    DepartmentRes toDepartmentRes(Department department);

    @Mapping(target = "id", ignore = true)
    Department toDepartment(DepartmentReq departmentReq);

    // Employee Mappings
    @Mapping(target = "id", source = "id")
    @Mapping(target = "departmentId", source = "departmentId")
    EmployeeRes toEmployeeRes(Employee employee);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "departmentId", source = "departmentId")
    Employee toEmployee(EmployeeReq employeeReq);

    // Conversion Methods
    default String objectIdToString(ObjectId objectId) {
        return objectId != null ? objectId.toHexString() : null;
    }

    default ObjectId stringToObjectId(String id) {
        return id != null ? new ObjectId(id) : null;
    }
}