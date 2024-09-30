package org.assignment.dto;

import lombok.Data;
import org.bson.types.ObjectId;

@Data
public class EmployeeReq {
    private String name;
    private String position;
    private ObjectId departmentId;
}
