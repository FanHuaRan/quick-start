package com.dmall.smartimage.depot.controller;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Fan Huaran
 * created on 2019/10/29
 * @description
 */
public class SignTool {

    private static byte[] CONSTANT_BYTES = { 0x65, 0x62, 0x72, 0x63, 0x55, 0x59, 0x69, 0x75, 0x78, 0x61, 0x5a, 0x76, 0x32, 0x58, 0x47, 0x75, 0x37, 0x4b, 0x49, 0x59, 0x4b, 0x78, 0x55, 0x72, 0x71, 0x66, 0x6e, 0x4f, 0x66, 0x70, 0x44, 0x46 };

    public static String sign(MySign mySign, String tempStr)
    {
        MessageDigest tempStrMessageDigest = getShaDigest();
        tempStrMessageDigest.update(tempStr.getBytes(StandardCharsets.UTF_8));
        byte[] tempStrSha1Bytes = tempStrMessageDigest.digest() ;

        byte[] tempStrSha1HexBytes = hexEncode4Bytes(tempStrSha1Bytes);
        byte[] uidBytes = String.valueOf(mySign.uid).getBytes(StandardCharsets.UTF_8);
        byte[] timeBytes = String.valueOf(mySign.time).getBytes(StandardCharsets.UTF_8);
        byte[] devUIDBytes = mySign.devUID.getBytes(StandardCharsets.UTF_8);

        byte[] mergeBytes = mergeBytes(tempStrSha1HexBytes, uidBytes, CONSTANT_BYTES, timeBytes, devUIDBytes);

        MessageDigest signMessageDigest = getShaDigest();
        signMessageDigest.update(mergeBytes);
        byte[] resultSha1Bytes = signMessageDigest.digest();
        return hexEncode(resultSha1Bytes);
    }

    public static String getDevUID(String feature)
    {
        return "O|" + md5(feature).toUpperCase();
    }

    private static MessageDigest getShaDigest(){
        try {
            return MessageDigest.getInstance("SHA");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private static String md5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(input.getBytes(StandardCharsets.UTF_8));
            byte[] outBytes = md.digest();
            return hexEncode(outBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private static String hexEncode(byte[] input) {
        StringBuilder builder = new StringBuilder();
        for(byte value : input) {
            String hex = Integer.toHexString(value & 0xFF);
            if(hex.length() < 2){
                builder.append("0");
            }
            builder.append(hex);
        }
        return builder.toString();
    }

    private static byte[] hexEncode4Bytes(byte[] input) {
        return hexEncode(input).getBytes(StandardCharsets.UTF_8);
    }

    private static byte[] mergeBytes(byte[]... bytesArray) {
        int length = 0;
        for (byte[] value : bytesArray) {
            length += value.length;
        }

        byte[] mergeBytes = new byte[length];
        int index = 0;
        for (byte[] value : bytesArray) {
            for (int i = 0, n = value.length; i < n; i++) {
                mergeBytes[index++] = value[i];
            }
        }

        return mergeBytes;
    }

    /**
     * input
     */
    public static class MySign {

        private long uid;

        private String devUID;

        private long time;

        public long getUid() {
            return uid;
        }

        public void setUid(long uid) {
            this.uid = uid;
        }

        public String getDevUID() {
            return devUID;
        }

        public void setDevUID(String devUID) {
            this.devUID = devUID;
        }

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }
    }

    public static void main(String[] args) {
        MySign mySign = new MySign();
        mySign.setUid(1L);
        mySign.setDevUID(SignTool.getDevUID("helloworld"));
        mySign.setTime(1L);
        System.out.println(sign(mySign,  "hello"));
    }

}
