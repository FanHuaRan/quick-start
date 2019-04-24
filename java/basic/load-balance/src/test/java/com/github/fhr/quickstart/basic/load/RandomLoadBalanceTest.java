package com.github.fhr.quickstart.basic.load;

import org.junit.Test;

import static org.junit.Assert.*;

public class RandomLoadBalanceTest {

    @Test
    public void getAddress() {
        String[] address = new String[]{"server1", "server2", "server3"};
        int[] weights = new int[]{1, 2, 3};
        LoadBalance loadBalance = RandomLoadBalance.create(address,weights);

        System.out.println(loadBalance.getAddress(null));
        System.out.println(loadBalance.getAddress(null));
        System.out.println(loadBalance.getAddress(null));
        System.out.println(loadBalance.getAddress(null));
        System.out.println(loadBalance.getAddress(null));
        System.out.println(loadBalance.getAddress(null));
        System.out.println(loadBalance.getAddress(null));
        System.out.println(loadBalance.getAddress(null));
        System.out.println(loadBalance.getAddress(null));
        System.out.println(loadBalance.getAddress(null));
        System.out.println(loadBalance.getAddress(null));
        System.out.println(loadBalance.getAddress(null));
        System.out.println(loadBalance.getAddress(null));
        System.out.println(loadBalance.getAddress(null));

    }
}