package com.disney.studios.dogs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
public class DogsImgController {

    @Autowired
    private DogImgRepository dogImgRepository;

    @Autowired
    private VoteRepository voteRepository;

    @RequestMapping(value = "/dogs", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<DogImg> index(Pageable pageable) {
        return dogImgRepository.findAll(pageable);
    }

    @RequestMapping(value = "/dog/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public DogImg getDogImg(@PathVariable("id") long id) {
        return dogImgRepository.findOne(id);
    }

    @RequestMapping(value = "/dog/{dogId}/vote/{userId}", method = RequestMethod.PUT)
    public ResponseEntity<?> upVote(@PathVariable("dogId") long dogImgId, @PathVariable("userId") long userId) {

        DogImg dogImg = dogImgRepository.findOne(dogImgId);
        if (dogImg == null) {
            return ResponseEntity.notFound().build();
        }

        Vote vote = new Vote();
        vote.setUserId(userId);
        vote.setDogImg(dogImg);

        voteRepository.save(vote);

        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/dog/{dogId}/vote/{userId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> downVote(@PathVariable("dogId") long dogImgId, @PathVariable("userId") long userId) {

        DogImg dogImg = dogImgRepository.findOne(dogImgId);
        if (dogImg == null) {
            return ResponseEntity.notFound().build();
        }

        Vote vote = new Vote();
        vote.setUserId(userId);
        vote.setDogImg(dogImg);

        voteRepository.delete(vote);

        return ResponseEntity.ok().build();
    }
}
