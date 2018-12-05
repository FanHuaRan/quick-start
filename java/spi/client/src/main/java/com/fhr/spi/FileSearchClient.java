package com.fhr.spi;

import com.fhr.spi.filesearach.FileSearchService;

import java.util.ServiceLoader;

/**
 * @author Fan Huaran
 * created on 2018/12/5
 * @description
 */
public class FileSearchClient {
    public static void main(String[] args) {
        ServiceLoader<FileSearchService> loader = ServiceLoader.load(FileSearchService.class);
//        Iterator<FileSearchService> serviceIterator = loader.iterator();
        for(FileSearchService fileSearchService : loader){
            System.out.println(fileSearchService.search("hello-world"));
        }
    }
}
