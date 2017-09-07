package com.disney.studios.dogs;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class DogImgDataTests {

    @Autowired
    DogImgRepository dogImgRepository;

    @Autowired
    VoteRepository voteRepository;

    @Autowired
    DogImgService dogImgService;

    @Test
    public void saveDogImgTest() {

        DogImg saved = dogImgService.saveDogImg("testBread", "http");
        Assert.assertNotNull(dogImgRepository.findOne(saved.getId()));
    }

    @Test
    public void saveVoteTest() {

        DogImg savedDogImg = dogImgService.saveDogImg("testBreed", "http://ad.com");

        Vote savedVote = dogImgService.saveVote(savedDogImg.getId(), 1L);

        Assert.assertNotNull(voteRepository.findOne(savedVote.getId()));

        Assert.assertNotNull(voteRepository.findOneByDogImgIdAndUser(savedDogImg.getId(), 1L));

    }

    @Test
    public void deleteVoteTest() {
        final long userId = 3L;

        DogImg savedDogImg = dogImgService.saveDogImg("testBreed", "http://ad.com");
        Vote savedVote = dogImgService.saveVote(savedDogImg.getId(), userId);
        Assert.assertNotNull(voteRepository.findOneByDogImgIdAndUser(savedDogImg.getId(), userId));

        dogImgService.deleteVote(savedDogImg.getId(), userId);
        Assert.assertNull(voteRepository.findOneByDogImgIdAndUser(savedDogImg.getId(), userId));
    }


    @Test
    public void getDogImagesEvenWhenNoVotes() {
        DogImg savedDogImg1 = dogImgService.saveDogImg("testBreed1", "http://ad1.com");
        DogImg savedDogImg2 = dogImgService.saveDogImg("testBreed2", "http://ad2.com");
        DogImg savedDogImg3 = dogImgService.saveDogImg("testBreed3", "http://ad3.com");

        for(long l=0; l<99; l++) {
            dogImgService.saveVote(savedDogImg1.getId(), l);
        }

        dogImgService.saveVote(savedDogImg2.getId(), 2L);

        Page page = dogImgRepository.findWithVoteCount(new PageRequest(0,5));

        Assert.assertEquals(new Long(99), ((Object[])(page.getContent().get(0)))[2]);
    }



}

