package com.fhr.jtaexamples.atomikomysql.controller;

import com.fhr.jtaexamples.atomikomysql.dao.income.IncomeMapper;
import com.fhr.jtaexamples.atomikomysql.dao.user.UserMapper;
import com.fhr.jtaexamples.atomikomysql.entity.Income;
import com.fhr.jtaexamples.atomikomysql.entity.User;
import com.fhr.jtaexamples.atomikomysql.service.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;

/**
 * @author FanHuaran
 * @description
 * @create 2018-05-29 13:49
 **/
@RestController
@RequestMapping("/income")
public class IncomeController {
    public static final String RESULT_SUCCESS = "success";
    public static final String RESULT_FAILED = "failed";

    @Autowired
    IncomeService incomeService;

    @GetMapping("/addincome/1")
    @Transactional
    public String addIncome1(@RequestParam("name") String name, @RequestParam("amount") double amount) {

        try {
            incomeService.saveForSuccess(name,amount);

            return RESULT_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return RESULT_FAILED + ":" + e.getMessage();
        }
    }

    @GetMapping("/addincome/2")
    @Transactional
    public String addIncome2(@RequestParam("name") String name, @RequestParam("amount") double amount) {
        try {
            incomeService.saveForFail(name,amount);

            return RESULT_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
            // return RESULT_FAILED + ":" + e.getMessage();
        }
    }


}
