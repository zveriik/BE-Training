package controller;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.model.Dog;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.jayway.restassured.RestAssured.given;
import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;


public class DogControllerTest {

    @BeforeClass
    public static void setUp() {
        RestAssured.baseURI = "http://localhost:8080/";
    }

    @Test
    public void testGetDogStatus() {
        postDog(Dog.random());
        given().
                when().get("/dog").
                then().assertThat()
                .statusCode(200).and()
                .contentType(ContentType.JSON);
    }

    @Test
    public void testGetDogErrorStatus() {
        given().
                when().get("/dog" + Integer.MAX_VALUE).
                then().assertThat()
                .statusCode(404);
    }

    @Test
    public void testDogCreation() {
        Dog original = Dog.random();
        Dog created = postDog(original);
        original.setId(created.getId());
        Dog fromDb = getDogById(original.getId());
        assertReflectionEquals(original, fromDb);
    }

    @Test
    public void testDogPut() {
        Dog original = Dog.random();
        original.setId(2);
        original = putDog(2, original);
        Dog fromDb = getDogById(original.getId());
        assertReflectionEquals(original, fromDb);
    }

    @Test
    public void testDogPutNotValid() {
        Dog original = Dog.random();
        original.setId(5);
        original = putDog(5, original);
        Dog fromDb = getDogById(original.getId());
        assertReflectionEquals(original, fromDb);
    }

    @Test
    public void testDogDelete() {
        Dog original = Dog.random();
        Dog postedDog = postDog(original);
        given().pathParam("id", postedDog.getId()).
                when().delete("/dog/{id}").
                then().assertThat().statusCode(200);

        given().pathParam("id", postedDog.getId()).get("/dog/{id}").
                then().assertThat().statusCode(404);
    }

    private Dog getDogById(int id) {
        return given().pathParam("id", id).get("/dog/{id}").as(Dog.class);
    }

    private Dog postDog(Dog original) {
        return given().contentType(ContentType.JSON).accept(ContentType.JSON).body(original).
                when().post("/dog").as(Dog.class);
    }

    private Dog putDog(int id, Dog dog) {
        return given().pathParam("id", id).body(dog).contentType(ContentType.JSON).
                when().put("/dog/{id}").as(Dog.class);
    }
}
