package org.assignment.entity;

import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoEntity;
import org.bson.types.ObjectId;


public class Employee extends ReactivePanacheMongoEntity {
    public String name;
    public String position;
    public ObjectId departmentId;
}