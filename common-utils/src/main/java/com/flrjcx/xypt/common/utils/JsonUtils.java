package com.flrjcx.xypt.common.utils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JSON与POJO转换工具类.
 *
 * @author fengfei
 */
@Slf4j
public class JsonUtils {
    @SuppressWarnings("unchecked")
    @Deprecated
    public static <U> U json2object(String json, TypeReference<U> typeToken) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return (U) mapper.readValue(json, typeToken);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    public static String object2jackJson(Object arg0) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(arg0);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> T parse(String json, Class<T> clazz) {
        if (json == null || json.length() == 0) {
            return null;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            return objectMapper.readValue(json, clazz);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /***
     *
     * <p>Description:[json字符串转换为bean，json中是小写下划线格式，bean中是驼峰式]</p>
     * @author:[wangyunliang]
     * @param json
     * @param
     * @return
     */
    public static <T> T parse2(String json, TypeReference<T> valueTypeRef) {
        if (json == null || json.length() == 0) {
            return null;
        }
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setPropertyNamingStrategy(com.fasterxml.jackson.databind.PropertyNamingStrategy.SNAKE_CASE);
            return objectMapper.readValue(json, valueTypeRef);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T fastJsonParse(String json, Class<T> clazz) {
        if (json == null || json.length() == 0) {
            return null;
        }
        return JSON.parseObject(json, clazz);
    }

    public static String fastJsonToString(Object obj) {
        if (obj == null) {
            return null;
        }
        return JSON.toJSONString(obj);
    }

    /**
     * <p>Description:[使用jackson jar包，将json格式的字符串转换为JsonNode,便于后续使用jackson的相关方法进一步解析]</p>
     *
     * @param str
     * @return
     * @author:[wangyunliang]
     */
    public static JsonNode stringToJsonNode(String str) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readTree(str);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将对象的大写转换为下划线加大写，例如：userName-->USER_NAME
     *
     * @param object
     * @return
     * @throws JsonProcessingException
     */
    public static String toUpperCamelJSONString(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SnakeCaseStrategy.UPPER_CAMEL_CASE);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String reqJson = mapper.writeValueAsString(object);
        return reqJson;
    }

    /**
     * 将对象的大写转换为下划线加小写，例如：userName-->user_name
     *
     * @param object
     * @return
     * @throws JsonProcessingException
     */
    public static String toUnderlineJSONString(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String reqJson = mapper.writeValueAsString(object);
        return reqJson;
    }



    /**
     * 将下划线转换为驼峰的形式，例如：user_name-->userName
     *
     * @param json
     * @param clazz
     * @return
     * @throws IOException
     */
    public static <T> T toSnakeObject(String json, Class<T> clazz) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        T reqJson =  mapper.readValue(json, clazz);
        return reqJson;
    }

    /**
     * 将大写下划线转换为驼峰的形式，例如：USER_NAME-->userName
     *
     * @param json
     * @param clazz
     * @return
     * @throws IOException
     */
    public static <T> T toLowerSnakeObject(String json, Class<T> clazz) throws IOException {
        Map<String, Object> map = JSONObject.parseObject(json, Map.class);
        Map<String, Object> resultMap = new HashMap<>();
        for(Map.Entry<String, Object> entry : map.entrySet()) {
            resultMap.put(camelName(entry.getKey()), entry.getValue());
        }
        return BeanUtil.toBean(resultMap,clazz);
    }

    public static Map toUpperCaseObject(String json) throws IOException {
        Map<String, Object> map = JSONObject.parseObject(json, Map.class);
        Map<String, Object> resultMap = new HashMap<>();
        for(Map.Entry<String, Object> entry : map.entrySet()) {
            resultMap.put(toUnderlineJSONString(entry.getKey()).toUpperCase(), entry.getValue());
        }
        return resultMap;
    }

    public static String convertArrayToString(String[] strArr) {
        if (strArr == null || strArr.length == 0) {
            return "";
        }
        String res = "";
        for (int i = 0, len = strArr.length; i < len; i++) {
            res += strArr[i];
            if (i < len - 1) {
                res += ",";
            }
        }
        return res;
    }

    public static Map<String, Object> toReplaceKeyLow(Map<String, Object> map) throws JsonProcessingException {
        Map re_map = new HashMap();
        if (re_map != null) {
            for(Map.Entry<String, Object> entry : map.entrySet()) {
                re_map.put(StrUtil.toUnderlineCase(entry.getKey().toString()), entry.getValue());
            }
        }

        return re_map;
    }

    /**
     * 将下划线大写方式命名的字符串转换为驼峰式。如果转换前的下划线大写方式命名的字符串为空，则返回空字符串。
     * 例如：HELLO_WORLD->helloWorld
     * @param name 转换前的下划线大写方式命名的字符串
     * @return 转换后的驼峰式命名的字符串
     */
    public static String camelName(String name) {
        StringBuilder result = new StringBuilder();
        // 快速检查
        if (name == null || name.isEmpty()) {
            // 没必要转换
            return "";
        } else if (!name.contains("_")) {
            // 不含下划线，仅将首字母小写
            return name.toLowerCase();
        }
        // 用下划线将原始字符串分割
        String camels[] = name.split("_");
            for (String camel :  camels) {
                // 跳过原始字符串中开头、结尾的下换线或双重下划线
                if (camel.isEmpty()) {
                    continue;
                }
                // 处理真正的驼峰片段
                if (result.length() <= 1) {
                    // 第一个驼峰片段，全部字母都小写
                    result.append(camel.toLowerCase());
                } else {
                    // 其他的驼峰片段，首字母大写
                    result.append(camel.substring(0, 1).toUpperCase());
                    result.append(camel.substring(1).toLowerCase());
                }
            }
        return result.toString();

    }




//    public static void main(String[] args)  {
//        String json = "user_name";
//       System.out.println(StrUtil.toCamelCase(json));
//    }
}
