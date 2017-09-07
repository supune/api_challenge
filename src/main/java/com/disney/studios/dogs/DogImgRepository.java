package com.disney.studios.dogs;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DogImgRepository extends JpaRepository<DogImg, Long> {

    String SELECT_STRING =
            "SELECT D.url as url ,D.breed as breed, count(V) as votes, D.id as id " +
            "FROM DogImg D left join D.votes V ";
    String POST_STRING =
            "group by D.breed, D.id order by D.breed, votes desc, id";

    Page<DogImg> findByBreed(String breed, Pageable pageable);

    @Query(SELECT_STRING + POST_STRING)
    Page findWithVoteCount(Pageable page);

    @Query(SELECT_STRING + "where D.breed=:breed group by D order by votes desc, D.id")
    Page findByBreedWithVoteCount(@Param("breed") String breed, Pageable pageable);
}
