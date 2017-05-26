package com.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Created by Aleksey_Zverkov on 28.03.2017.
 */
public class Dog implements Serializable {

    @Max(value = Integer.MAX_VALUE)
    private int id;

    @Size(min=3, max=30)
    private String name;

    @NotNull
    private String gender;

    @Min(value = 0, message = "Age must be positive")
    private int age;

    public Dog() {
    }

    public Dog(String name, String gender, int age) {
        this.name = name;
        this.gender = gender;
        this.age = age;
    }

    public Dog(int id, String name, String gender, int age) {
        this.id = id;
        this.name = name;
        this.gender = gender;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + id;
        result = 31 * result + name.hashCode();
        result = 31 * result + gender.hashCode();
        result = 31 * result + age;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof Dog)) {
            return false;
        }

        Dog dog = (Dog) obj;

        return dog.id == id &&
                dog.name.equals(name) &&
                dog.gender.equals(gender) &&
                dog.age == age;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
