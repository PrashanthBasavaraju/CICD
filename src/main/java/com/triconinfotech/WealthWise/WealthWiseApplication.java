package com.triconinfotech.WealthWise;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;



/**
 * The Class WealthWiseApplication.
 */
@SpringBootApplication
@EnableAutoConfiguration(exclude = { FlywayAutoConfiguration.class })
public class WealthWiseApplication {

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 * @throws Exception the exception
	 */
	public static void main(String[] args) throws Exception {
		SpringApplication.run(WealthWiseApplication.class, args);
		System.out.println("Wealthwise started");
	}

	/**
	 * Repair flyway.
	 *
	 * @return the flyway migration strategy
	 */
//	@Bean
//	public FlywayMigrationStrategy repairFlyway() {
//		return flyway -> {
//			// repair each script's checksum
//			flyway.repair();
//			// before new migrations are executed
//			flyway.migrate();
//		};
//	}
}
