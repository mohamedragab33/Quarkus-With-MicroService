package org.assignment.dto;

import lombok.Data;
import org.bson.types.ObjectId;

@Data
public class DepartmentRes {
    private ObjectId id;
    private String name;
}
