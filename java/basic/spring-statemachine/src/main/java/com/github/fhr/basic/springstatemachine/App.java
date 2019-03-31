package com.github.fhr.basic.springstatemachine;

import com.github.fhr.basic.springstatemachine.constant.Event;
import com.github.fhr.basic.springstatemachine.constant.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.statemachine.StateMachine;

/**
 * @author Fan Huaran
 * created on 2019/3/31
 * @description
 */
@SpringBootApplication
public class App implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Autowired
    private StateMachine<State, Event> stateEventStateMachine;


    @Override
    public void run(String... args) throws Exception {
        stateEventStateMachine.start();
        stateEventStateMachine.sendEvent(Event.PAY);
        stateEventStateMachine.sendEvent(Event.RECEIVE);
        stateEventStateMachine.stop();
    }
}
