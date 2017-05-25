import com.controller.DogController;
import com.model.Dog;
import com.service.DogService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.concurrent.ConcurrentHashMap;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:dog-servlet.xml")
public class DogControllerMockTest {

    private MockMvc mockMvc;

    @Mock
    private DogService dogService;

    @InjectMocks
    private DogController dogController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(dogController).build();
    }

    @Test
    public void testGetDogs() throws Exception {
        when(dogService.getAll()).thenReturn(createTestData());
        this.mockMvc.perform(get("/dog"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
//                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[1].id", is(1)))
                .andExpect(jsonPath("$[1].username", is("Daenerys Targaryen")))
                .andExpect(jsonPath("$[2].id", is(2)))
                .andExpect(jsonPath("$[2].username", is("John Snow")));
        verify(dogService, times(1)).getAll();
        verifyNoMoreInteractions(dogService);
    }
//
//    @Test
//    public void test_get_all_success() throws Exception {
//        List<Dog> users = Arrays.asList(
//                new Dog("mock_creation_1", false, 5),
//                new Dog("mock_creation_2", false, 3));
////        when(mockMvc.getAll()).thenReturn(users);
//        mockMvc.perform(get("/dog"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
//                .andExpect(jsonPath("$", hasSize(2)))
//                .andExpect(jsonPath("$[0].id", is(1)))
//                .andExpect(jsonPath("$[0].username", is("Daenerys Targaryen")))
//                .andExpect(jsonPath("$[1].id", is(2)))
//                .andExpect(jsonPath("$[1].username", is("John Snow")));
//        verify(userService, times(1)).getAll();
//        verifyNoMoreInteractions(userService);
//    }


//    @Test
//    public void test_not_found_error() throws Exception{
//        this.mockMvc.perform(get("dog/{id}", 200))
//                .andExpect(status().isNotFound());
//        verify()
//    }

//    @Test
//    public void testDogCreation() throws Exception {
//        Dog original = new Dog("test", true, 14);
//        Dog posted = postDog(original);
//        this.mockMvc.perform(get("/dog/{id}", original.getId()))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
//                .andExpect()
//    }

    private Dog postDog(Dog original) {
        return null;
    }

    private ConcurrentHashMap<Integer, Dog> createTestData(){
        ConcurrentHashMap<Integer, Dog> dogs = new ConcurrentHashMap<>();
        dogs.put(1, new Dog(1, "test_1", true, 2));
        dogs.put(2, new Dog(2, "test_2", true, 2));
        dogs.put(3, new Dog(3, "test_3", true, 2));
        return dogs;
    }
}