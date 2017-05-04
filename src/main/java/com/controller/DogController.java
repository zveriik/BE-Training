package com.controller;

import com.dao.DogDAO;
import com.model.Dog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dog")
public class DogController {

    @Autowired
    private DogDAO dogDAO;

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public List getDogs() {
        return dogDAO.list();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Dog getDogById (@PathVariable("id") Long id) {
        Dog dog = dogDAO.get(id);
//        if (dog == null){
//            return "No Dog found for ID " + id;
//        }
        return dog;
    }

    @RequestMapping(value = "/create/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Dog updateCustomer(@PathVariable Long id) {
//        Dog dog = dogDAO.get(id);
//        if (dog == null) {
            Dog dog = new Dog();
            dog.setId(id);
            dogDAO.create(dog);
//        }
        return dog;
    }
}
