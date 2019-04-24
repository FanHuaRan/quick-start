package com.github.fhr.basic.limiter.guava;

import java.util.Scanner;

/**
 * Created by Huaran Fan on 2019/3/8
 *
 * @description 实现阿拉伯数字到中文数字转换, 基于递归
 */
public class Main {
    /**
     * 实现阿拉伯字符到中文字符映射
     */
    private static final String[] DICT = new String[]{"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
    /**
     * 阿拉伯数字单位
     */
    private static final Long[] UNITS = new Long[]{100000000L, 10000L, 1000L, 100L, 10L};
    /**
     * 中文数字单位
     */
    private static final String[] UNIT_STRS = new String[]{"亿", "万", "千", "百", "十"};

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            System.out.println(convertAlabToChinese(scanner.nextLong()));
        }
    }

    /**
     * 核心函数，基于递归
     *
     * @param value
     * @return
     */
    private static String convertAlabToChinese(long value) {
        for (int i = 0, n = UNITS.length; i < n; i++) {
            long unit = UNITS[i];
            String unitStr = UNIT_STRS[i];
            if (value >= unit) {
                return doCompute(unit, unitStr, value);
            }
        }

        return DICT[(int) value];
    }

    /**
     * 递归算左 + 递归算右 处理小细节
     *
     * @param unit
     * @param unitStr
     * @param value
     * @return
     */
    private static String doCompute(long unit, String unitStr, long value) {
        long high = value / unit;
        long low = value % unit;
        if (low == 0L) {
            return convertAlabToChinese(high) + unitStr;
        }
        return convertAlabToChinese(high) + unitStr + (low < (unit / 10) ? "零" : "") + convertAlabToChinese(low);
    }

}
