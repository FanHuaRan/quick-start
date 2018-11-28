package com.fhr.akka.minirpg;

/**
 * @author Fan Huaran
 * created on 2018/11/28
 * @description
 */
public class PlayerInfo {
    private int id;
    private String name;
    private int exp;
    private int level;

    public PlayerInfo(int id, String name, int exp, int level) {
        this.id = id;
        this.name = name;
        this.exp = exp;
        this.level = level;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}