package com.service;

import com.model.Dog;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class DogServiceImpl implements DogService {

    private static AtomicInteger current_id = new AtomicInteger(0);

    private static final ConcurrentHashMap<Integer, Dog> dogs = new ConcurrentHashMap<>();

    @Override
    public List<Dog> getAll() {
        return new ArrayList<>(dogs.values());
    }

    @Override
    public Dog getById(int id) {
        return dogs.get(id);
    }

    @Override
    public Dog create(Dog dog) {
        if (!dogs.containsKey(dog.getId())){
            dog.setId(current_id.getAndIncrement());
        }
        dogs.put(dog.getId(), dog);
        return dog;
    }

    @Override
    public Dog update(int id, Dog dog) {
        dog.setId(id);
        dogs.put(id, dog);
        return dogs.get(id);
    }

    @Override
    public Dog delete(int id) {
        Dog dog = dogs.remove(id);
        return dog;
    }
}
