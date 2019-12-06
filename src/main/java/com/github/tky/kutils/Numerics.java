package com.github.tky.kutils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Numerics {

    private Numerics() {}

    /**
     * 提供精确的加法运算
     * @param oneAmount 被加数
     * @param twoAmount 加数
     * @return 两个参数的和
     */
    public static BigDecimal add(double oneAmount, double twoAmount, int scale) {
        return BigDecimal.valueOf(oneAmount).add(BigDecimal.valueOf(twoAmount)).setScale(scale);
    }

    public static BigDecimal add(BigDecimal oneAmount, BigDecimal twoAmount, int scale) {
        return oneAmount.add(twoAmount).setScale(scale);
    }
    
    /**
     * 提供精确的减法运算
     * @param oneAmount 被减数
     * @param twoAmount 减数
     * @return 两个参数的差
     */
    public static BigDecimal subtract(double oneAmount, double twoAmount, int scale) {
        return BigDecimal.valueOf(oneAmount).subtract(BigDecimal.valueOf(twoAmount)).setScale(scale);
    }
    
    public static BigDecimal subtract(BigDecimal oneAmount, BigDecimal twoAmount, int scale) {
        return oneAmount.subtract(twoAmount).setScale(scale);
    }

    /**
     * 提供精确的乘法运算
     * @param oneAmount 被乘数
     * @param twoAmount 乘数
     * @return 两个参数的积
     */
    public static BigDecimal multiply(double oneAmount, double twoAmount, int scale, RoundingMode roundingMode) {
        return BigDecimal.valueOf(oneAmount).multiply(BigDecimal.valueOf(twoAmount)).setScale(scale, roundingMode);
    }
    
    public static BigDecimal multiply(BigDecimal oneAmount, BigDecimal twoAmount, int scale, RoundingMode roundingMode) {
        return oneAmount.multiply(twoAmount).setScale(scale, roundingMode);
    }

    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指定精度，以后的数字按取舍模式进行取舍
     * @param oneAmount 被除数
     * @param twoAmount 除数
     * @param scale 精度
     * @param roundingMode 取舍模式
     * @return 两个参数的商
     */
    public static BigDecimal divide(double oneAmount, double twoAmount, int scale, RoundingMode roundingMode) {
        if (scale < 0) throw new IllegalArgumentException("The scale must be a positive integer or zero");
        return BigDecimal.valueOf(oneAmount).divide(BigDecimal.valueOf(twoAmount), scale, roundingMode);
    }
    
    public static BigDecimal divide(BigDecimal oneAmount, BigDecimal twoAmount, int scale, RoundingMode roundingMode) {
        if (scale < 0) throw new IllegalArgumentException("The scale must be a positive integer or zero");
        return oneAmount.divide(twoAmount, scale, roundingMode);
    }


    /**
     * 比较两个数是否相等
     * @param onwAmount 第一个数
     * @param twoAmount 第二个数
     * @return 比较结果
     */
    public static boolean eq(BigDecimal onwAmount, BigDecimal twoAmount) {
        return onwAmount.compareTo(twoAmount) == 0;
    }
    
    public static boolean eq(double d1, double d2, int scale, RoundingMode roundingMode) {
        BigDecimal onwAmount = new BigDecimal(d1).setScale(scale,roundingMode) ;
        BigDecimal twoAmount = new BigDecimal(d2).setScale(scale,roundingMode) ;
        return onwAmount.compareTo(twoAmount) == 0;
    }
    /**
     * 比较第一个数是否比第二个数小
     * @param onwAmount 第一个数
     * @param twoAmount 第二个数
     * @return 比较结果
     */
    public static boolean le(BigDecimal onwAmount, BigDecimal twoAmount) {
        return onwAmount.compareTo(twoAmount) < 0;
    }

    /**
     * 比较第一个数是否比第二个数大
     * @param onwAmount 第一个数
     * @param twoAmount 第二个数
     * @return 比较结果
     */
    public static boolean gt(BigDecimal onwAmount, BigDecimal twoAmount) {
        return onwAmount.compareTo(twoAmount) > 0;
    }
    
    /**
     * 使用指定精度比较两个浮点数据
     * @param d1 first
     * @param d2 second
     * @param scale
     * @param roundingMode {@link RoundingMode}
     * @return 比较结果
     */
    public static boolean gt(double d1, double d2, int scale, RoundingMode roundingMode) {
        BigDecimal onwAmount = new BigDecimal(d1).setScale(scale,roundingMode) ;
        BigDecimal twoAmount = new BigDecimal(d2).setScale(scale,roundingMode) ;
        return onwAmount.compareTo(twoAmount) > 0;
    }

    /**
     * 比较第一个数是否比第二个数小或相等
     * @param onwAmount 第一个数
     * @param twoAmount 第二个数
     * @return 比较结果
     */
    public static boolean leq(BigDecimal onwAmount, BigDecimal twoAmount) {
        return onwAmount.compareTo(twoAmount) <= 0;
    }

    /**
     * 比较第一个数是否比第二个数大或相等
     * @param onwAmount 第一个数
     * @param twoAmount 第二个数
     * @return 比较结果
     */
    public static boolean geq(BigDecimal onwAmount, BigDecimal twoAmount) {
        return onwAmount.compareTo(twoAmount) >= 0;
    }
}
