package com.wupao.oneclickrelease.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * json工具类
 * <br>创建时间：2021/4/19
 *
 * @author 吴翔龙
 */
public class JsonUtil {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * 格式化json
     *
     * @param json json字符串
     * @return 格式化后的json字符串
     */
    public static String formatJson(String json) {
        if (StringUtils.isBlank(json)) {
            return null;
        }
        Object obj = null;
        try {
            obj = OBJECT_MAPPER.readValue(json, Object.class);
            return OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * json转对象
     *
     * @param jsonString json
     * @param clazz      对象.class
     * @param <T>        对象泛型
     * @return 转换后的对象数据
     */
    public static <T> T json2Pojo(String jsonString, Class<T> clazz) {
        OBJECT_MAPPER.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        try {
            return OBJECT_MAPPER.readValue(jsonString, clazz);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * json转list对象
     *
     * @param jsonString json
     * @param clazz      对象.class
     * @param <T>        对象泛型
     * @return 转换后的list对象数据
     */
    public static <T> List<T> json2List(String jsonString, Class<T> clazz) {
        try {
            JavaType t = OBJECT_MAPPER.getTypeFactory().constructParametricType(List.class, clazz);
            return OBJECT_MAPPER.readValue(jsonString, t);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 对象转json
     *
     * @param obj 实体对象
     * @param <T> 泛型
     * @return json字符串
     */
    public static <T> String objectToJson(T obj) {
        if (obj == null) {
            return null;
        }
        try {
            return obj instanceof String ? (String) obj : OBJECT_MAPPER.writeValueAsString(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
