package com.github.fhr.quickstart.basic.load;

public class ConsistencyLoadBalanceTest {

    @org.junit.Test
    public void getAddress() {
        String[] address = new String[]{"server1", "server2", "server3"};
        int[] weights = new int[]{1, 2, 3};
        ConsistencyLoadBalance loadBalance = ConsistencyLoadBalance.create(address,weights);

        System.out.println("param:" + null + "server:" + loadBalance.getAddress(null));
        System.out.println("param:" + null + "server:" + loadBalance.getAddress(null));

        System.out.println("param:" + "sda234" + "server:" + loadBalance.getAddress("sda234"));
        System.out.println("param:" + "sda234" + "server:" + loadBalance.getAddress("sda234"));

        System.out.println("param:" + "sda235" + "server:" + loadBalance.getAddress("sda235"));
        System.out.println("param:" + "sda235" + "server:" + loadBalance.getAddress("sda235"));

        System.out.println("param:" + "sdasdfsfasdfasdfas235" + "server:" + loadBalance.getAddress("sdasdfsfasdfasdfas235"));
        System.out.println("param:" + "sdasdfsfasdfasdfas235" + "server:" + loadBalance.getAddress("sdasdfsfasdfasdfas235"));

        System.out.println("param:" + "12342423423235" + "server:" + loadBalance.getAddress("12342423423235"));
        System.out.println("param:" + "12342423423235" + "server:" + loadBalance.getAddress("12342423423235"));

    }
}