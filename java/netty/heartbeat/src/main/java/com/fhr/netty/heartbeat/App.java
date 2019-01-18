package com.fhr.netty.heartbeat;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Fan Huaran
 * created on 2019/1/18
 * @description
 */
public class App {

    public static void main(String[] args) {
        BufferedReader br = null;
        Process process = null;
        try {
            process = new ProcessBuilder("E:/cygwin64/bin/bash.exe",
                    "-l", // login
                    "-i", // interactive
                    "-c",
                    "\"octave --help --interactive --no-line-editing\"",
                    "vimdiff 1.txt 2.txt").start();
          //  process = Runtime.getRuntime().exec("E:\\cygwin64\\bin\\mintty.exe vimdiff 1.txt 2.txt", null);
            //  process = Runtime.getRuntime().exec("CMD.exe /C dir", null, new File("C:\\"));
            br = new BufferedReader(new InputStreamReader(process.getInputStream(), "GBK"));
            String line = null;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (process != null) {
                process.destroy();
            }
        }
    }
}

