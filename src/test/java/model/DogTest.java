package model;

import com.model.Dog;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.testng.Assert.assertEquals;

@Test
public class DogTest {

    private static Validator validator;

    @BeforeClass
    private static void initValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    public void testValidName(){
        Dog dog = Dog.random();
        Set<ConstraintViolation<Dog>> errors = validator.validate(dog);
        assertEquals(errors.size(), 0);
    }

    public void testNotValidName(){
        Dog dog = Dog.random();
        dog.setName("1");
        Set<ConstraintViolation<Dog>> errors = validator.validate(dog);
        assertEquals(errors.size(), 1);
    }
}
