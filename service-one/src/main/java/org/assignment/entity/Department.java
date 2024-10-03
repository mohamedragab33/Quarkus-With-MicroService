package org.assignment.entity;

import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bson.types.ObjectId;

@EqualsAndHashCode(callSuper = true)
@Data
public class Department extends ReactivePanacheMongoEntity {
    private String name;


    public void setId(ObjectId id) {
        super.id = id;
    }

}