package com.example.blogjwt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@EnableJpaAuditing // Time entity 자동생성
@SpringBootApplication
public class BlogJwtApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogJwtApplication.class, args);
	}

}
