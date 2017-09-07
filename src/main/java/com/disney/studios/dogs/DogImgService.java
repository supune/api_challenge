package com.disney.studios.dogs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class DogImgService {

    @Autowired
    private DogImgRepository dogImgRepository;

    @Autowired
    private VoteRepository voteRepository;

    @Transactional
    public DogImg saveDogImg(String breed, String url) {
        DogImg img = new DogImg();
        img.setBreed(breed);
        img.setUrl(url);
        return dogImgRepository.save(img);
    }

    @Transactional
    public Iterable<DogImg> saveDogImgs(Iterable<DogImg> dogsToSave) {
        return dogImgRepository.save(dogsToSave);
    }

    @Transactional
    public Vote saveVote(long dogImgId, long userId) {

        DogImg dogImg = dogImgRepository.findOne(dogImgId);
        if(dogImg == null) throw new IllegalArgumentException("dog image not found");

        Vote alreadyExists = voteRepository.findOneByDogImgAndUser(dogImg, userId);
        if(alreadyExists != null) throw new IllegalStateException("vote already exists");

        Vote vote = new Vote();
        vote.setDogImg(dogImg);
        vote.setUser(userId);

        return voteRepository.save(vote);
    }

    @Transactional
    public void deleteVote(long dogImgId, long userId) {

        Vote vote = voteRepository.findOneByDogImgIdAndUser(dogImgId, userId);
        if(vote == null) throw new IllegalArgumentException("vote not found");

        voteRepository.delete(vote);

    }


}
