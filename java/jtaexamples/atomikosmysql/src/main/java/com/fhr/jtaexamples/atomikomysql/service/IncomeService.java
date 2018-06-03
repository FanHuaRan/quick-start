package com.fhr.jtaexamples.atomikomysql.service;

/**
 * @author FanHuaran
 * @description
 * @create 2018-05-29 13:59
 **/
public interface IncomeService {

    void saveForSuccess(String name,double amount);

    void saveForFail(String name,double amount);
}
