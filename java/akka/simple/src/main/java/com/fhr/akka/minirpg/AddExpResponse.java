package com.fhr.akka.minirpg;

/**
 * @author Fan Huaran
 * created on 2018/11/28
 * @description
 */
public class AddExpResponse {
    private int newExp;

    public AddExpResponse(int newExp) {
        this.newExp = newExp;
    }

    public int getNewExp() {
        return newExp;
    }

    public void setNewExp(int newExp) {
        this.newExp = newExp;
    }
}
