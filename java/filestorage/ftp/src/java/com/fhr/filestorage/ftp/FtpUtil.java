package com.fhr.filestorage.ftp;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.*;

/**
 * Created by Huaran Fan on 2018/11/26
 *
 * @description FTP工具封装demo，FtpClient应该还是比较重的对象，这儿没做复用连接，可以考虑使用Apache commons pool2构建FTP连接池。
 */
public class FtpUtil {

    private static final int DEFAULT_BUFFER_LEN = 1024 * 1024 * 8;


    /**
     * 向FTP服务器上传文件
     *
     * @param url      FTP服务器hostname
     * @param port     FTP服务器端口
     * @param username FTP登录账号
     * @param password FTP登录密码
     * @param path     FTP服务器保存目录
     * @param filename 上传到FTP服务器上的文件名
     * @param input    输入流
     * @return 成功返回true，否则返回false
     */
    public static boolean uploadFile(String url, int port, String username, String password, String path, String filename, InputStream input) throws Exception {
        FTPClient ftp = new FTPClient();
        try {
            // 连接FTP服务器
            ftp.connect(url, port);
            ftp.login(username, password);//登录
            int reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                return false;
            }

            ftp.changeWorkingDirectory(path);

            try (BufferedInputStream bufferedInput = new BufferedInputStream(input, DEFAULT_BUFFER_LEN)) {
                ftp.storeFile(filename, bufferedInput);
            }

            // input.close(); 文件流交给外部关闭
            ftp.logout();
            return true;
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException er) {
                    // ignore
                }
            }
        }
    }


    /**
     * 从FTP服务器下载文件
     *
     * @param url        FTP服务器hostname
     * @param port       FTP服务器端口
     * @param username   FTP登录账号
     * @param password   FTP登录密码
     * @param remotePath FTP服务器上的相对路径
     * @param fileName   要下载的文件名
     * @param localPath  下载后保存到本地的路径
     * @return
     */
    public static boolean downloadFile(String url, int port, String username, String password, String remotePath, String fileName, String localPath) throws Exception {
        boolean success = false;
        FTPClient ftp = new FTPClient();
        try {
            ftp.connect(url, port);
            ftp.login(username, password);//登录
            int reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                return false;
            }
            // 转移到FTP服务器目录
            ftp.changeWorkingDirectory(remotePath);
            FTPFile[] fs = ftp.listFiles();
            for (FTPFile ff : fs) {
                if (ff.getName().equals(fileName)) {
                    File localFile = new File(localPath + "/" + ff.getName());

                    try (OutputStream outputStream = new FileOutputStream(localFile);
                         BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream)) {
                        ftp.retrieveFile(ff.getName(), bufferedOutputStream);
                    }
                    return true;
                }
            }

            ftp.logout();
            return false;
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException er) {
                    // ignore
                }
            }
        }
    }
}