import com.jayway.restassured.http.ContentType;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


public class DogControllerTest {

    @DataProvider(name="dogNames")
    public Object[][] createTestDataRecords() {
        return new Object[][] {
                {1L, "one"},
                {2L, "two"},
                {3L, "three"}
        };
    }

    @Test
    public void testGetDogStatus() {
        given().
                when().get("http://localhost:8080/dog").
                then().assertThat()
                .statusCode(200).and()
                .contentType(ContentType.JSON);
    }

    @Test
    public void testGetDogErrorStatus() {
        given().
                when().get("http://localhost:8080/dog/5").
                then().assertThat()
                .statusCode(400);
    }

    @Test(dataProvider="dogNames")
    public void testGetDogByIdStatus(Long id, String name) {
        given().pathParam("id", id).
                when().get("http://localhost:8080/dog/{id}").
                then().assertThat().body("name", equalTo(name));
    }
}
