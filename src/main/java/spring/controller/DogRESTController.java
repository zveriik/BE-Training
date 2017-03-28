package spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.dao.DogDAO;
import spring.model.Dog;

import java.util.List;

@RestController
public class DogRESTController {

    @Autowired
    private DogDAO dogDAO;

    @RequestMapping("/")
    public String welcome() {
        return "Welcome.";
    }

    @GetMapping(value = "/dogs")
    public List getDogs() {
        return dogDAO.list();
    }

    @GetMapping(value = "/dogs/{id}")
    public ResponseEntity getDogById (@PathVariable("id") Long id) {
        Dog dog = dogDAO.get(id);
        if (dog == null){
            return new ResponseEntity("No Dog found for ID " + id, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(dog, HttpStatus.OK);
    }

    @PutMapping("/dogs/{id}")
    public ResponseEntity updateCustomer(@PathVariable Long id, @RequestBody Dog dog) {
        dog = dogDAO.update(id, dog);
        if (null == dog) {
            return new ResponseEntity("No Dog found for ID " + id, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(dog, HttpStatus.OK);
    }
}
