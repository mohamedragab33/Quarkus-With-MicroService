package org.assignment.entity;

import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class Department extends ReactivePanacheMongoEntity {
    private String name;

}