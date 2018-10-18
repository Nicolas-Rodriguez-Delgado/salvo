package com.codeoftheweb.salvo;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long date;



    @OneToMany(mappedBy="gameID", fetch=FetchType.LAZY)
    private Set<GamePlayer> gamePlayerSet;


    public Game () {
        this.date = new Date().getTime();
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public Set<GamePlayer> getGamePlayerSet() {
        return gamePlayerSet;
    }



}

