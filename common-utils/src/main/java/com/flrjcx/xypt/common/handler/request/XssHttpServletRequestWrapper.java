package com.flrjcx.xypt.common.handler.request;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author malaka
 * @Description 自定RequestWrapper，使body能多次访问
 *              HTTP请求是基于流（stream）的协议，一旦请求体的数据被读取，就无法重新读取。
 *              因此，如果需要读取请求体中的内容，可以将读取到的内容保存到一个变量中或者缓存中
 *              这里用HttpServletRequestWrapper对HttpServletRequest进行包装，并设置一个对象保存reader中的内容
 * @date 2022/11/5 16:37
 */
public class XssHttpServletRequestWrapper  extends HttpServletRequestWrapper {
    public String _body;

    public XssHttpServletRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        StringBuffer sBuffer = new StringBuffer();
        BufferedReader bufferedReader = request.getReader();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            sBuffer.append(line);
        }
        _body = sBuffer.toString();

    }

    @Override
    public ServletInputStream getInputStream() {
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(_body.getBytes());
        return new ServletInputStream() {
            @Override
            public int read() {
                return byteArrayInputStream.read();
            }

            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener listener) {

            }
        };
    }

    @Override
    public BufferedReader getReader() {
        return new BufferedReader(new InputStreamReader(this.getInputStream()));
    }
}