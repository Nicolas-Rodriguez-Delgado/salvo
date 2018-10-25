package com.codeoftheweb.salvo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.*;

@Entity
public class GamePlayer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    private Date date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id")
    @JsonIgnore
    private Player playerID;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id")
    @JsonIgnore
    private Game gameID;

    @OneToMany(mappedBy = "gameplayerID", fetch = FetchType.LAZY)
    List<Ship> shipSet = new ArrayList<>();

    @OneToMany(mappedBy = "gamePlayer", fetch = FetchType.EAGER)
    List<Salvo> salvoSet = new ArrayList<>();

    public GamePlayer() {
    }

    public Player getPlayerID() {
        return playerID;
    }

    public void setPlayerID(Player playerID) {
        this.playerID = playerID;
    }

    public GamePlayer(Player playerID, Game gameID) {

        this.date = new Date();
        this.playerID = playerID;
        this.gameID = gameID;
    }

    public long getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public List<Ship> getShipSet() {
        return shipSet;
    }

    public List<Salvo> getSalvoSet() {
        return salvoSet;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void addShip(Ship ship) {

        ship.setGameplayerID(this);
        shipSet.add(ship);

    }

    public void addSalvo(Salvo salvo){

        salvo.setGamePlayer(this);
        salvoSet.add(salvo);
    }

    public Game getGameID() {
        return gameID;
    }


}


