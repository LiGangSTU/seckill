package com.example.seckilldemo.utils;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class JsonUtil {
    private static ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 对象转换为json字符串
     * @param object
     * @return
     */
    public static String object2JsonStr(Object object){
        try{
            return objectMapper.writeValueAsString(object);
        }catch (JsonProcessingException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Json转为对象
     * @param jsonStr
     * @param T
     * @param <T>
     * @return
     */
    public static <T> T jsonStr2Object(String jsonStr, Class<T> T){
        try {
            return objectMapper.readValue(jsonStr.getBytes(StandardCharsets.UTF_8), T);
        } catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
    public static <T>List<T> json2List(String jsonStr, Class<T> beanType){
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(List.class, beanType);
        try {
            List<T> list = objectMapper.readValue(jsonStr, javaType);
            return list;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
