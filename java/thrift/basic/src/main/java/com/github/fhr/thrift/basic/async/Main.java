package com.github.fhr.thrift.basic.async;

/**
 * @author Fan Huaran
 * created on 2019/2/13
 * @description
 */
public class Main {
    public static void main(String[] args) {
        String key = "RCS.FK.1.36.1.16.119447|8.6553.20190213";

        String[] arrays = key.split("\\.");
        for(String item : arrays){
            System.out.println(item);
        }
    }
}
