package org.assignment.entity;

import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoEntity;

public class Department extends ReactivePanacheMongoEntity {
    public String name;
}