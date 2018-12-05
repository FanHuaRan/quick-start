package com.fhr.spi.filesearch.network;

import com.fhr.spi.filesearach.FileSearchService;

/**
 * @author Fan Huaran
 * created on 2018/12/5
 * @description
 */
public class NetworkFileSearchService implements FileSearchService {
    @Override
    public String search(String file) {
        return "NetworkFile:" + file;
    }
}
