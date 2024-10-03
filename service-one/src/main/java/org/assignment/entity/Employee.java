package org.assignment.entity;

import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bson.types.ObjectId;

@EqualsAndHashCode(callSuper = true)
@Data
public class Employee extends ReactivePanacheMongoEntity {
    private String name;
    private String position;
    private ObjectId departmentId;
}
