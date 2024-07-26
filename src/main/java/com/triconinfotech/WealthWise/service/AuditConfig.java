package com.triconinfotech.WealthWise.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


/**
 * The Class AuditConfig.
 */
@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class AuditConfig {
	
	/**
	 * Auditor aware.
	 *
	 * @return the auditor aware
	 */
	@Bean
	public AuditorAware<Long> auditorAware() {return new AuditorAwareImpl();}

}