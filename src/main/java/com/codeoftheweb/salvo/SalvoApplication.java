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
	public CommandLineRunner initData (PlayerRepository repository) {
		return (args) -> {
			// save a couple of customers
			repository.save(new Player ("Jack", "javaisworse@help.com"));
			repository.save(new Player ("Chloe", "koko@koko.com"));
			repository.save(new Player ("Kim", "basinger@basinger.com"));
			repository.save(new Player ("David", "123@123.com"));
			repository.save(new Player ("Michelle", "pfeiffer@mich.com"));
		};
	}

}
