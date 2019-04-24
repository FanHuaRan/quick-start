package com.github.fhr.basic.springweb.repeat;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Fan Huaran
 * created on 2019/2/22
 * @description
 */
public class HttpHelper {

    public static byte[] getBodyFromRequest(HttpServletRequest servletRequest) throws IOException {
        InputStream inputStream = servletRequest.getInputStream();
        int available = inputStream.available();
        byte[] data = new byte[available];
        inputStream.read(data, 0, available);
        // 这个流没有外部资源，无需关闭
        return data;
    }

}
