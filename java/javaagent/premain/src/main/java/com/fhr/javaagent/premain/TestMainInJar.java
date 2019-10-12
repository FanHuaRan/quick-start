package com.fhr.javaagent.premain;

/**
 * @author Fan Huaran
 * created on 2019/10/11
 * @description
 */
public class TestMainInJar {
    public static void main(String[] args) {
        System.out.println(new TransClass().getNumber());
    }
}
