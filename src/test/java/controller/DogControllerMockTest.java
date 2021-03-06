package controller;

import com.controller.DogController;
import com.google.gson.Gson;
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

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:dog-servlet.xml")
public class DogControllerMockTest {

    private MockMvc mockMvc;

    private Gson gson = new Gson();

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
    public void test_get_all_success() throws Exception {
        when(dogService.getAll()).thenReturn(createTestData());
        this.mockMvc.perform(get("/dog"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$", hasSize(1)));
        verify(dogService, times(1)).getAll();
        verifyNoMoreInteractions(dogService);
    }

    @Test
    public void test_get_by_id_success() throws Exception {
        Dog dog = Dog.random();
        when(dogService.getById(1)).thenReturn(dog);
        this.mockMvc.perform(get("/dog/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id", is(dog.getId())))
                .andExpect(jsonPath("$.name", is(dog.getName())));
        verify(dogService, times(1)).getById(1);
        verifyNoMoreInteractions(dogService);
    }

    @Test
    public void test_get_by_id_fail_404_not_found() throws Exception {
        when(dogService.getById(1)).thenReturn(null);
        mockMvc.perform(get("/dog/{id}", 1))
                .andExpect(status().isNotFound());
        verify(dogService, times(1)).getById(1);
        verifyNoMoreInteractions(dogService);
    }

    @Test
    public void test_create_dog_success() throws Exception {
        Dog dog = Dog.random();
        when(dogService.create(any(Dog.class))).thenReturn(dog);
        mockMvc.perform(post("/dog")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(dog)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(dog.getId())))
                .andExpect(jsonPath("$.name", is(dog.getName())))
                .andExpect(jsonPath("$.age", is(dog.getAge())));
        verify(dogService, times(1)).create(dog);
        verifyNoMoreInteractions(dogService);
    }

    @Test
    public void test_update_dog_success() throws Exception {
        Dog dog = Dog.random();
        when(dogService.update(1, dog)).thenReturn(dog);
        mockMvc.perform(put("/dog/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(dog)))
                .andExpect(status().isOk());
        verify(dogService, times(1)).update(1, dog);
        verifyNoMoreInteractions(dogService);
    }

    @Test
    public void test_delete_dog_success() throws Exception {
        Dog dog = Dog.random();
        when(dogService.delete(dog.getId())).thenReturn(dog);
        mockMvc.perform(
                delete("/dog/{id}", dog.getId()))
                .andExpect(status().isOk());
        verify(dogService, times(1)).delete(dog.getId());
        verifyNoMoreInteractions(dogService);
    }
    
    private List<Dog> createTestData(){
        List<Dog> dogs = new ArrayList<>();
        dogs.add(Dog.random());
        return dogs;
    }
}