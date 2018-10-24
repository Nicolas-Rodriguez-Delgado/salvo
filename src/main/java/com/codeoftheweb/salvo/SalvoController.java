package com.codeoftheweb.salvo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class SalvoController {

    @Autowired
    private GameRepository gameRepo;
    @Autowired
    private GamePlayerRepository GamePlayerRepo;
    @Autowired
    ShipRepository shipRepository;


    @RequestMapping("/games")
    @GetMapping
    public List<Object> findThemAll() {
        return gameRepo
                .findAll()
                .stream()
                .map(game -> gameMap(game))
                .collect(toList());
    }


    private Map<String, Object> gameMap (Game game) {
        Map<String, Object> gamemap = new LinkedHashMap<String, Object>();
        gamemap.put("id", game.getId());
        gamemap.put("date", game.getDate());
        gamemap.put("gamplayers", gameplayerSet(game.getGamePlayerSet()));
        return gamemap;
    }


    private Map<String, Object> gameplayerMap (GamePlayer gamePlayer) {
        return new LinkedHashMap<String, Object>(){{
            put("id", gamePlayer.getId());
            put("player", playerMap(gamePlayer.getPlayerID()));
        }};
    }

    private Map<String, Object> playerMap (Player player) {
        Map<String, Object> playermap = new LinkedHashMap<String, Object>();
        playermap.put("id", player.getId());
        playermap.put("username", player.getUserName());
        playermap.put("email", player.getEmail());

        return playermap;
    }

    public List<Map<String, Object>> gameplayerSet(Set<GamePlayer> gameplayers) {
        return gameplayers.stream()
                .map(gameplay -> gameplayerMap(gameplay))
                .collect(toList());
    }




    @RequestMapping("/game_view/{id}")
    public Map<String, Object> gameView (@PathVariable Long id) {
         GamePlayer gg = GamePlayerRepo.findById(id).orElse(null);
        if(gg != null) {
            return gamePMap(gg);
        }else {
            return null;
        }

    }


    private Map<String, Object> gamePMap (GamePlayer gamePlayer) {
        Map<String, Object> gamePmap = new LinkedHashMap<String, Object>();
        gamePmap.put("id", gamePlayer.getGameID().getId());
        gamePmap.put("date", gamePlayer.getDate());
        gamePmap.put("gameplayer", gameplayerSet(gamePlayer.getGameID().getGamePlayerSet()));
        gamePmap.put("ships", ships(gamePlayer.getShipSet()));
        return gamePmap;
    }

    private Map<String, Object> shipMap (Ship gp) {

        Map<String, Object> sm = new LinkedHashMap<>();
        sm.put("type", gp.getType());
        sm.put("locations", gp.getLocation());
        return sm;
    }

    public List<Map<String, Object>> ships (List<Ship> ship) {
        return ship.stream().map(loc -> shipMap(loc)).collect(toList());
    }


}
