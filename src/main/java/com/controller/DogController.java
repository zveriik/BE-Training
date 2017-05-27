package com.controller;

import com.model.Dog;
import com.service.DogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/")
public class DogController {

    @Autowired
    private DogService dogService;

    @GetMapping(value = "/dog")
    @ResponseBody
    public ResponseEntity getAllDogs() {
        List<Dog> dogs = dogService.getAll();
        if (!dogs.isEmpty()){
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(dogs);
        }
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Dogs not found");
    }

    @GetMapping(value = "/dog/{id}")
    public ResponseEntity getDogById (@PathVariable("id") @Valid int id) {
        Dog dog = dogService.getById(id);
        if (dog != null)
            return ResponseEntity
                .status(HttpStatus.OK)
                .body(dog);
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body("No Dog found for ID " + id);
    }

    @PostMapping(value = "/dog")
    @ResponseBody
    public Dog createDog(@RequestBody @Valid Dog dog) {
        return dogService.create(dog);
    }

    @PutMapping(value = "/dog/{id}")
    @ResponseBody
    public Dog updateDogById(@PathVariable int id, @RequestBody @Valid Dog dog) {
        return dogService.update(id, dog);
    }

    @DeleteMapping(value = "/dog/{id}")
    @ResponseBody
    public ResponseEntity removeDogById(@PathVariable @Valid int id) {
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
