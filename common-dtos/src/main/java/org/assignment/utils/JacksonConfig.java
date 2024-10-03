package org.assignment.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.bson.types.ObjectId;

import javax.enterprise.inject.Produces;
import javax.inject.Singleton;

@Singleton
public class JacksonConfig {

    @Produces
    public ObjectMapper objectMapper() {
        SimpleModule module = new SimpleModule();
        module.addDeserializer(ObjectId.class, new ObjectIdDeserializer());
        module.addSerializer(ObjectId.class, new ObjectIdSerializer());

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(module);

        return mapper;
    }
}
