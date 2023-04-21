package com.flrjcx.xypt.common.utils;


import com.flrjcx.xypt.common.model.result.ResponseData;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Map;

/**
 * Created on 2018/5/8 20:39
 * <p>Title:       []/p>
 * <p>Description: []</p>
 * <p>Company:     羚羊极速</p>
 *
 * @author [wudey]
 * @version 1.0
 */
public class HttpUtils {
    private static Logger logger = LoggerFactory.getLogger(HttpUtils.class);
    private static HttpClientBuilder httpBuilder = null;

    static {
        //update by 罗婷 20180803 将时间改为100000，原来为60000
        RequestConfig config = RequestConfig.custom().setConnectTimeout(100000)
                .setSocketTimeout(100000).build();
        httpBuilder = HttpClientBuilder.create().setDefaultRequestConfig(config);
    }

    /**
     * Created on 2018年5月22日
     * <p>Discription:[get方式通信]</p>
     *
     * @param url:url
     * @param params:url中所带参数
     * @return
     * @author:[wangyunliang]
     */
    public static String sendGet(String url, Map<String, Object> params) {
        return sendGet(url, params, null);
    }

    /**
     * HTTP Get 获取内容
     */
    public static String sendGet(String url, Map<String, Object> params, Map<String, Object> headerParams) {
        if (!StringUtils.hasText(url)) {
            return "";
        }
        logger.info("url={},params={},headerParams={}", url, params, headerParams);
        CloseableHttpClient httpClient = httpBuilder.build();
        CloseableHttpResponse response = null;
        HttpEntity entity = null;
        try {
            URIBuilder uriBuilder = new URIBuilder(url);
            if (!CollectionUtils.isEmpty(params)) {
                Iterator<Map.Entry<String, Object>> iter = params.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry<String, Object> entry = iter.next();
                    uriBuilder.addParameter(entry.getKey(), String.valueOf(entry.getValue()));
                }
            }

            HttpGet httpGet = new HttpGet(uriBuilder.build());
            // 添加头部参数 王云亮  mod 20180522
            if (!CollectionUtils.isEmpty(headerParams)) {
                for (Map.Entry<String, Object> entry : headerParams.entrySet()) {
                    httpGet.addHeader(entry.getKey(), entry.getValue().toString());
                }
            }

            response = httpClient.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                httpGet.abort();
                logger.error("HttpClient,error status code :" + statusCode);
            }

