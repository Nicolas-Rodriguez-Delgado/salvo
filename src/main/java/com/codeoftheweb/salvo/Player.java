package com.codeoftheweb.salvo;


import javax.persistence.*;
import java.util.Set;

@Entity
public class Player {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long id;
        private String userName;
        private String email;

    @OneToMany(mappedBy="player_id", fetch=FetchType.EAGER)
    Set<GamePlayer> gamePlayerSet;


    public void addGamePlayer(GamePlayer gamePlayer) {
        gamePlayer.setPlayer(this);
        gamePlayerSet.add(gamePlayer);
    }

    public Player(String userName, String email) {
        this.userName = userName;
        this.email = email;
    }

    public Player() {
    }

    public long getId() {
        return id;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}