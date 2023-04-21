package com.flrjcx.xypt.common.utils;

import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Created on 2019年12月11日 下午2:06:15
 * <p>Title:       [公共工具模块]/p>
 * <p>Description: [带连接池功能的httpUtil]</p >
 * <p>Company:     羚羊极速</p >
 *
 * @author [wangyunliang]
 * @version 1.0
 */
public class HttpPoolUtils {
    private static final int MAX_TIMEOUT = 30000;
    private static Logger log = LoggerFactory.getLogger(HttpUtils.class);
    private static PoolingHttpClientConnectionManager connMgr;
    private static RequestConfig requestConfig;
    public static final String HOST = "https://api01.aliyun.venuscn.com/ip";
    public static final String APP_CODE = "85f02a14a06d4f749065a74b4f59d29d";

    static {
        // 设置连接池
        connMgr = new PoolingHttpClientConnectionManager();
        // 设置连接池大小
        connMgr.setMaxTotal(2000);
        connMgr.setDefaultMaxPerRoute(connMgr.getMaxTotal() - 10);

        RequestConfig.Builder configBuilder = RequestConfig.custom();
        // 设置连接超时
        configBuilder.setConnectTimeout(MAX_TIMEOUT);
        // 设置读取超时
        configBuilder.setSocketTimeout(MAX_TIMEOUT);
        // 设置从连接池获取连接实例的超时
        configBuilder.setConnectionRequestTimeout(MAX_TIMEOUT);
        // 在提交请求之前 测试连接是否可用
        // configBuilder.setStaleConnectionCheckEnabled(true);
        requestConfig = configBuilder.build();

    }

    /**
     * 发送 GET 请求（HTTP），不带输入数据
     *
     * @param url
     * @return
     */
    public static String get(String url) {
        return get(url, new HashMap<String, Object>(), new HashMap<String, String>());
    }

    public static String get(String url, Map<String, String> headers) {
        return get(url, new HashMap<String, Object>(), headers);
    }

