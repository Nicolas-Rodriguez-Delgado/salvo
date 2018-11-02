package com.codeoftheweb.salvo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class SalvoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SalvoApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData (PlayerRepository playerRepository,
									   GameRepository gameRepository,
									   GamePlayerRepository gamePlayerRepository,
									   ShipRepository shipRepository,
									   SalvoRepository salvoRepository,
									   ScoreRepository scoreRepository) {
		return (args) -> {
			// save a couple of customers
//

			Player p1 = new Player("Jack", "javaisworse@help.com");
			playerRepository.save(p1);
			Player p2 = new Player("Kim", "basinger@basinger.com");
			playerRepository.save(p2);
			Player p3 = new Player("David", "123@123.com");
			playerRepository.save(p3);
			Player p4 = new Player("Michelle", "pfeiffer@mich.com");
			playerRepository.save(p4);


			Game g1 = new Game();
			gameRepository.save(g1);
			Game g2 = new Game();
			gameRepository.save(g2);
			Game g3 = new Game();
			gameRepository.save(g3);

			GamePlayer gp1 = new GamePlayer(p1, g1);
			gamePlayerRepository.save(gp1);
			GamePlayer gp2 = new GamePlayer(p2, g1);
			gamePlayerRepository.save(gp2);
			GamePlayer gp3 = new GamePlayer(p3, g2);
			gamePlayerRepository.save(gp3);
			GamePlayer gp4 = new GamePlayer(p4, g2);
			gamePlayerRepository.save(gp4);

			List<String> loc1 = Arrays.asList("A1","A2","A3","A4","A5");
			List<String> loc2 = Arrays.asList("A8","B8","C8","D8");
			List<String> loc3 = Arrays.asList("F1","F2","F3");
			List<String> loc4 = Arrays.asList("D5","E5","F5");
			List<String> loc5 = Arrays.asList("B3","C3");
			List<String> loc6 = Arrays.asList("H2","H3","H4","H5","H6");
			List<String> loc7 = Arrays.asList("E8","F8","G8","H8");
			List<String> loc8 = Arrays.asList("I1","I2","I3");
			List<String> loc9 = Arrays.asList("F10","G10","H10");
			List<String> loc10 = Arrays.asList("B3","C3");


			Ship s1 = new Ship("carrier", loc1);
			Ship s2 = new Ship("battleship", loc2);
			Ship s3 = new Ship("submarine", loc3);
			Ship s4 = new Ship("destroyer", loc4);
			Ship s5 = new Ship("patrol boat", loc5);
			Ship s6 = new Ship("carrier", loc6);
			Ship s7 = new Ship("battleship", loc7);
			Ship s8 = new Ship("submarine", loc8);
			Ship s9 = new Ship("destroyer", loc9);
			Ship s10 = new Ship("patrol boat", loc10);

			gp1.addShip(s1);
			gp1.addShip(s2);
			gp1.addShip(s3);
			gp1.addShip(s4);
			gp1.addShip(s5);
			gp2.addShip(s6);
			gp2.addShip(s7);
			gp2.addShip(s8);
			gp2.addShip(s9);
			gp2.addShip(s10);

			shipRepository.save(s1);
			shipRepository.save(s2);
			shipRepository.save(s3);
			shipRepository.save(s4);
			shipRepository.save(s5);
			shipRepository.save(s6);
			shipRepository.save(s7);
			shipRepository.save(s8);
			shipRepository.save(s9);
			shipRepository.save(s10);

			List<String> sloc1 = Arrays.asList("A3","A8","B10");
			List<String> sloc2 = Arrays.asList("B3","H8","D5");
			List<String> sloc3 = Arrays.asList("G7","J3","I5");
			List<String> sloc4 = Arrays.asList("I2","F3","D2");

			Salvo sl1 = new Salvo(1, sloc1);
			Salvo sl2 = new Salvo(1,sloc2);
			Salvo sl3 = new Salvo(2,sloc3);
			Salvo sl4 = new Salvo(2, sloc4);

			gp1.addSalvo(sl1);
			gp2.addSalvo(sl2);
			gp1.addSalvo(sl3);
			gp2.addSalvo(sl4);

			salvoRepository.save(sl1);
			salvoRepository.save(sl2);
			salvoRepository.save(sl3);
			salvoRepository.save(sl4);

			Score score1 = new Score(p1, g1, 0.0);
			Score score2 = new Score(p2, g1, 1.0);
			Score score3 = new Score(p3, g2, 0.5);
			Score score4 = new Score(p4, g2, 0.5);
			Score score5 = new Score(p3, g3, 1.0);
			Score score6 = new Score(p4, g3, 0.0);

			scoreRepository.save(score1);
			scoreRepository.save(score2);
			scoreRepository.save(score3);
			scoreRepository.save(score4);
			scoreRepository.save(score5);
			scoreRepository.save(score6);


		};
	}

}
