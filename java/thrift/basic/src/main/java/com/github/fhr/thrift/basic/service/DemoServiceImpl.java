package com.github.fhr.thrift.basic.service;

import com.github.fhr.thrift.basic.dto.Person;
import com.github.fhr.thrift.basic.dto.QueryParameter;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Fan Huaran
 * created on 2019/1/31
 * @description
 */
public class DemoServiceImpl implements DemoService.Iface {
    private static final Logger logger = LoggerFactory.getLogger(DemoServiceImpl.class);

    @Override
    public String ping() throws TException {
        logger.info("ping");
        return "pong";
    }

    @Override
    public List<Person> getPersonList(QueryParameter parameter) throws TException {
        List<Person> list = new ArrayList<>(10);
        for (short i = 0; i < 10; i++) {
            Person p = new Person();
            p.setAge(i);
            p.setChildrenCount(Byte.valueOf(i + ""));
            p.setName("test" + i);
            p.setSalary(10000D);
            p.setSex(true);
            list.add(p);
        }
        return list;
    }
}
