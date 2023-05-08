package com.board;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@EnableJpaAuditing
@Slf4j
public class BoardApplication {

	public static void main(String[] args) {
		log.info("main...");
		SpringApplication.run(BoardApplication.class, args);
	}

}
