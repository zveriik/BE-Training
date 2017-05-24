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
        given().
                when().get("/dog").
                then().assertThat()
                .statusCode(200).and()
                .contentType(ContentType.JSON);
    }

    @Test
    public void testGetDogErrorStatus() {
        given().
                when().get("/dog/" + Integer.MAX_VALUE).
                then().assertThat()
                .statusCode(400);
    }

    @Test
    public void testDogCreation() {
        Dog original = new Dog("creation_test", true, 14);
        Dog created = postDog(original);
        original.setId(created.getId());
        Dog fromDb = getDogById(original.getId());
        assertReflectionEquals(original, fromDb);
    }

    @Test
    public void testDogPut() {
        Dog original = new Dog("put_test", true, 14);
        original.setId(2);
        putDog(2, original);
        Dog fromDb = getDogById(original.getId());
        assertReflectionEquals(original, fromDb);
    }

    @Test
    public void testDogDelete() {
        Dog original = new Dog("deleted_test", true, 14);
        Dog postedDog = postDog(original);
        given().pathParam("id", postedDog.getId()).
                when().delete("/dog/{id}").
                then().assertThat().statusCode(200);

        given().pathParam("id", postedDog.getId()).get("/dog/{id}").
                then().assertThat().statusCode(400);
    }

    private Dog getDogById(int id) {
        return given().pathParam("id", id).get("/dog/{id}").as(Dog.class);
    }

    private Dog postDog(Dog original) {
        return given().body(original).contentType(ContentType.JSON).
                when().post("/dog/").as(Dog.class);
    }

    private Dog putDog(int id, Dog dog) {
        return given().pathParam("id", id).body(dog).contentType(ContentType.JSON).
                when().put("/dog/{id}").as(Dog.class);
    }
}
