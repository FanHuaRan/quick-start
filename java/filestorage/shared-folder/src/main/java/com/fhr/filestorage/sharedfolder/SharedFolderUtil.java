package com.fhr.filestorage.sharedfolder;

import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;
import jcifs.smb.SmbFileOutputStream;
import org.apache.commons.io.IOUtils;

import java.io.*;

/**
 * @author Fan Huaran
 * created on 2018/11/26
 * @description 共享文件工具类demo，基于smb封装
 */
public class SharedFolderUtil {
    private static final String REMOTE_SHARED_FOLDER_URL = "smb://share:admin@192.168.135.11/sharedFolder/";

    private static final int DEFAULT_BUFFER_LEN = 1024 * 1024 * 8;

    /**
     * 上传文件
     *
     * @param localFileName
     * @param remoteFileName
     * @throws Exception
     */
    private static void uploadFile(String localFileName, String remoteFileName) throws Exception {
        InputStream in = null;
        OutputStream out = null;
        try {
            // 创建远程文件对象
            SmbFile remoteFile = new SmbFile(REMOTE_SHARED_FOLDER_URL + remoteFileName);
            // 创建连接
            remoteFile.connect();

            //创建文件流
            in = new BufferedInputStream(new FileInputStream(localFileName), DEFAULT_BUFFER_LEN);
            out = new BufferedOutputStream(new SmbFileOutputStream(remoteFile), DEFAULT_BUFFER_LEN);

            byte[] buffer = new byte[DEFAULT_BUFFER_LEN];
            int len; //读取长度
            while ((len = in.read(buffer, 0, buffer.length)) != -1) {
                out.write(buffer, 0, len);
            }
            out.flush(); //刷新缓冲的输出流
        } finally {
            IOUtils.closeQuietly(in);
            IOUtils.closeQuietly(out);
        }
    }

    /**
     * 下载文件
     *
     * @param remoteFileName
     * @param localFileName
     * @throws Exception
     */
    private static void downloadFile(String remoteFileName, String localFileName) throws Exception {
        new File(localFileName).createNewFile();
        InputStream in = null;
        OutputStream out = null;
        try {
            //创建远程文件对象
            SmbFile remoteFile = new SmbFile(REMOTE_SHARED_FOLDER_URL + remoteFileName);
            remoteFile.connect(); //尝试连接
            // remoteFile.exists();
            //创建文件流
            in = new BufferedInputStream(new SmbFileInputStream(remoteFile), DEFAULT_BUFFER_LEN);
            out = new BufferedOutputStream(new FileOutputStream(localFileName), DEFAULT_BUFFER_LEN);
            //读取文件内容
            byte[] buffer = new byte[DEFAULT_BUFFER_LEN];
            int len; //读取长度
            while ((len = in.read(buffer, 0, buffer.length)) != -1) {
                out.write(buffer, 0, len);
            }

            out.flush(); //刷新缓冲的输出流
        } finally {
            IOUtils.closeQuietly(in);
            IOUtils.closeQuietly(out);
        }
    }
}
