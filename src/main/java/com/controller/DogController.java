package com.controller;

import com.model.Dog;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/dog")
public class DogController {

    private static final HashMap<Integer, Dog> dogs = new HashMap<>();
//    static {
//        ArrayList<Dog> dogList = new ArrayList<Dog>(){{
//            add(new Dog("one", true, 4));
//            add(new Dog( "two", false, 4));
//            add(new Dog("three", true, 4));
//        }};
//        for(Dog dog : dogList) {
//            dogs.put(dog.getId(), dog);
//        }
//    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public HashMap<Integer, Dog> getAllDogs() {
        return dogs;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public synchronized ResponseEntity getDogById (@PathVariable("id") int id) {
        Dog dog = dogs.get(id);
        if (dog != null)
            return ResponseEntity
                .status(HttpStatus.OK)
                .body(dog);
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body("No Dog found for ID " + id);
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    @ResponseBody
    public synchronized Dog createDog(@RequestBody Dog dog) {
        dogs.put(dog.getId(), dog);
        return dog;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public synchronized Dog updateDogById(@PathVariable int id, @RequestBody Dog dog) {
        dogs.put(id, dog);
        return dog;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public synchronized ResponseEntity removeDogById(@PathVariable int id) {
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
