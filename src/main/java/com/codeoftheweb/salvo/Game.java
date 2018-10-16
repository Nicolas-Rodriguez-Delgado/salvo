package com.codeoftheweb.salvo;

import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private Date date;

    @OneToMany(mappedBy="gameID", fetch=FetchType.LAZY)
    Set<GamePlayer> gamePlayerSet;

//    public void addGamePlayer(GamePlayer gamePlayer) {
//        gamePlayer.setGame(this);
//        gamePlayerSet.add(gamePlayer);
//    }

    public Game () {
        this.date = new Date();
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getId() {
        return id;
    }



}

