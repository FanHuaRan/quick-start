package com.github.fhr.jsonrpc4j.service.content;

/**
 * @author Fan Huaran
 * created on 2019/11/1
 * @description
 */
public class ContentServiceImpl implements ContentService {
    @Override
    public String getContent(String content) {
        return "content:" + content;
    }
}
