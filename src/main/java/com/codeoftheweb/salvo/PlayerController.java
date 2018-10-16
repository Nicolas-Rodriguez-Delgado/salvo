package com.codeoftheweb.salvo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("player")
public class PlayerController {

    @Autowired
    PlayerRepository playerRepository;

    @GetMapping
    public List<Player> findThemAll(){
        return playerRepository.findAll();
    }
}
