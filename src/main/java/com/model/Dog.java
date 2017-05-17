package com.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Aleksey_Zverkov on 28.03.2017.
 */
public class Dog implements Serializable {

    private static AtomicInteger current_id = new AtomicInteger(0);

    private int id;

    @Size(min=3, max=30)
    private String name;

    @NotNull
    private boolean sex;

    @Min(value = 0, message = "Age must be positive")
    private int age;

    public Dog() {
    }

    public Dog(String name, boolean sex, int age) {
        this.id = current_id.incrementAndGet();
        this.name = name;
        this.sex = sex;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
