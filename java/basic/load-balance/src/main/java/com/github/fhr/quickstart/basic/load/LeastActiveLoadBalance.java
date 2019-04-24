package com.github.fhr.quickstart.basic.load;

/**
 * @author Fan Huaran
 * created on 2019/4/24
 * @description 最少连接数，当前上下文暂时无法实现
 */
public class LeastActiveLoadBalance implements LoadBalance {
    @Override
    public String getAddress(Object param) {
        return null;
    }
}
