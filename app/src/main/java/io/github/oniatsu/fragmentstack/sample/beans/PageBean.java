package io.github.oniatsu.fragmentstack.sample.beans;

import java.io.Serializable;

/**
 * PageBean
 */
public class PageBean implements Serializable {

    private final String name;
    private final int number;

    public PageBean(String name, int number) {
        this.name = name;
        this.number = number;
    }
    public String getName() {
        return this.name;
    }
    public int getNumber() {
        return this.number;
    }

    public String getPageNumberString() {
        return "Page " + number;
    }
}
