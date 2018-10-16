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
									   GameRepository gameRepository) {
		return (args) -> {
			// save a couple of customers
			playerRepository.save(new Player ("Jack", "javaisworse@help.com"));
			playerRepository.save(new Player ("Chloe", "koko@koko.com"));
			playerRepository.save(new Player ("Kim", "basinger@basinger.com"));
			playerRepository.save(new Player ("David", "123@123.com"));
			playerRepository.save(new Player ("Michelle", "pfeiffer@mich.com"));
			gameRepository.save(new Game());
			gameRepository.save(new Game());
			gameRepository.save(new Game());

		};
	}

}
