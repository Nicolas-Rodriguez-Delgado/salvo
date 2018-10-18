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
    private GameRepository GameRepo;
    @Autowired
    private GamePlayerRepository GamePlayerRepo;


    @RequestMapping("/games")
    @GetMapping
    public List<Object> findThemAll() {
        return GameRepo.findAll().stream()
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
        return new HashMap<String, Object>(){{
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

    @GetMapping("/game_view/{id}")
    public Object getInfo (@PathVariable long id) {
        return GamePlayerRepo.findById(id);
    }





    private Map<String, Object> shipMap (Ship ship) {
        Map<String, Object> shipmap = new LinkedHashMap<String, Object>();
        shipmap.put("type", ship.getType());
        shipmap.put("locations", ship.getLocation());
        return shipmap;
    }

    private Map<String, Object> gamePMap (GamePlayer gamePlayer) {
        Map<String, Object> gamePmap = new LinkedHashMap<String, Object>();
        gamePmap.put("id", gamePlayer.getId());
        gamePmap.put("date", gamePlayer.getDate());
        gamePmap.put("gamplayers", gameplayerSet(gamePlayer.getGameID().getGamePlayerSet()));
        gamePmap.put("ships", gamePlayer.getShipSet());
        return gamePmap;
    }

    public List<Map<String, Object>> shipSet (Set<Ship> ship) {
        return ship.stream().map(loc -> shipMap(loc)).collect(toList());
    }


}