    /**
     * 发送 GET 请求（HTTP），K-V形式
     *
     * @param url
     * @param params
     * @return
     */
    public static String get(String url, Map<String, Object> params, Map<String, String> headers) {
        String apiUrl = url;
        StringBuffer param = new StringBuffer();
        int i = 0;
        if (params != null) {
            for (String key : params.keySet()) {
                if (i == 0) {
                    param.append("?");
                } else {
                    param.append("&");
                }
                param.append(key).append("=").append(params.get(key));
                i++;
            }
        }
        apiUrl += param;
        String result = null;
        CloseableHttpClient httpclient = buildHttpClient();

        CloseableHttpResponse response = null;
        try {
            URL url1 = new URL(apiUrl);
            URI uri = new URI(url1.getProtocol(), url1.getAuthority(), url1.getPath(), url1.getQuery(), null);
            HttpGet httpGet = new HttpGet(uri);
            httpGet.setConfig(requestConfig);
            if (headers != null) {
                for (Entry<String, String> entry : headers.entrySet()) {
                    httpGet.setHeader(entry.getKey(), entry.getValue());
                }
            }
            response = httpclient.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                result = EntityUtils.toString(response.getEntity(), "UTF-8");
            } else {
                log.error("访问URL错误:" + url, statusCode);
            }
        } catch (IOException | URISyntaxException e) {
            log.error("", e);
        } finally {
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException e) {
                    log.error("", e);
                }
            }
        }
        return result;
    }

    /**
     * 发送 POST 请求（HTTP），不带输入数据
     *
     * @param apiUrl
     * @return
     */
    public static String post(String apiUrl) {
        return post(apiUrl, new HashMap<String, Object>());
    }

    private static String post(String apiUrl, HashMap<String, Object> params) {
        return post(apiUrl, params, new HashMap<String, String>());
    }

    public static String post(String apiUrl, Map<String, String> headers) {
        return post(apiUrl, new HashMap<String, Object>(), headers);
    }

    /**
     * 发送 POST 请求（HTTP），K-V形式
     *
     * @param apiUrl API接口URL
     * @param params 参数map
     * @return
     */
    public static String post(String apiUrl, Map<String, Object> params, Map<String, String> headers) {
        CloseableHttpClient httpClient = buildHttpClient();
        String httpStr = null;
        HttpPost httpPost = new HttpPost(apiUrl);
        CloseableHttpResponse response = null;

        try {
            httpPost.setConfig(requestConfig);
            List<NameValuePair> pairList = new ArrayList<NameValuePair>(params.size());
            for (Entry<String, Object> entry : params.entrySet()) {
                NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry.getValue().toString());
                pairList.add(pair);
            }
            if (headers != null) {
                for (Entry<String, String> entry : headers.entrySet()) {
                    httpPost.setHeader(entry.getKey(), entry.getValue());
                }
            }

            httpPost.setEntity(new UrlEncodedFormEntity(pairList, Charset.forName("UTF-8")));
            response = httpClient.execute(httpPost);

            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                httpStr = EntityUtils.toString(response.getEntity(), "UTF-8");
            } else {
                log.error("访问URL错误:" + apiUrl, statusCode);
            }
        } catch (IOException e) {
            log.error("", e);
        } finally {
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException e) {
                    log.error("", e);
                }
            }
        }
        return httpStr;
    }


    /**
     * 发送 POST 请求（HTTP），JSON形式
     *
     * @param apiUrl
     * @param json   json对象
     * @return
     */
    public static String doPost(String apiUrl, Object json) {
        CloseableHttpClient httpClient = buildHttpClient();
        String httpStr = null;
        HttpPost httpPost = new HttpPost(apiUrl);
        CloseableHttpResponse response = null;

        try {
            log.warn("httpclient pool, totalStats : {} ", connMgr.getTotalStats());

            final int available = connMgr.getTotalStats().getAvailable();
            if (connMgr.getTotalStats().getMax() - available > available) {
                log.warn("httpclient pool, totalStats:{}", connMgr.getTotalStats());
            }

            httpPost.setConfig(requestConfig);
            // 解决中文乱码问题
            StringEntity stringEntity = new StringEntity(json.toString(), "UTF-8");
            stringEntity.setContentEncoding("UTF-8");
            stringEntity.setContentType("application/json");
            httpPost.setEntity(stringEntity);
            response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                httpStr = EntityUtils.toString(response.getEntity(), "UTF-8");
            } else {
                log.error("访问URL错误:{},statuscode:{}, resp={}", apiUrl, statusCode, EntityUtils.toString(response.getEntity(), "UTF-8"));
            }
        } catch (Exception e) {
            log.error(e.getMessage());

        } finally {
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
            }
        }
        return httpStr;
    }


    private static CloseableHttpClient buildHttpClient() {
        HttpClientBuilder cb = HttpClientBuilder.create().setRetryHandler(new HttpRequestRetryHandler() {
            @Override
            public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
                log.warn("http retry times : {}", executionCount);
                return executionCount <= 3;
            }
        }).setConnectionManager(connMgr).setDefaultRequestConfig(requestConfig);
        return cb.build();
    }

    /**
     * 获取ip
     *
     * @param request
     * @return
     */
    public static String getIp(HttpServletRequest request){
        String ipAddress = null;
            ipAddress = request.getHeader("x-forwarded-for");
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getRemoteAddr();
                if (ipAddress.equals("127.0.0.1")) {
                    // 根据网卡取本机配置的IP
                    InetAddress inet = null;
                    try {
                        inet = InetAddress.getLocalHost();
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                    ipAddress = inet.getHostAddress();
                }
            }
            // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
            // "***.***.***.***".length()
            if (ipAddress != null && ipAddress.length() > 15) {
                // = 15
                if (ipAddress.indexOf(",") > 0) {
                    ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
                }
            }
            return ipAddress;
    }
    /**
     * 查询ip归属地
     *
     * @param ip
     * @return
     */
    public static String ipLocal(String ip) {
        Map<String, String> headers = new HashMap<>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + APP_CODE);
        Map<String, Object> querys = new HashMap<>();
        querys.put("ip", ip);
        return HttpPoolUtils.get(HOST, querys, headers);
    }
}
