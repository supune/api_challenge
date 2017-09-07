package com.disney.studios.dogs;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepository extends JpaRepository<Vote, Long> {

    Vote findOneByDogImgAndUser(DogImg dogImg, long user);

    Vote findOneByDogImgIdAndUser(long dogImgId, long user);


}
