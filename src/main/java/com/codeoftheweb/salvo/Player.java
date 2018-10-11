package com.codeoftheweb.salvo;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Player {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long id;
        private String userName;
        private String email;


    public Player(String userName, String email) {
        this.userName = userName;
        this.email = email;
    }

    public Player() {
    }

    public String getUserName() {
            return userName;
        }
        public void setUserName(String user) {
            this.userName = user;
        }

        public String getEmail() {
            return this.email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String toString() {
            return userName + " " + email;
        }
}