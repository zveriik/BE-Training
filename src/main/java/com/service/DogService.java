package com.service;

import com.model.Dog;

import java.util.concurrent.ConcurrentHashMap;

public interface DogService {

    ConcurrentHashMap<Integer, Dog> getAll();

    Dog getById(int id);

    Dog create(Dog dog);

    Dog update(int id, Dog dog);

    Dog delete(int id);

    boolean exists(Dog dog);
}
