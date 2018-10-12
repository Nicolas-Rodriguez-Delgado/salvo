package com.codeoftheweb.salvo;

import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Game {


    @CreatedDate
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Date date;
    private long id;

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

