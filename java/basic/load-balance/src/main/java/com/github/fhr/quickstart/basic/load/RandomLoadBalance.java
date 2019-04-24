package com.github.fhr.quickstart.basic.load;

import java.util.Arrays;
import java.util.Random;

/**
 * @author Fan Huaran
 * created on 2019/4/24
 * @description 随机实现
 */
public class RandomLoadBalance implements LoadBalance {
    private final String[] addresses;

    private final int[] weights;

    private final int totalWeight;

    protected RandomLoadBalance(String[] addresses, int[] weights, int totalWeight) {
        this.addresses = addresses;
        this.weights = weights;
        this.totalWeight = totalWeight;
    }

    public static LoadBalance create(String[] addresses) {
        int[] weights = null;
        if (addresses != null) {
            weights = new int[addresses.length];
            Arrays.fill(weights, 1);
        }

        return create(addresses, weights);
    }


    public static LoadBalance create(String[] addresses, int[] weights) {
        if (addresses == null || addresses.length == 0) {
            throw new IllegalArgumentException("addresses must not be null or empty");
        }

        for (String address : addresses) {
            if (address == null || "".equals(address)) {
                throw new IllegalArgumentException("address must not be empty");
            }
        }

        if (weights == null || weights.length == 0) {
            throw new IllegalArgumentException("weights must not be null or empty");
        }

        for (int weight : weights) {
            if (weight <= 0) {
                throw new IllegalArgumentException("address must greater than zero:" + weight);
            }
        }

        if (addresses.length != weights.length) {
            throw new IllegalArgumentException("addresses must correspond weights");
        }

        int totalWeight = 0;
        for (int weight : weights) {
            totalWeight += weight;
        }

        return new RandomLoadBalance(addresses, weights, totalWeight);
    }


    @Override
    public String getAddress(Object param) {
        long currentIndexer = new Random().nextInt(totalWeight);

        for (int i = 0, n = addresses.length; i < n; i++) {
            currentIndexer -= weights[i];
            if (currentIndexer <= 0) {
                return addresses[i];
            }
        }

        // this will not happen
        return null;
    }
}
