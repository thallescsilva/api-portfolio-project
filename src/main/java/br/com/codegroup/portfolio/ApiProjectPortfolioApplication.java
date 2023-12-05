package br.com.codegroup.portfolio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Locale;

@SpringBootApplication
public class ApiProjectPortfolioApplication {

	public static void main(String[] args) {
		Locale.setDefault(new Locale("pt", "BR"));
		SpringApplication.run(ApiProjectPortfolioApplication.class, args);
	}

}
