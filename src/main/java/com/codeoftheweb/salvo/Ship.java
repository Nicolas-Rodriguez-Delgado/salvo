package com.codeoftheweb.salvo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
public class Ship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String type;

    @ElementCollection
    @Column(name = "location")
    private List<String> location;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "g")
    @JsonIgnore
    private GamePlayer gameplayerID;




    public Ship(String type, List<String> location ) {
        this.type = type;
        this.location = location;
    }

    public Ship () {}

    public long getId() {
        return id;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getLocation() {
        return location;
    }

    public void setLocation(List<String> location) {
        this.location = location;
    }

    public GamePlayer getGameplayerID() {
        return gameplayerID;
    }

    public void setGameplayerID(GamePlayer gameplayerID) {
        this.gameplayerID = gameplayerID;
    }
}
