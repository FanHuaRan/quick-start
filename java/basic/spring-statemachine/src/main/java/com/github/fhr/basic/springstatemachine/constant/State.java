package com.github.fhr.basic.springstatemachine.constant;

/**
 * @author Fan Huaran
 * created on 2019/3/31
 * @description
 */
public enum State {
    UNPAID, // 待支付
    WAITING_FOR_RECEIVE, // 待收货
    DONE   // 结束
}
