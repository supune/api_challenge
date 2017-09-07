package com.disney.studios.dogs;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DogsImgController {

    @Autowired
    private DogImgRepository dogImgRepository;

    @Autowired
    private DogImgService dogImgService;

    @RequestMapping(value = "/dogs", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<DogImg> index(Pageable pageable) {

        //return dogImgRepository.findAll(pageable);
        return dogImgRepository.findWithVoteCount(pageable);
    }

    @RequestMapping(value = "/dogs/breed/{breed}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<DogImg> byBreed(@PathVariable("breed") String breed, Pageable pageable) {
        return dogImgRepository.findByBreedWithVoteCount(breed, pageable); //dogImgRepository.findByBreed(breed, pageable);
    }

    @RequestMapping(value = "/dog/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DogImg> getDogImg(@PathVariable("id") long id) {
        DogImg found = dogImgRepository.findOne(id);
        if(found == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(found);
    }

    @RequestMapping(value = "/dog/{dogId}/vote/{user}", method = RequestMethod.PUT)
    public ResponseEntity<?> upVote(@PathVariable("dogId") long dogImgId, @PathVariable("user") long userId) {

        try {
            dogImgService.saveVote(dogImgId, userId);
        } catch(IllegalArgumentException badDogImgId) {
            return ResponseEntity.notFound().build();
        } catch(IllegalStateException alreadyExists) {
            //in case client needs to know a vote was already cast use a different 20x
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/dog/{dogId}/vote/{user}", method = RequestMethod.DELETE)
    public ResponseEntity<?> downVote(@PathVariable("dogId") long dogImgId, @PathVariable("user") long userId) {

        try {
            dogImgService.deleteVote(dogImgId, userId);
        } catch(IllegalArgumentException badDogImgId) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().build();
    }
}
