package com.github.fhr.grizzily.echo;

import java.util.Arrays;

/**
 * @author Fan Huaran
 * created on 2019/2/12
 * @description
 */
public class Main {

    public static void main(String[] args) {
        System.out.println(Arrays.asList(1, 2, 3, 4, 5)
                .stream()
                .reduce((a, b) -> (a + b)).get());
    }

}
