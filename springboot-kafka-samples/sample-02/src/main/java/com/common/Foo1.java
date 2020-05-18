package com.common;

/**
 * @author xinchen
 * @version 1.0
 * @date 18/05/2020 15:21
 */
public class Foo1 {
    private String foo;

    public Foo1() {
    }

    public Foo1(String foo) {
        this.foo = foo;
    }

    public String getFoo() {
        return foo;
    }

    public void setFoo(String foo) {
        this.foo = foo;
    }

    @Override
    public String toString() {
        return "Foo1{" +
                "foo='" + foo + '\'' +
                '}';
    }
}
