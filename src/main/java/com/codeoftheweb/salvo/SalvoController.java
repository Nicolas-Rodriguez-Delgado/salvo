package com.codeoftheweb.salvo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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
    private PlayerRepository playerRepository;


    @RequestMapping(value ="/games")
    @GetMapping
    public List<Map<String, Object>> findThemAll(Authentication authentication) {
        List<Map<String, Object>> collect = gameRepo
                .findAll()
                .stream()
                .map(game -> gameMap(game, authentication))
                .collect(toList());
        return collect;
    }


    private Map<String, Object> playerMap (Player player) {
        Map<String, Object> playermap = new LinkedHashMap<String, Object>();
        playermap.put("id", player.getId());
        playermap.put("username", player.getUserName());
        playermap.put("email", player.getEmail());

        return playermap;
    }

    private Map<String, Object> gameMap (Game game, Authentication authentication) {
        Map<String, Object> gamemap = new LinkedHashMap<String, Object>();

        if(!isGuest(authentication)) {
            gamemap.put("loggedPlayer", playerMap(getAuthPlayer(authentication)));
        } else {
            gamemap.put("loggedPlayer", null);
        }
        gamemap.put("id", game.getId());
        gamemap.put("date", game.getDate());
        gamemap.put("gameplayers", gameplayerSet(game.getGamePlayerSet()));
        gamemap.put("scores", game.getGameScore());

        return gamemap;
    }

    @RequestMapping(value = "/games", method = RequestMethod.POST)
    public ResponseEntity<Object> createGame(Authentication authentication) {

        if (isGuest(authentication)) {
            return new ResponseEntity<>(createMap("Error", "You must be Logged In"), HttpStatus.FORBIDDEN);
        }else {
            Game save = gameRepo.save(new Game());
            GamePlayer gamePlayer = GamePlayerRepo.save(new GamePlayer(getAuthPlayer(authentication),save));
            return new ResponseEntity<>(gamePlayer.getId(), HttpStatus.CREATED);
        }

    }


    private Player getAuthPlayer(Authentication authentication) {

        if (!isGuest(authentication)){
            return playerRepository.findByEmail(authentication.getName());
        }else{
            return null;
        }
    }

    private boolean isGuest(Authentication authentication) {
        return authentication == null || authentication instanceof AnonymousAuthenticationToken;
    }


    private Map<String, Object> gameplayerMap (GamePlayer gamePlayer) {
        return new LinkedHashMap<String, Object>(){{
            put("id", gamePlayer.getId());
            put("player", playerMap(gamePlayer.getPlayerID()));
        }};
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
        gamePmap.put("salvoes", salvoes(gamePlayer.getSalvoSet()));
        gamePmap.put("OpponentSalvoes", salvoes(gamePlayer.getOpponentSalvo(gamePlayer)));
        gamePmap.put("salvoHits", hits(gamePlayer));

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

    private Map<String, Object> salvoMap (Salvo slv){

        Map<String, Object> sl = new LinkedHashMap<>();
        sl.put("id", slv.getGamePlayer().getPlayerID().getId());
        sl.put("turn" , slv.getTurn());
        sl.put("locations", slv.getLocations());
        return sl;
    }

    public List<Map<String, Object>> salvoes (List<Salvo> salvos) {
        return salvos.stream().map(sl -> salvoMap(sl)).collect(toList());
    }

    public List<String> hits (GamePlayer gamePlayer) {
        List<String> hitList =  new ArrayList<>();
        for (String shipLocation: gamePlayer.opponentShipList(gamePlayer)){
            for(String salvoLocation: gamePlayer.salvoList(gamePlayer)){
                if (salvoLocation == shipLocation){
                    if(!hitList.contains(shipLocation)){
                        hitList.add(shipLocation);
                    }
                }
            }
        }
        return hitList;
    }

    @RequestMapping("/scores")
    @GetMapping
    public List<Object> findScores() {
        return playerRepository
                .findAll()
                .stream()
                .map(player -> scoreMap(player))
                .collect(toList());
    }


    private Map<String,Object> scoreMap (Player player){
        Map<String, Object> scoremap = new LinkedHashMap<>();
        scoremap.put("player", player.getEmail());
        scoremap.put("scores", player.getPlayerScore());
        return scoremap;
    }

    @RequestMapping(value = "/players", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> registerUser(  @RequestParam String username,

                                                              @RequestParam String password){
        if (username == "" || password == ""){

            return new ResponseEntity<>(createMap("Error","All fields must be filled"), HttpStatus.FORBIDDEN);

        }else if (playerRepository.findByEmail(username) == null){

            playerRepository.save(new Player(username, username, password));

            return new ResponseEntity<>(createMap("Success", "User created"), HttpStatus.CREATED);

        }else {
            return new ResponseEntity<>(createMap("Error","User already exists"), HttpStatus.FORBIDDEN);
        }
    }

    public Map<String, Object> createMap (String key, String value){
        Map<String, Object> map = new HashMap<>();
        map.put("status", key);
        map.put("key", value);
        return map;
    }

    @RequestMapping(value = "/api/game/{nn}/players", method = RequestMethod.POST)
    public ResponseEntity<Object> joinGame (@PathVariable Long nn,Authentication authentication){

        Game game = gameRepo.findById();

        if(isGuest(authentication)){
            return new ResponseEntity<>("Error", HttpStatus.UNAUTHORIZED);
        }else if (game.getId() != nn){
            return new ResponseEntity<>("Error", HttpStatus.FORBIDDEN);
        }else if(game.getGamePlayerSet().size() >= 2) {
            return new ResponseEntity<>("Error", HttpStatus.FORBIDDEN);
        }else {
             GamePlayerRepo.save(new GamePlayer(getAuthPlayer(authentication), game));
             return new ResponseEntity<>("Success", HttpStatus.CREATED);
        }

    }




}
