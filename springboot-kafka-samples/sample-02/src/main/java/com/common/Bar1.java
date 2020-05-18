package com.common;

/**
 * @author xinchen
 * @version 1.0
 * @date 18/05/2020 15:52
 */
public class Bar1 {
    public String bar;

    public Bar1() {
    }

    public Bar1(String bar) {
        this.bar = bar;
    }

    public String getBar() {
        return this.bar;
    }

    public void setBar(String bar) {
        this.bar = bar;
    }

    @Override
    public String toString() {
        return "Bar1 [bar=" + this.bar + "]";
    }
}
