package com.github.fhr.basic.springweb.repeat;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;

/**
 * @author Fan Huaran
 * created on 2019/2/22
 * @description
 */
public class RepeatableHttpServletRequestWrapper extends HttpServletRequestWrapper {

    private final byte[] data;

    /**
     * Constructs a request object wrapping the given request.
     *
     * @param request
     * @throws IllegalArgumentException if the request is null
     */
    public RepeatableHttpServletRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        data = HttpHelper.getBodyFromRequest(request);
    }

    @Override
    public ServletInputStream getInputStream() {
        return new ServletInputStream() {
            private final InputStream inputStream = new ByteArrayInputStream(data);

            @Override
            public int read() throws IOException {
                return inputStream.read();
            }

            @Override
            public int read(byte[] b) throws IOException {
                return inputStream.read(b);
            }

            @Override
            public int read(byte[] b, int off, int len) throws IOException {
                return inputStream.read(b, off, len);
            }

            @Override
            public long skip(long n) throws IOException {
                return inputStream.skip(n);
            }

            @Override
            public int available() throws IOException {
                return inputStream.available();
            }

            @Override
            public void close() throws IOException {
                inputStream.close();
            }

            @Override
            public synchronized void mark(int readlimit) {
                inputStream.mark(readlimit);
            }

            @Override
            public synchronized void reset() throws IOException {
                inputStream.reset();
            }

            @Override
            public boolean markSupported() {
                return true;
            }
        };
    }

    @Override
    public BufferedReader getReader() {
        return new BufferedReader(new InputStreamReader(new ByteArrayInputStream(data)));
    }


}
