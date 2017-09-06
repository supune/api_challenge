package com.disney.studios.dogs;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames ={"dog_img_id","user"}))
public class Vote implements Serializable{


    @Id
    @GeneratedValue
    private long id;

    @Column
    private long user;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private DogImg dogImg;

    public Long getUser() {
        return user;
    }

    public void setUser(Long user) {
        this.user = user;
    }

    public DogImg getDogImg() {
        return dogImg;
    }

    public void setDogImg(DogImg dogImg) {
        this.dogImg = dogImg;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setUser(long user) {
        this.user = user;
    }
}



