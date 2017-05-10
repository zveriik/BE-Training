package com.controller;

import com.model.Dog;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/dog")
public class DogController {

    private static final HashMap<Long, Dog> dogs = new HashMap<>();
    static {
        dogs.put(1L, new Dog(1L, "one", true, 4));
        dogs.put(2L, new Dog(2L, "two", false, 4));
        dogs.put(3L, new Dog(3L, "three", true, 4));
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public HashMap<Long, Dog> getDogs() {
        return dogs;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public synchronized ResponseEntity getDogById (@PathVariable("id") Long id) {
        Dog dog = dogs.get(id);
        if (dog != null)
            return ResponseEntity
                .status(HttpStatus.OK)
                .body(dog);
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body("No Dog found for ID " + id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    @ResponseBody
    public synchronized Dog updateDog(@PathVariable Long id) {
        Dog dog = new Dog();
        dog.setId(id);
        dogs.put(id, dog);
        return dog;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public synchronized ResponseEntity removeDog(@PathVariable Long id) {
        Dog dog = dogs.remove(id);
        if (dog != null)
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(dog);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("No Dog found for ID " + id);
    }
}
