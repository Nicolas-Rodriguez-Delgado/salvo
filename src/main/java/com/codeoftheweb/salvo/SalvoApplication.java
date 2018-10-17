package com.codeoftheweb.salvo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SalvoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SalvoApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData (PlayerRepository playerRepository,
									   GameRepository gameRepository,
									   GamePlayerRepository gamePlayerRepository) {
		return (args) -> {
			// save a couple of customers
//			playerRepository.save(new Player ("Jack", "javaisworse@help.com"));
			playerRepository.save(new Player ("Chloe", "koko@koko.com"));
			playerRepository.save(new Player ("Kim", "basinger@basinger.com"));
			playerRepository.save(new Player ("David", "123@123.com"));
			playerRepository.save(new Player ("Michelle", "pfeiffer@mich.com"));
			gameRepository.save(new Game());
			gameRepository.save(new Game());
			gameRepository.save(new Game());
//			gamePlayerRepository.save( )

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

		};
	}

}
