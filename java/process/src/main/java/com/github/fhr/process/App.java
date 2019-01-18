package com.github.fhr.process;

import java.io.*;

/**
 * @author Fan Huaran
 * created on 2019/1/18
 * @description
 */
public class App {
    public static void main(String[] args) throws IOException, InterruptedException {
        Process process = new ProcessBuilder("E:\\cat.exe", "E:\\Program Files\\Notepad++\\readme.txt").start();
        Runtime.getRuntime().exec()
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            int exitValue = process.waitFor();
            System.out.println(exitValue);
        }
    }

}
