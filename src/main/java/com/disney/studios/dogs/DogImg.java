package com.disney.studios.dogs;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
public class DogImg implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    private String url;

    private HashSet<Vote> votes = new HashSet<>();

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @OneToMany(mappedBy = "dogImg")
    public HashSet<Vote> getVotes() {
        return votes;
    }

    public void setVotes(HashSet<Vote> votes) {
        this.votes = votes;
    }


}
