package com.github.fhr.quickstart.basic.load;

import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

/**
 * @author Fan Huaran
 * created on 2019/4/23
 * @description 一致性hash实现，支持通过权重配置虚拟节点
 */
public class ConsistencyLoadBalance implements LoadBalance {
    private final TreeMap<Integer, String> hashHoop;

    protected ConsistencyLoadBalance(TreeMap<Integer, String> hashHoop) {
        this.hashHoop = hashHoop;
    }

    public static ConsistencyLoadBalance create(String[] addresses, int[] weights) {
        if (addresses == null || addresses.length == 0) {
            throw new IllegalArgumentException("addresses must not be null or empty");
        }

        for (String address : addresses) {
            if (address == null || "".equals(address)) {
                throw new IllegalArgumentException("address must not be empty");
            }
        }

        for (int weight : weights) {
            if (weight <= 0) {
                throw new IllegalArgumentException("address must greater than zero:" + weight);
            }
        }

        if (weights == null || weights.length == 0) {
            throw new IllegalArgumentException("weights must not be null or empty");
        }

        if (addresses.length != weights.length) {
            throw new IllegalArgumentException("addresses must correspond weights");
        }


        TreeMap<Integer, String> hashHoop = new TreeMap<>();

        for (int i = 0, n = addresses.length; i < n; i++) {
            String address = addresses[i];
            int weight = weights[i];
            for (int j = 0; j < weight; j++) {
                String virtualAddress = address + i;
                int hash = computeHash(virtualAddress);
                hashHoop.put(hash, address);
            }
        }

        return new ConsistencyLoadBalance(hashHoop);
    }

    private static int computeHash(Object value) {
        return Math.abs(value.hashCode());
    }

    @Override
    public String getAddress(Object param) {
        int hash = param == null ? 0 : computeHash(param);
        Map.Entry<Integer, String> node = null;
        NavigableMap<Integer, String> navigableMap = hashHoop.tailMap(hash, false);
        if (navigableMap != null && !navigableMap.isEmpty()) {
            node = navigableMap.firstEntry();
        } else {
            node = hashHoop.firstEntry();
        }

        return node.getValue();
    }
}
