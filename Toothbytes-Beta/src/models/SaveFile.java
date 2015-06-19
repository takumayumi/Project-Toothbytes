/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.awt.geom.Area;

/**
 *
 * @author Jolas
 */
public class SaveFile {
    private int num;
    private String state;
    private String remark;
    private Area mark;
    private double price;

    public SaveFile(int num, String state, String remark, Area mark, double price) {
        this.num = num;
        this.state = state;
        this.remark = remark;
        this.mark = mark;
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    
    public Area getMark() {
        return mark;
    }

    public void setMark(Area mark) {
        this.mark = mark;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    
}
