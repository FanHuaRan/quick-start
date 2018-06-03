package com.fhr.jtaexamples.atomikomysql.dao.income;

import com.fhr.jtaexamples.atomikomysql.entity.Income;
import org.apache.ibatis.annotations.Insert;

/**
 * @author FanHuaran
 * @description
 * @create 2018-05-29 13:52
 **/
public interface IncomeMapper {

    @Insert("INSERT INTO INCOME(userId,amount,operateDate) VALUES(#{userId},#{amount},#{operateDate})")
    public void insert(Income income);

}