            entity = response.getEntity();
            if (entity != null) {
                String result = EntityUtils.toString(entity, StandardCharsets.UTF_8);
                return result;
            }
        } catch (Exception e) {
            logger.error("HttpClient, error invoke sendGet", e);
        } finally {
            if (null != entity) {
                try {
                    EntityUtils.consume(entity);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (null != response) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * get请求
     *
     * @return
     */
    public static String doGet(String url) {
        try {
            logger.info("url={}", url);
            CloseableHttpClient httpClient = httpBuilder.build();
            HttpGet request = new HttpGet(url);
            HttpResponse response = httpClient.execute(request);

            /**请求发送成功，并得到响应**/
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                /**读取服务器返回过来的json字符串数据**/
                String strResult = EntityUtils.toString(response.getEntity());

                return strResult;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Created on 2018年11月5日
     * <p>Discription:[增强get请求的返回数据]</p>
     *
     * @param url
     * @return ResponseData类型的数据，code为httpcode，message为返回报文，result没进行赋值
     * @author:[wangyunliang]
     */
    public static ResponseData doGetV2(String url) {
        try {
            logger.info("url={}", url);
            CloseableHttpClient httpClient = httpBuilder.build();
            HttpGet request = new HttpGet(url);
            HttpResponse response = httpClient.execute(request);

            ResponseData respData = new ResponseData();
            respData.setCode(response.getStatusLine().getStatusCode());

            /**请求发送成功，并得到响应**/
            /**读取服务器返回过来的json字符串数据**/
            String strResult = EntityUtils.toString(response.getEntity());
            respData.setMessage(strResult);
            return respData;
        } catch (IOException e) {
            logger.error("doGetV2 exception,url=" + url, e);
        }

        return null;
    }

    /**
     * Created on 2018年10月25日 11:30:36
     * <p>Discription:[get请求，携带header]</p>
     *
     * @author:[baixiaotao]
     * @param: url
     * @param: requestHeader
     * @return: java.lang.String
     */
    public static String doGet(String url, Map<String, String> requestHeader) {
        try {
            logger.info("url={}", url);
            CloseableHttpClient httpClient = httpBuilder.build();
            HttpGet request = new HttpGet(url);
            if (requestHeader != null) {
                for (Map.Entry<String, String> entry : requestHeader.entrySet()) {
                    request.setHeader(entry.getKey(), entry.getValue());
                }
            }
            HttpResponse response = httpClient.execute(request);

            /**请求发送成功，并得到响应**/
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                /**读取服务器返回过来的json字符串数据**/
                String strResult = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);

                return strResult;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * get请求,传字符集
     *
     * @return
     */
    public static String doGet(String url, Charset charset) {
        try {
            logger.info("url={}", url);
            CloseableHttpClient httpClient = httpBuilder.build();
            HttpGet request = new HttpGet(url);
            HttpResponse response = httpClient.execute(request);

            /**请求发送成功，并得到响应**/
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                /**读取服务器返回过来的json字符串数据**/
                String strResult = EntityUtils.toString(response.getEntity(), charset);

                return strResult;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * post请求(用于key-value格式的参数)
     *
     * @param url
     * @param params
     * @return
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static String doPost(String url, Map params, String body) {
        if (!StringUtils.hasText(url)) {
            return "";
        }
        logger.info("url={}", url);
        CloseableHttpClient httpClient = httpBuilder.build();
        BufferedReader in = null;
        try {
            URIBuilder uriBuilder = new URIBuilder(url);
            if (!CollectionUtils.isEmpty(params)) {
                Iterator<Map.Entry<String, Object>> iter = params.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry<String, Object> entry = iter.next();
                    Object value = entry.getValue();
                    if (null == value) {

                    }
                    uriBuilder.addParameter(entry.getKey(), String.valueOf(entry.getValue()));
                }
            }

            HttpPost request = new HttpPost(uriBuilder.build());
            if (!org.apache.commons.lang3.StringUtils.isEmpty(body)) {
                request.setEntity(new StringEntity(body, StandardCharsets.UTF_8));
            }
            HttpResponse response = httpClient.execute(request);

            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                request.abort();
                logger.error("HttpClient,error status code :" + statusCode);
            }
            in = new BufferedReader(new InputStreamReader(response.getEntity().getContent(),
                    StandardCharsets.UTF_8));

            StringBuffer sb = new StringBuffer("");
            String line = "";
            String nl = System.getProperty("line.separator");
            while ((line = in.readLine()) != null) {
                sb.append(line + nl);
            }

            return sb.toString();
        } catch (Exception e) {
            logger.error("HttpClient, error invoke doPost", e);
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public static String doDelete(String url) throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = "";

        try {
            HttpDelete httpPost = new HttpDelete(url);
            httpPost.setHeader("HTTP Method", "POST");
            httpPost.setHeader("Connection", "Keep-Alive");
            httpPost.setHeader("Content-Type", "application/json;charset=utf-8");
            response = httpClient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == 200) {
                resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        } catch (Exception var12) {
            throw var12;
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException var11) {
                var11.printStackTrace();
            }

        }

        return resultString;
    }

    public static String doPut(String url, String params, Map<String, String> requestHeader) throws Exception {
        logger.info("url={}", url);
        HttpPut httpPut = new HttpPut(url);
        httpPut.setHeader("Accept", "application/json");
        httpPut.setHeader("Content-Type", "application/json");
        if (requestHeader != null) {
            Iterator var4 = requestHeader.entrySet().iterator();

            while (var4.hasNext()) {
                Map.Entry<String, String> entry = (Map.Entry) var4.next();
                httpPut.setHeader((String) entry.getKey(), (String) entry.getValue());
            }
        }

        StringEntity entity = new StringEntity(params, StandardCharsets.UTF_8);
        httpPut.setEntity(entity);
        CloseableHttpClient httpClient = httpBuilder.build();
        CloseableHttpResponse response = null;

        String var11;
        try {
            response = httpClient.execute(httpPut);
            StatusLine status = response.getStatusLine();
            int state = status.getStatusCode();
            if (state != 200) {
                logger.error("请求返回:" + state + "(" + url + ")");
                if (response.getEntity() != null) {
                    logger.error("resp={}", EntityUtils.toString(response.getEntity()));
                }

                throw new Exception("http 请求异常");
            }

            HttpEntity responseEntity = response.getEntity();
            String jsonString = EntityUtils.toString(responseEntity, StandardCharsets.UTF_8);
            var11 = jsonString;
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException var21) {
                    var21.printStackTrace();
                }
            }

            try {
                httpClient.close();
            } catch (IOException var20) {
                var20.printStackTrace();
            }

        }

        return var11;
    }



    /**
     * Created on 2019/12/21
     * <p>Description:[将以前的doPost接口改为返回ResponseData的形式，这样调用者就知道了返回码]</p>
     *
     * @param url
     * @param params
     * @return
     * @author [wangyunliang]
     */
    public static ResponseData doPostV2(String url, String params) {
        // mod by 王云亮 20191221 params不打印出来，因为params可能很长，会影响性能，如需打印，由调用方打印。
        logger.info("url={}", url);
        HttpPost httpPost = new HttpPost(url);// 创建httpPost
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-Type", "application/json");
        StringEntity entity = new StringEntity(params, StandardCharsets.UTF_8);
        httpPost.setEntity(entity);

        CloseableHttpClient httpClient = httpBuilder.build();
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpPost);
            StatusLine status = response.getStatusLine();
            ResponseData responseData = new ResponseData();
            responseData.setCode(status.getStatusCode());
            responseData.setMessage(EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8));
            if (!responseData.isSuccess()) {
                logger.error("请求失败！resp={}", JsonUtils.object2jackJson(responseData));
            }
            return responseData;
        } catch (Throwable e) {
            logger.error("请求异常！", e);
            return null;
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    logger.error("response.close exception!", e);
                }
            }
            try {
                httpClient.close();
            } catch (IOException e) {
                logger.error("httpClient.close exception!", e);
            }
        }
    }

    /**
     * Created on 2018年10月25日 11:31:41
     * <p>Discription:[post请求，携带header]</p>
     *
     * @author:[baixiaotao]
     * @param: url
     * @param: params
     * @param: requestHeader
     * @return: java.lang.String
     */
    public static String doPost(String url, String params, Map<String, String> requestHeader) throws Exception {
        logger.info("url={}", url);
        HttpPost httpPost = new HttpPost(url);// 创建httpPost
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-Type", "application/json");
        if (requestHeader != null) {
            for (Map.Entry<String, String> entry : requestHeader.entrySet()) {
                httpPost.setHeader(entry.getKey(), entry.getValue());
            }
        }
        StringEntity entity = new StringEntity(params, StandardCharsets.UTF_8);
        httpPost.setEntity(entity);

        CloseableHttpClient httpClient = httpBuilder.build();
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpPost);
            StatusLine status = response.getStatusLine();
            int state = status.getStatusCode();
            if (state == HttpStatus.SC_OK) {
                HttpEntity responseEntity = response.getEntity();
                String jsonString = EntityUtils.toString(responseEntity, StandardCharsets.UTF_8);
                return jsonString;
            } else {
                logger.error("请求返回:" + state + "(" + url + ")");
                if (response.getEntity() != null) {
                    logger.error("resp={}", EntityUtils.toString(response.getEntity()));
                }
            }
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
