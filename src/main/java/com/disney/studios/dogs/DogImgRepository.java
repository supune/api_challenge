package com.disney.studios.dogs;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface DogImgRepository extends JpaRepository<DogImg, Long> {

    Page<DogImg> findByBreed(String breed, Pageable pageable);

//    @Query("SELECT D FROM DogImg D inner join D.votes")
//    List<DogImg> locate();
}
