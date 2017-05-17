import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.model.Dog;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.jayway.restassured.RestAssured.given;
import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;


public class DogControllerTest {

    @BeforeClass
    public static void setUp(){
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
                when().get("/dog/5").
                then().assertThat()
                .statusCode(400);
    }

    @Test
    public void testDogCreation() {
        Dog original = new Dog("test", true, 14);
        Dog created = postDog(original);
        original.setId(created.getId());
        Dog fromDb = getDogById(original.getId());
        assertReflectionEquals(original, fromDb);
    }

    @Test
    public void testDogPut(){
        Dog original = new Dog("put_dog", true, 14);
        original.setId(1);
        putDog(1, original);
        Dog fromDb = getDogById(original.getId());
        assertReflectionEquals(original, fromDb);
    }

    @Test
    public void testDogDelete() {
        Dog original = new Dog("put_dog", true, 14);
        original.setId(1);
        postDog(original);
        given()
            .pathParam("id", 1).
        when()
            .delete("http://localhost:8080/dog/{id}").
        then().assertThat().statusCode(200);
    }

    private Dog getDogById(int id) {
        return given().pathParam("id", id).get("/dog/{id}").as(Dog.class);
    }

    private Dog postDog(Dog original) {
        return given().body(original).contentType(ContentType.JSON).
                when().post("/dog/new").as(Dog.class);
    }

    private Dog putDog(int id, Dog dog){
        return given().pathParam("id", id).body(dog).contentType(ContentType.JSON).
                when().put("/dog/{id}").as(Dog.class);
    }
}
