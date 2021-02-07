package com.knoldus.Serializer;


import org.apache.kafka.common.serialization.Serializer;
import org.codehaus.jackson.map.ObjectMapper;

import java.util.Map;

public class UserSerializer implements Serializer {
    @Override
    public void configure(Map configs, boolean isKey) {

    }

    @Override
    public byte[] serialize(String s, Object o) {
        byte[] returnVal = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            returnVal = objectMapper.writeValueAsString(o).getBytes();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnVal;
    }



    @Override
    public void close() {

    }
}
