package com.controller;

import com.model.Dog;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/")
public class DogController {

    private static AtomicInteger current_id = new AtomicInteger(0);

    private static final ConcurrentHashMap<Integer, Dog> dogs = new ConcurrentHashMap<>();
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

    @RequestMapping(value = "/dog", method = RequestMethod.GET)
    @ResponseBody
    public ConcurrentHashMap<Integer, Dog> getAllDogs() {
        return dogs;
    }

    @RequestMapping(value = "/dog/{id}", method = RequestMethod.GET)
    public ResponseEntity getDogById (@PathVariable("id") int id) {
        Dog dog = dogs.get(id);
        if (dog != null)
            return ResponseEntity
                .status(HttpStatus.OK)
                .body(dog);
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body("No Dog found for ID " + id);
    }

    @RequestMapping(value = "/dog", method = RequestMethod.POST)
    @ResponseBody
    public Dog createDog(@RequestBody Dog dog) {
        if (!dogs.containsKey(dog.getId())){
            dog.setId(current_id.getAndIncrement());
        }
        dogs.put(dog.getId(), dog);
        return dog;
    }

    @RequestMapping(value = "/dog/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public Dog updateDogById(@PathVariable int id, @RequestBody Dog dog) {
        dog.setId(id);
        dogs.put(id, dog);
        return dog;
    }

    @RequestMapping(value = "/dog/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity removeDogById(@PathVariable int id) {
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
