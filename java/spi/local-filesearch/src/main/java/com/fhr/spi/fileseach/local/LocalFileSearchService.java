package com.fhr.spi.fileseach.local;

import com.fhr.spi.filesearach.FileSearchService;

/**
 * @author Fan Huaran
 * created on 2018/12/5
 * @description
 */
public class LocalFileSearchService implements FileSearchService {
    public String search(String file) {
        return "LocalFile:" + file;
    }
}
