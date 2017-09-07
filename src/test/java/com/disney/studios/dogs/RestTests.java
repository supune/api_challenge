package com.disney.studios.dogs;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RestTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private DogImgService dogImgService;

    @Autowired
    private DogImgRepository dogImgRepository;

    private ArrayList<DogImg> savedDogs = new ArrayList<>();
    @Before
    public void seedDogImgs() {
        ArrayList<DogImg> dogs = new ArrayList<>();

        for (int i=0; i<18; i++) {
            DogImg dogImg = new DogImg();
            dogImg.setBreed("breed" + i % 3);
            dogImg.setUrl("url" + i);
            dogs.add(dogImg);
        }

        savedDogs.addAll(dogImgRepository.save(dogs));

        int j = 1;
        for(DogImg dogImg: savedDogs) {
            j++;
            for(int k=j; k>0; k--) {
                dogImgService.saveVote(dogImg.getId(), k);
            }
        }
    }

    @Test
    public void testIndex() throws Exception{
        mvc.perform(MockMvcRequestBuilders.get("/dogs").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testFindOneDog() throws Exception {
        DogImg firstDog = savedDogs.get(0);
        mvc.perform(MockMvcRequestBuilders.get("/dog/" + firstDog.getId()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testUnknownDog() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/dog/999999").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }



}
