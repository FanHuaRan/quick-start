package com.fhr.jtaexamples.atomikomysql.service.impl;

import com.fhr.jtaexamples.atomikomysql.dao.income.IncomeMapper;
import com.fhr.jtaexamples.atomikomysql.dao.user.UserMapper;
import com.fhr.jtaexamples.atomikomysql.entity.Income;
import com.fhr.jtaexamples.atomikomysql.entity.User;
import com.fhr.jtaexamples.atomikomysql.service.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

/**
 * @author FanHuaran
 * @description
 * @create 2018-05-29 14:00
 **/
@Service
public class IncomeServiceImpl implements IncomeService {

    @Autowired
    private IncomeMapper incomeMapper;

    @Autowired
    private UserMapper userMapper;

    @Transactional
    @Override
    public void saveForSuccess(String name, double amount) {
        User user = new User();
        user.setName(name);
        userMapper.insert(user);

        Income income = new Income();
        income.setUserId(user.getId());
        income.setAmount(amount);
        income.setOperateDate(new Timestamp(System.currentTimeMillis()));
        incomeMapper.insert(income);
    }

    @Transactional
    @Override
    public void saveForFail(String name, double amount) {
        User user = new User();
        user.setName(name);
        userMapper.insert(user);

        throwRuntimeException();

        Income income = new Income();
        income.setUserId(user.getId());
        income.setAmount(amount);
        income.setOperateDate(new Timestamp(System.currentTimeMillis()));
        incomeMapper.insert(income);
    }

    public void throwRuntimeException() {
        throw new RuntimeException("User defined exceptions");
    }
}
