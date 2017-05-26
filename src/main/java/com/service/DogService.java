package com.service;

import com.model.Dog;

import java.util.List;

public interface DogService {

    List<Dog> getAll();

    Dog getById(int id);

    Dog create(Dog dog);

    Dog update(int id, Dog dog);

    Dog delete(int id);
}
