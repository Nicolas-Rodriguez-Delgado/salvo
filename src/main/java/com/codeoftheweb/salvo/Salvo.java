package com.codeoftheweb.salvo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
public class Salvo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    private int turn;

    @ElementCollection
    @Column(name = "locations")
    private List<String> locations;

    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn(name = "gameplayer_id")
    @JsonIgnore
    private GamePlayer gamePlayer;


    public Salvo() { }

    public Salvo(int turn, List<String> locations) {
        this.turn = turn;
        this.locations = locations;

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public List<String> getLocations() {
        return locations;
    }

    public void setLocations(List<String> locations) {
        this.locations = locations;
    }

    public GamePlayer getGamePlayer() {
        return gamePlayer;
    }

    public void setGamePlayer(GamePlayer gamePlayer) {
        this.gamePlayer = gamePlayer;
    }
}
