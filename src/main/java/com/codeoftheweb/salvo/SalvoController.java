package com.codeoftheweb.salvo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class SalvoController {

    @Autowired
    GameRepository GameRepo;


    @RequestMapping("/games")
    @GetMapping
    public List<Object> findThemAll() {
        return GameRepo.findAll().stream().map(game -> gameMap(game)).collect(toList());
    }

    private Map<String, Object> gameMap (Game game) {
        Map<String, Object> gamemap = new LinkedHashMap<String, Object>();
        gamemap.put("id", game.getId());
        gamemap.put("date", game.getDate());
        gamemap.put("gamplayers", gameplayerSet(game.gamePlayerSet));
        return gamemap;
    }

    private Map<String, Object> playerMap (Player player) {
        Map<String, Object> playermap = new LinkedHashMap<String, Object>();
        playermap.put("id", player.getId());
        playermap.put("username", player.getUserName());
        playermap.put("email", player.getEmail());

        return playermap;
    }

    private Map<String, Object> gameplayerMap (GamePlayer gamePlayer) {
        Map<String, Object> gameplayermap = new LinkedHashMap<String, Object>();
        gameplayermap.put("id", gamePlayer.getId());
        gameplayermap.put("player", playerMap(gamePlayer.getPlayerID()));

        return gameplayermap;
    }

    public List<Map<String, Object>> gameplayerSet(Set<GamePlayer> gameplayer) {
        return gameplayer.stream().map(gameplay -> gameplayerMap(gameplay)).collect(toList());
    }
}
