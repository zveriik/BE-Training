import com.controller.DogController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/dog-test-servlet.xml")
public class DogControllerMockTest {

    private MockMvc mockMvc;

    @InjectMocks
    private DogController dogController = new DogController();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(dogController).build();
    }

    @Test
    public void testGetDog() throws Exception {
        this.mockMvc.perform(get("/dog"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
    }

//    @Test
//    public void test_not_found_error() throws Exception{
//        this.mockMvc.perform(get("dog/{id}", 200))
//                .andExpect(status().isNotFound());
//        verify()
//    }

//    @Test
//    public void testDogCreation() throws Exception {
//        Dog original = new Dog("test", true, 14);
//        this.mockMvc.perform(get("/dog/{id}", original.getId()))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
//                .andExpect()
//    }
}