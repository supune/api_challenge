package com.disney.studios.dogs;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(indexes = @Index(columnList = "breed"))
public class DogImg implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    private String url;

    private String breed;

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    @OneToMany(mappedBy = "dogImg", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Set<Vote> votes = new HashSet<>();

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

    @JsonIgnore
    public Set<Vote> getVotes() {
        return votes;
    }

    public void setVotes(HashSet<Vote> votes) {
        this.votes = votes;
    }

    public int getVoteCount() {
        return getVotes().size();
    }


}
