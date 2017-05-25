package com.controller;

import com.model.Dog;
import com.service.DogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/")
public class DogController {

    @Autowired
    private DogService dogService;

    @RequestMapping(value = "/dog", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity getAllDogs() {
        ConcurrentHashMap<Integer, Dog> dogs = dogService.getAll();
        if (!dogs.isEmpty()){
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(dogs);
        }
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Dogs not found");
    }

    @RequestMapping(value = "/dog/{id}", method = RequestMethod.GET)
    public ResponseEntity getDogById (@PathVariable("id") int id) {
        Dog dog = dogService.getById(id);
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
        return dogService.create(dog);
    }

    @RequestMapping(value = "/dog/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public Dog updateDogById(@PathVariable int id, @RequestBody Dog dog) {
        return dogService.update(id, dog);
    }

    @RequestMapping(value = "/dog/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity removeDogById(@PathVariable int id) {
        Dog dog = dogService.delete(id);
        if (dog != null)
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(dog);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("No Dog found for ID " + id);
    }
}
