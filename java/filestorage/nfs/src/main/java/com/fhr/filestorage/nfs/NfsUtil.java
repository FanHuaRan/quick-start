package com.fhr.filestorage.nfs;

import com.sun.xfile.XFile;
import com.sun.xfile.XFileExtensionAccessor;
import com.sun.xfile.XFileInputStream;
import com.sun.xfile.XFileOutputStream;

import java.io.*;

/**
 * Created by Huaran Fan on 2018/11/26
 *
 * @description NFS文件封装demo
 * 参考：
 * https://docs.oracle.com/cd/E19455-01/806-1067/6jacl3e6g/index.html
 * https://github.com/raisercostin/yanfs
 */
public class NfsUtil {
    private static final int DEFAULT_BUFFER_LEN = 1024 * 1024 * 8;

    public boolean uploadFile(final String ip, final String user, final String password,
                              String localFilePath, final String remoteFilePath) throws Exception {
        String url = "nfs://" + ip + "/" + remoteFilePath;
        XFile xf = new XFile(url);

        // 登录NFS
        com.sun.nfs.XFileExtensionAccessor nfsx = (com.sun.nfs.nfsXFileExtensionAccessor) xf.getExtensionAccessor();
        if (!nfsx.loginPCNFSD(ip, user, password)) {
            return false;
        }

        try (FileInputStream in = new FileInputStream(localFilePath);
             XFileOutputStream xFileOutputStream = new XFileOutputStream(localFilePath);
             BufferedOutputStream outputStream = new BufferedOutputStream(xFileOutputStream, DEFAULT_BUFFER_LEN)) {
            {
                int c;
                byte[] buf = new byte[DEFAULT_BUFFER_LEN];
                while ((c = in.read(buf)) > 0) {
                    outputStream.write(buf, 0, c);
                }
            }
        }
        return true;
    }

    public boolean downloadFile(String ip, String user, String password,
                                String filePath, String localFilePath) throws Exception {
        new File(localFilePath).createNewFile();

        String url = "nfs://" + ip + "/" + filePath;
        XFile xf = new XFile(url);

        // 文件不存在或者不是文件
        if (!xf.exists() || !xf.isFile()) {
            return false;
        }

        // 登录NFS
        com.sun.nfs.XFileExtensionAccessor nfsx = (com.sun.nfs.nfsXFileExtensionAccessor) xf.getExtensionAccessor();
        if (!nfsx.loginPCNFSD(ip, user, password)) {
            return false;
        }

        try (XFileInputStream in = new XFileInputStream(xf);
             FileOutputStream fileOutputStream = new FileOutputStream(localFilePath);
             BufferedOutputStream outputStream = new BufferedOutputStream(fileOutputStream, DEFAULT_BUFFER_LEN)) {
            {
                int c;
                byte[] buf = new byte[DEFAULT_BUFFER_LEN];
                while ((c = in.read(buf)) > 0) {
                    outputStream.write(buf, 0, c);
                }
            }
        }
        return true;
    }
}

