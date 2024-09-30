package org.assignment.mapper;

import org.assignment.dto.EmployeeReq;
import org.assignment.dto.EmployeeRes;
import org.assignment.dto.DepartmentReq;
import org.assignment.dto.DepartmentRes;
import org.assignment.entity.Employee;
import org.assignment.entity.Department;
import org.bson.types.ObjectId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EntityMapper {

    EntityMapper INSTANCE = Mappers.getMapper(EntityMapper.class);

    // Mapping for Employee
    @Mapping(target = "id", ignore = true)
    Employee toEmployee(EmployeeReq employeeReq);

    EmployeeRes toEmployeeRes(Employee employee);



    @Mapping(target = "id", ignore = true)
    Department toDepartment(DepartmentReq departmentReq);

    DepartmentRes toDepartmentRes(Department department);

    // Conversion for ObjectId from String and vice-versa
    default ObjectId fromString(String id) {
        return id != null ? new ObjectId(id) : null;
    }

    default String fromObjectId(ObjectId objectId) {
        return objectId != null ? objectId.toString() : null;
    }
}
