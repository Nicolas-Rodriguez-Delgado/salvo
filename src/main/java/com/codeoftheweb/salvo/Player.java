package com.codeoftheweb.salvo;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.persistence.*;
import java.util.Optional;
import java.util.Set;

@Entity
public class Player {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long id;
        private String userName;
        private String email;

    @OneToMany(mappedBy="playerID", fetch=FetchType.LAZY)
    private Set<GamePlayer> gamePlayerSet;

    @OneToMany (mappedBy = "player", fetch = FetchType.LAZY)
    private Set<Score> playerScore;

    private String password;

    private User user;


    public Player(String userName, String email, String password) {
        this.userName = userName;
        this.email = email;
        this.password = password;
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

    public Set<Score> getPlayerScore() {
        return playerScore;
    }

    public Score getScore(Game game){
        return playerScore.stream().filter(score -> score.getGame().equals(game)).findFirst().orElse(null);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}