package com.disney.studios.dogs;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface VoteRepository extends JpaRepository<Vote, Long> {

    Vote findOneByDogImgAndUser(DogImg dogImg, long user);

    Vote findOneByDogImgIdAndUser(long dogImgId, long user);

    @Query("Select V FROM Vote V left join V.dogImg")
    List<Vote> locate();

}
