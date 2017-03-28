package spring.dao;

import org.springframework.stereotype.Repository;
import spring.model.Dog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aleksey_Zverkov on 28.03.2017.
 */
@Repository
public class DogDAO implements Serializable {

    private static List<Dog> dogs;
    {
        dogs = new ArrayList();
        dogs.add(new Dog(1L, "one", true, 4));
        dogs.add(new Dog(2L, "two", false, 4));
        dogs.add(new Dog(3L, "three", true, 4));
    }

    public List list() {
        return dogs;
    }

    public Dog get(Long id) {
        for (Dog dog : dogs) {
            if (dog.getId().equals(id)) {
                return dog;
            }
        }
        return null;
    }

    public Dog create(Dog dog) {
        dog.setId(System.currentTimeMillis());;
        dogs.add(dog);
        return dog;
    }

    public Long delete(Long id){
        for (Dog dog : dogs) {
            if (dog.getId().equals(id)) {
                dogs.remove(dog);
                return id;
            }
        }
        return null;
    }

    public Dog update(Long id, Dog updatedDog){
        for (Dog dog : dogs) {
            if (dog.getId().equals(id)) {
                dog.setId(updatedDog.getId());
                dogs.remove(dog);
                dogs.add(updatedDog);
                return updatedDog;
            }
        }

        return null;
    }
}
