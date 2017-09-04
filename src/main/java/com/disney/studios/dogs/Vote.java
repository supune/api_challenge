package com.disney.studios.dogs;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@IdClass(VoteId.class)
public class Vote implements Serializable{

    @Id
    long userId;

    @Id
    @ManyToOne
    DogImg dogImg;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public DogImg getDogImg() {
        return dogImg;
    }

    public void setDogImg(DogImg dogImg) {
        this.dogImg = dogImg;
    }

}

class VoteId implements Serializable{
    DogImg dogImg;
    Long userId;
}


