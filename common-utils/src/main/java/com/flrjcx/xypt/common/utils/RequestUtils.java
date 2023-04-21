package com.flrjcx.xypt.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author malaka
 * @Description TODO
 * @date 2022/11/5 3:20
 */
public class RequestUtils {

    public final static String KEY_METHOD = "method";
    public final static String KEY_HEADERS = "headers";
    public final static String KEY_PARAMS = "params";
    public final static String KEY_BODY = "body";
    public final static String KEY_IP = "ip";

    public static JSONObject getRequestInfoJson(@NotNull HttpServletRequest request) {
        JSONObject res = new JSONObject();
        res.put(KEY_METHOD, request.getMethod());
        res.put(KEY_HEADERS, getHeaders(request));
        res.put(KEY_BODY, getBody(request));
        res.put(KEY_PARAMS, getParams(request));
        res.put(KEY_IP, HttpPoolUtils.getIp(request));
        return res;
    }

    public static Map<String, String> getHeaders(HttpServletRequest request) {
        Map<String, String> map = new HashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            map.put(name, request.getHeader(name));
        }

        return map;
    }


    public static Map<String, String[]> getParams(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        return parameterMap;
    }

    public static String getBody(HttpServletRequest request) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder("");
        try
        {
            br = request.getReader();
            String str;
            while ((str = br.readLine()) != null)
            {
                sb.append(str);
            }
            br.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (null != br)
            {
                try
                {
                    br.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }

}
